-module(edmondskarp).
-author("Adrian Helberg, 2309051").
-export([edmondskarp/3, edmondskarpT/3]).

%%%
%%% Edmonds-Karp Algorithmus
%%% Autor: Adrian Helberg, 2309051
%%%
edmondskarp(GraphOrFilename, Q, S) ->
  % Register global debug variable to enable outputs, logging, etc.
  util:setglobalvar('Debug', true),
  % Register variables for source and sink for global access
  util:setglobalvar('Q', Q),
  util:setglobalvar('S', S),
  util:setglobalvar('Queue', []),
  % Delete logging file because existing file woulnd not be overwritten correctly
  file:delete('VerGrWege.log'),
  G = case util:type_is(GraphOrFilename) of
        atom ->
          util:setglobalvar('Filename', GraphOrFilename),
          adtgraph:importG(GraphOrFilename, d);
        tuple ->
          % Register global name for generated *.dot and *.svg files
          util:setglobalvar('Filename', 'result'),
          GraphOrFilename;
        _ -> throw('Bad parameter, should be graph-file name as atom or intern adtgraph-graph')
      end,
  G_ = step1(G),
  loop(G_, []).

edmondskarpT(GraphOrFilename, Q, S) ->
  % Register global debug variable to disable outputs, logging, etc.
  util:setglobalvar('Debug', false),
  util:setglobalvar('Q', Q),
  util:setglobalvar('S', S),
  % Register global queue
  util:setglobalvar('Queue', []),
  G = case util:type_is(GraphOrFilename) of
        atom -> adtgraph:importG(GraphOrFilename, d);
        tuple -> GraphOrFilename;
        _ -> throw('Bad parameter, should be graph-file name as atom or intern adtgraph-graph')
      end,
  G_ = step1(G),
  loop(G_, []).

% algorithm recursion loop
loop(G, Nodes) ->
  case step2a(G) of
    true ->
      Debug = util:getglobalvar('Debug'),
      if
        Debug == true ->
          F = util:getglobalvar('Filename'),
          graphToDot(G, F),
          dotToSVG(F, 500);
        true -> ok
      end,
      Nodes;
    false ->
      {G_, MarkedNotInspectedNode} = step2b(G),
      Incidents = adtgraph:getIncident(G_, MarkedNotInspectedNode),
      G__ = processEdges(G_, MarkedNotInspectedNode, Incidents),
      case step2c(G__) of
        true ->
          S = util:getglobalvar('S'),
          {_, _, Delta} = adtgraph:getValV(G__, S, 'Marke'),
          {G_Vergr, N} = step3(G__, S, [], Delta),
          loop(G_Vergr, N);
        false -> loop(G__, Nodes)
      end
  end.

%%% INITIALISIERUNG
% Weise allen Kanten f(eij) als einen (initialen) Wert zu,
% der die Nebenbedingungen erfüllt. Markiere q mit (undefiniert, ∞)
step1(G) ->
  Edges = adtgraph:getEdges(G),
  G_ = initialize(G, Edges, 'flow', 0),
  mark(G_, util:getglobalvar('Q'), {'/', undefined, 'Infinity'}).

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2 | T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

mark(G, V, Value) ->
  % Füge markierte Knoten der Queue hinzu
  util:setglobalvar('Queue', [V] ++ util:getglobalvar('Queue')),
  adtgraph:setAtV(G, V, 'Marke', Value).
inspect(G, V) ->
  % Entferne inspizierte Knoten aus der Queue
  util:setglobalvar('Queue', util:getglobalvar('Queue') -- [V]),
  adtgraph:setAtV(G, V, 'Inspektion', '*').

%%% Inspektion und Markierung
% Falls alle markierten Ecken inspiziert wurden, terminiere (Schnitt wird nicht gebraucht)
step2a(G) -> areNodesMarkedInspected(G, adtgraph:getVertices(G)).

areNodesMarkedInspected(_, []) -> true;
areNodesMarkedInspected(G, [H | T]) ->
  Marked = isMarked(adtgraph:getValV(G, H, 'Marke')),
  Inspected = isInspected(adtgraph:getValV(G, H, 'Inspektion')),
  case Marked of
    true ->
      case Inspected of
        true -> areNodesMarkedInspected(G, T);
        false -> false
      end;
    false -> areNodesMarkedInspected(G, T)
  end.

isMarked({_, _, _}) -> true;
isMarked(_) -> false.

isInspected('*') -> true;
isInspected(_) -> false.

% Wähle eine beliebige markierte, aber noch nicht inspizierte Ecke Vi
% und inspiziere sie wie folgt (Berechnung des Inkrements)
step2b(G) ->
  % Take the first node of the queue (marked but not inspected)
  Node = getMarkedNotInspectedNode(util:getglobalvar('Queue')),
  {inspect(G, Node), Node}.

getMarkedNotInspectedNode([]) -> throw('Empty List (getMarkedNotInspectedNode)');
getMarkedNotInspectedNode([H|_]) -> H.


