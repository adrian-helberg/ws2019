-module(skizze).
-author("Main").

%% API
-compile(export_all).

fordfulkerson(Filename, Q, S) ->
  G = step1(adtgraph:importG(Filename, d), Q),
  G_ = loop(G),
  step4(G_).

loop(G) ->
  case step2a(G) of
    true -> G;
    false ->
      G_ = step2b(),
      case step2c() of
        true -> step3(), loop(G_);
        false -> loop(G_)
      end
  end.

%%% INITIALISIERUNG
step1(G, Q) ->
  Edges = adtgraph:getEdges(G),
  G_ = initialize(G, Edges, 'flow', 0),
  mark(G_, Q, 'Marke', {undefined, 'Infinity'}).

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2|T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

mark(G, V, Name, Value) -> adtgraph:setAtV(G, V, Name, Value).

%%% Inspektion und Markierung
step2a(G) ->
  areAllNodesMarked(G, adtgraph:getVertices(G), true).

areAllNodesMarked(_, [], Marked) -> Marked;
areAllNodesMarked(_, _, false) -> false;
areAllNodesMarked(G, [H|T], _) ->
Value = adtgraph:getValV(G, H, 'Marke'),
areAllNodesMarked(G, T, checkValue(Value)).

checkValue(nil) -> false;
checkValue({_, Value}) -> Value > 0.

step2b() -> nil.

step2c() -> nil.

%%% Vergrößerung der Flussstärke
step3() -> nil.

%%% Der Wert von d ist optimal
step4(G) -> nil.