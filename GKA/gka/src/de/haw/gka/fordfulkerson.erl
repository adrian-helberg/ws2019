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
  Nodes = adtgraph:getVertices(G_qMarked),
  G_ = markAll(G, Nodes, 'Marke', 'X'),
%%  AllNodesAreMarked = areAllNodesMarked(G_, Nodes, true),
  erlang:display(G_),
  areAllNodesMarked(G_, Nodes, true).

%%  case AllNodesAreMarked of
%%    true ->
%%      % Ein Schnitt A(X,X') mit c(X,X') = d wird gebildet
%%      makeCut(G_qMarked, Q);
%%    false ->
%%  end,
%%  % (b) Wähle eine beliebige markierte, aber noch nicht inspizierte Ecke und inspiziere sie
%%  inspectMarkedNotInspectedNode().

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2|T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

markAll(G, [], _, _) -> G;
markAll(G, [H|T], Name, Value) ->
  G_ = adtgraph:setAtV(G, H, Name, Value),
  markAll(G_, T, Name, Value).

mark(G, V, Name, Value) -> adtgraph:setAtV(G, V, Name, Value).

areAllNodesMarked(_, [], Marked) -> Marked;
areAllNodesMarked(_, _, false) -> false;
areAllNodesMarked(G, [H|T], _) ->
  Attr = adtgraph:getValV(G, H, 'Marke'),
  erlang:display(Attr),
  if
    Attr == nil -> IsMarked = false;
    true -> Name = Attr,
      if
        Attr == 'Marke' -> IsMarked = true;
        true -> IsMarked = false
      end
  end,
  areAllNodesMarked(G, T, IsMarked).

makeCut(G, Q) ->
  % Alle herausgehenden Kanten aus Q für den Schnitt nutzen
  Incidents = adtgraph:getIncident(G, Q),
  ForwardEdges = getFowardEdges(Q, Incidents, []),
  ForwardEdges.% TODO: ForwardEdges sind alle Vorwärtskanten der Quelle

getFowardEdges(_, [], Nodes) -> Nodes;
getFowardEdges(Q, [Q, V|T], Nodes) -> getFowardEdges(Q, T, lists:append(Nodes, [Q, V]));
getFowardEdges(Q, [_, _|T], Nodes) -> getFowardEdges(Q, T, Nodes).

inspectMarkedNotInspectedNode() ->
  inspectForwards(),
  inspectBackwards().

inspectForwards() ->
  0.

inspectBackwards() ->
  0.

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

%%fileToDot(Filename) ->
%%  G = adtgraph:importG(Filename, d),
%%  adtgraph:printGFF(G, 'graph_01').
%%
%%decompile(BeamFileName) ->
%%  file:write_file("./decompile", io_lib:fwrite("~p.\\n", [beam_disasm:file(BeamFileName)])).