processEdges(G, _, []) -> G;
% (Vorwärtskante) Für jede Kante eij ∈ O(vi) mit unmarkierter
% Ecke vj und f(eij) < c(eij) markiere vj mit (+vi, δj), wobei δj
% die kleinere der beiden Zahlen c(eij) − f(eij) und δi ist
processEdges(G, V1, [V1, V2 | T]) ->
  Mark = adtgraph:getValV(G, V2, 'Marke'),
  case isMarked(Mark) of
    true -> processEdges(G, V1, T);
    false ->
      F = adtgraph:getValE(G, V1, V2, 'flow'),
      C = adtgraph:getValE(G, V1, V2, 'weight'),
      case F < C of
        true ->
          {_, _, DeltaI} = adtgraph:getValV(G, V1, 'Marke'),
          G_ = mark(G, V2, {'+', V1, min(C - F, DeltaI)}),
          processEdges(G_, V1, T);
        false ->
          processEdges(G, V1, T)
      end
  end;
% (Rückwärtskante) Für jede Kante eji ∈ I(vi) mit unmarkierter
% Ecke vj und f(eji) > 0 markiere vj mit (−vi, δj), wobei δj die
% kleinere der beiden Zahlen f(eji) und δi ist
processEdges(G, V1, [V2, V1 | T]) ->
  Mark = adtgraph:getValV(G, V2, 'Marke'),
  case isMarked(Mark) of
    true -> processEdges(G, V1, T);
    false ->
      F = adtgraph:getValE(G, V2, V1, 'flow'),
      case F > 0 of
        true ->
          {_, _, DeltaI} = adtgraph:getValV(G, V1, 'Marke'),
          mark(G, V2, {'-', V1, min(F, DeltaI)});
        false -> processEdges(G, V1, T)
      end
  end.

% Falls s markiert ist, gehe zu 3. (step3), sonst zu 2.(a) (step2a)
step2c(G) ->
  Marke = adtgraph:getValV(G, util:getglobalvar('S'), 'Marke'),
  isMarked(Marke).

%%% Vergrößerung der Flussstärke
% Bei s beginnend lässt sich anhand der Markierungen der gefundene vergrößernde
% Weg bis zur Ecke q rückwärts durchlaufen. Für jede Vorwärtskante
% wird f(eij) um δs erhöht, und für jede Rückwärtskante wird f(eji)
% um δs vermindert. Anschließend werden bei allen Ecken mit Ausnahme
% von q die Markierungen entfernt. Gehe zu 2 (step2a)
step3(G, Vj, Nodes, Delta) ->
  {Sign, Vi, _} = adtgraph:getValV(G, Vj, 'Marke'),
  Debug = util:getglobalvar('Debug'),
  if
    Debug == true -> util:logging('VerGrWege.log', integer_to_list(Vj));
    true -> ok
  end,
  case Sign of
    '+' ->
      Flow = adtgraph:getValE(G, Vi, Vj, 'flow'),
      G_ = adtgraph:setAtE(G, Vi, Vj, 'flow', Flow + Delta),
      if
        Debug == true -> util:logging('VerGrWege.log', "<-");
        true -> ok
      end,
      step3(G_, Vi, lists:append(Nodes, [Vi]), Delta);
    '-' ->
      Flow = adtgraph:getValE(G, Vj, Vi, 'flow'),
      G_ = adtgraph:setAtE(G, Vj, Vi, 'flow', Flow - Delta),
      if
        Debug == true -> util:logging('VerGrWege.log', "->");
        true -> ok
      end,
      step3(G_, Vi, lists:append(Nodes, [Vi]), Delta);
    '/' ->
      if
        Debug == true -> util:logging('VerGrWege.log', " (" ++ integer_to_list(Delta) ++ ")\n");
        true -> ok
      end,
      {removeMarksInspections(G, adtgraph:getVertices(G)), [util:getglobalvar('S')] ++ Nodes}
  end.

removeMarksInspections(G, []) ->
  Q = util:getglobalvar('Q'),
  % Reset queue
  util:setglobalvar('Queue', [Q]),
  mark(G, Q, {'/', undefined, 'Infinity'});
removeMarksInspections(G, [H | T]) ->
  G_ = adtgraph:setAtV(G, H, 'Marke', nil),
  G__ = adtgraph:setAtV(G_, H, 'Inspektion', nil),
  removeMarksInspections(G__, T).

%%% HELPER %%%
graphToDot(G, Filename) ->
  File = lists:concat([Filename, '.dot']),
  % Delete existing file because printGFF won't override correctly
  file:delete(File),
  adtgraph:printGFF(G, Filename).

dotToSVG(Filename, Sleep) ->
  % Wait for the dot file to be written
  timer:sleep(Sleep),
  os:cmd(lists:concat(["dot -Tsvg ", Filename, ".dot > ", Filename, ".svg"])),
  erlang:display('SVG-Datei erstellt').