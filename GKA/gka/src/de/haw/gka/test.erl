-module(test).

-export([test/1]).

test(Filename) ->
  graphToDot(adtgraph:importG(Filename, d), Filename),
  dotToSVG(Filename, 500).

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