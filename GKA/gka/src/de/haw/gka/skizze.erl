-module(skizze).
-author("Main").
-export([fordfulkerson/3, fordfulkersonT/3, fileToSVG/1]).

fordfulkerson(GraphOrFilename, Q, S) ->
  util:setglobalvar('Q', Q),
  util:setglobalvar('S', S),
  G = case util:type_is(GraphOrFilename) of
    atom -> adtgraph:importG(GraphOrFilename, d);
    tuple -> GraphOrFilename
  end,
  G_ = step1(G),
  {G__, Nodes} = loop(G_, []),
  step4(G__),
  Nodes.

fordfulkersonT(GraphOrFilename, Q, S) ->
  util:setglobalvar('Q', Q),
  util:setglobalvar('S', S),
  G = case util:type_is(GraphOrFilename) of
    atom -> adtgraph:importG(GraphOrFilename, d);
    tuple -> GraphOrFilename
  end,
  G_ = step1(G),
  {G__, Nodes} = loopT(G_, []),
  step4(G__),
  Nodes.

loop(G, Nodes) ->
  case step2a(G) of
    % Alle markierten Ecken wurden inspiziert
    true ->
      graphToDot(G, 'result'),
      dotToSVG('result', 500),
      {G, Nodes};
    false ->
      {G_, MarkedNotInspectedNode} = step2b(G),
      Incidents = adtgraph:getIncident(G_, MarkedNotInspectedNode),
      G__ = processEdges(G_, MarkedNotInspectedNode, Incidents),
      case step2c(G__) of
        true ->
          S = util:getglobalvar('S'),
          {_, _, Delta} = adtgraph:getValV(G__, util:getglobalvar('S'), 'Marke'),
          {G_Vergr, N} = step3(G__, S, [], Delta),
          loop(G_Vergr, N);
        false -> loop(G__, Nodes)
      end
  end.

loopT(G, Nodes) ->
  case step2a(G) of
    % Alle markierten Ecken wurden inspiziert
    true -> {G, Nodes};
    false ->
      {G_, MarkedNotInspectedNode} = step2b(G),
      Incidents = adtgraph:getIncident(G_, MarkedNotInspectedNode),
      G__ = processEdges(G_, MarkedNotInspectedNode, Incidents),
      case step2c(G__) of
        true ->
          S = util:getglobalvar('S'),
          {_, _, Delta} = adtgraph:getValV(G__, util:getglobalvar('S'), 'Marke'),
          {G_Vergr, N} = step3(G__, S, [], Delta),
          loopT(G_Vergr, N);
        false -> loopT(G__, Nodes)
      end
  end.

%%% INITIALISIERUNG
step1(G) ->
  Edges = adtgraph:getEdges(G),
  G_ = initialize(G, Edges, 'flow', 0),
  mark(G_, util:getglobalvar('Q'), {'/', undefined, 'Infinity'}).

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2 | T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

mark(G, V, Value) -> adtgraph:setAtV(G, V, 'Marke', Value).
inspect(G, V) -> adtgraph:setAtV(G, V, 'Inspektion', '*').

%%% Inspektion und Markierung
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

step2b(G) ->
  Node = getMarkedNotInspectedNode(G, adtgraph:getVertices(G)),
  {inspect(G, Node), Node}.

getMarkedNotInspectedNode(_, []) -> nil;
getMarkedNotInspectedNode(G, [H | T]) ->
  Mark = adtgraph:getValV(G, H, 'Marke'),
  case isMarked(Mark) of
    % Ecke ist nicht markiert
    false -> getMarkedNotInspectedNode(G, T);
    % Ecke is markiert
    true -> Inspection = adtgraph:getValV(G, H, 'Inspektion'),
      case isInspected(Inspection) of
        % Ecke ist nicht inspiziert
        false -> H;
        % Ecke ist inspiziert
        true -> getMarkedNotInspectedNode(G, T)
      end
  end.

processEdges(G, _, []) -> G;
% Vorwärtskanten
processEdges(G, V1, [V1, V2 | T]) ->
  Mark = adtgraph:getValV(G, V2, 'Marke'),
  case isMarked(Mark) of
    % Ecke ist markiert
    true -> processEdges(G, V1, T);
    % Ecke ist unmarkiert
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
% Rückwärtskanten
processEdges(G, V1, [V2, V1 | T]) ->
  Mark = adtgraph:getValV(G, V2, 'Marke'),
  case isMarked(Mark) of
    % Ecke ist markiert
    true -> processEdges(G, V1, T);
    % Ecke ist unmarkiert
    false ->
      F = adtgraph:getValE(G, V2, V1, 'flow'),
      case F > 0 of
        true ->
          {_, _, DeltaI} = adtgraph:getValV(G, V1, 'Marke'),
          mark(G, V2, {'-', V1, min(F, DeltaI)});
        false -> processEdges(G, V1, T)
      end
  end.

step2c(G) ->
  Marke = adtgraph:getValV(G, util:getglobalvar('S'), 'Marke'),
  isMarked(Marke).

%%% Vergrößerung der Flussstärke
step3(G, Vj, Nodes, Delta) ->
  {Sign, Vi, _} = adtgraph:getValV(G, Vj, 'Marke'),
  case Sign of
    '+' ->
      Flow = adtgraph:getValE(G, Vi, Vj, 'flow'),
      G_ = adtgraph:setAtE(G, Vi, Vj, 'flow', Flow + Delta),
      step3(G_, Vi, lists:append(Nodes, [Vi]), Delta);
    '-' ->
      Flow = adtgraph:getValE(G, Vj, Vi, 'flow'),
      G_ = adtgraph:setAtE(G, Vj, Vi, 'flow', Flow - Delta),
      step3(G_, Vi, lists:append(Nodes, [Vi]), Delta);
    '/' -> {removeMarksInspections(G, adtgraph:getVertices(G)), [util:getglobalvar('S')] ++ Nodes}
  end.

removeMarksInspections(G, []) -> adtgraph:setAtV(G, util:getglobalvar('Q'), 'Inspektion', nil);
removeMarksInspections(G, [H | T]) ->
  Q = util:getglobalvar('Q'),
  case H of
    Q -> removeMarksInspections(G, T);
    _ ->
      G_ = adtgraph:setAtV(G, H, 'Marke', nil),
      G__ = adtgraph:setAtV(G_, H, 'Inspektion', nil),
      removeMarksInspections(G__, T)
  end.

%%% Der Wert von d ist optimal
step4(G) -> G.

%%% HELPER %%%
graphToDot(G, Filename) ->
  File = lists:concat([Filename, '.dot']),
  % Existierende Datei löschen, da printGFF Dateien nicht überschreibt
  file:delete(File),
  adtgraph:printGFF(G, Filename).

dotToSVG(Filename, Sleep) ->
  % Warte bis die dot-Datei geschrieben wurde
  timer:sleep(Sleep),
  os:cmd(lists:concat(["dot -Tsvg ", Filename, ".dot > ", Filename, ".svg"])).

fileToSVG(Filename) ->
  G = adtgraph:importG(Filename, d),
  adtgraph:printGFF(G, Filename),
  dotToSVG(Filename, 500).