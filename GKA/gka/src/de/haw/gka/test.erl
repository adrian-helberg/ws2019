%%%-------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(test).
-author("Main").
-include_lib("eunit/include/eunit.hrl").
-compile(export_all).

graphToDot(Filename) ->
  G = adtgraph:importG(Filename, d),
  adtgraph:printGFF(G, 'graph_01').

dotToPNG(Filename) ->  os:cmd(lists:concat(["dot -Tpng ", Filename , ".dot > ", Filename, ".png"])).

test() ->
  graphToDot('graph_01'),
  timer:sleep(1000),
  dotToPNG('graph_01').