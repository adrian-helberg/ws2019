%%%-------------------------------------------------------------------
%%% Ford-Fulkerson algorithm
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(fordfulkerson).
-export([fordfulkerson/3]).

fordfulkerson(Filename, Q, S) ->
  % Graphen aus Datei einlesen
  G = adtgraph:importG(Filename, d),
  %%% INITIALISIERUNG %%%
  % 1. Weise allen Kanten f(eij) := 0 zu
  Edges = adtgraph:getEdges(G),
  G_ = initialize(G, Edges, 'flow', 0),
  graphToDot(G_, 'test'),
  dotToPNG('test', tuple_size(G_) * 100),
  tuple_size(G_).

initialize(G, [], _, _) -> G;
initialize(G, [H1, H2|T], Name, Value) ->
  G_ = adtgraph:setAtE(G, H1, H2, Name, Value),
  initialize(G_, T, Name, Value).

graphToDot(G, Filename) ->
  File = lists:concat([Filename, '.dot']),
  % Existierende Datei löschen, da printGFF Dateien nicht überschreibt
  file:delete(File),
  adtgraph:printGFF(G, Filename).

fileToDot(Filename) ->
  G = adtgraph:importG(Filename, d),
  adtgraph:printGFF(G, 'graph_01').

dotToPNG(Filename, Sleep) ->
  % Warte bis die dot-Datei geschrieben wurde
  timer:sleep(Sleep),
  os:cmd(lists:concat(["dot -Tpng ", Filename , ".dot > ", Filename, ".png"])).

decompile(BeamFileName) ->
  file:write_file("./decompile", io_lib:fwrite("~p.\\n", [beam_disasm:file(BeamFileName)])).