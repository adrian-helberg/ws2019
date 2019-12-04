%%%-------------------------------------------------------------------
%%% Ford-Fulkerson algorithm
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(fordfulkerson).

-export([graphToDot/1, dotToPNG/1]).

fordfulkerson(Filename, Q, S) -> adtgraph:importG(Filename, d).

graphToDot(Filename) ->
  G = adtgraph:importG(Filename, d),
  adtgraph:printGFF(G, 'graph_01').

dotToPNG(Filename) ->  os:cmd(lists:concat(["dot -Tpng ", Filename , ".dot > ", Filename, ".png"])).