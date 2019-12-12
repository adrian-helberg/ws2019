-module(test).
-author("Main").
-compile(export_all).

test() ->
  G = adtgraph:importG('graph_01', d),
  G_ = skizze:mark(G, 23569, 10),
  skizze:getUnMarked(G_, [18119,12099,18119,23569]).

graphToDot(G, Filename) ->
  File = lists:concat([Filename, '.dot']),
  % Existierende Datei löschen, da printGFF Dateien nicht überschreibt
  file:delete(File),
  adtgraph:printGFF(G, Filename).

dotToPNG(Filename, Sleep) ->
  % Warte bis die dot-Datei geschrieben wurde
  timer:sleep(Sleep),
  os:cmd(lists:concat(["dot -Tpng ", Filename , ".dot > ", Filename, ".png"])).