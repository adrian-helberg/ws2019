%%%-------------------------------------------------------------------
%%% Ford-Fulkerson algorithm
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(fordfulkerson).
-compile(export_all).

fordfulkerson(Filename, Q, S) ->
  % Graphen aus Datei einlesen
  G = adtgraph:importG(Filename, d),
  %%% 1. INITIALISIERUNG %%%
  % Weise allen Kanten f(eij) := 0 zu
  Edges = adtgraph:getEdges(G),
  G_initialized = initialize(G, Edges, 'flow', 0),
  % Markiere q mit (undefiniert, inf)
  % Nutze für Unendlich ein Atom, da Number < Atom
  G_qMarked = mark(G_initialized, Q, 'Marke', {undefined, 'Infinity'}),
  %%% 2. INSPEKTION UND MARKIERUNG %%%
  % (a) Falls alle markierten Ecken inspiziert wurden, gehe nach 4.
  AllNodesMarked = areAllNodesMarked(G_qMarked, adtgraph:getVertices(G_qMarked), true),
% TODO: TEST
  Result = if
    % 4. Es gibt keinen vergrößernden Weg
    AllNodesMarked -> makeCut(G_qMarked, Q);
    % (b) Wähle eine beliebige7 markierte, aber noch nicht inspizierte Ecke vi und inspiziere sie
    true -> getMarkedNotInspectedNode(G_qMarked, adtgraph:getVertices(G_qMarked))
  end,
  G_qInspected = inspect(G_qMarked, Result),
  ForwardEdges = getFowardEdges(Q, adtgraph:getIncident(G_qInspected, Q), []).

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2|T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

mark(G, V, Name, Value) -> adtgraph:setAtV(G, V, Name, Value).

areAllNodesMarked(_, [], Marked) -> Marked;
areAllNodesMarked(_, _, false) -> false;
areAllNodesMarked(G, [H|T], _) ->
  Value = adtgraph:getValV(G, H, 'Marke'),
  areAllNodesMarked(G, T, checkValue(Value)).

checkValue(nil) -> false;
checkValue({_, Value}) -> Value > 0.

makeCut(G, Q) ->
  % Alle herausgehenden Kanten aus Q für den Schnitt nutzen
  Incidents = adtgraph:getIncident(G, Q),
  ForwardEdges = getFowardEdges(Q, Incidents, []),
  ForwardEdges.% TODO: ForwardEdges sind alle Vorwärtskanten der Quelle

getFowardEdges(_, [], Nodes) -> Nodes;
getFowardEdges(Q, [Q, V|T], Nodes) -> getFowardEdges(Q, T, lists:append(Nodes, [Q, V]));
getFowardEdges(Q, [_, _|T], Nodes) -> getFowardEdges(Q, T, Nodes).

getMarkedNotInspectedNode(_, []) -> {getMarkedNotInspectedNode, notok};
getMarkedNotInspectedNode(G, [H|T]) ->
  Mark = adtgraph:getValV(G, H, 'Marke'),
  MarkedNotInspected = if
    % Node ist nicht markiert
    Mark == nil -> getMarkedNotInspectedNode(G, T);
    % Node is markiert
    true -> Inspection = adtgraph:getValV(G, H, '*'),
      if
        % Node ist nicht inspiziert
        Inspection == nil -> H;
        % Node ist inspiziert
        true -> getMarkedNotInspectedNode(G, T)
      end
  end,
  MarkedNotInspected.

inspect(G, V) -> G_ = adtgraph:setAtV(G, V, '*', nil).

%%% HELPER %%%
graphToDot(G, Filename) ->
  File = lists:concat([Filename, '.dot']),
  % Existierende Datei löschen, da printGFF Dateien nicht überschreibt
  file:delete(File),
  adtgraph:printGFF(G, Filename).

dotToPNG(Filename, Sleep) ->
  % Warte bis die dot-Datei geschrieben wurde
  timer:sleep(Sleep),
  os:cmd(lists:concat(["dot -Tpng ", Filename , ".dot > ", Filename, ".png"])).