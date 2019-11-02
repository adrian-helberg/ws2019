%%%-------------------------------------------------------------------
%%% @author Main
%%% @copyright (C) 2019, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 10. Okt 2019 11:08
%%%-------------------------------------------------------------------
-module(test).
-author("Main").
-include_lib("eunit/include/eunit.hrl").

createG_wrong_input_test() -> ?assertEqual(adtgraph:createG(d), []).

createG_undirected_test() ->
  G = adtgraph:createG(ud),
  ?assertEqual(G, []),
  Alignment = util:getglobalvar(yndirected),
  ?assertEqual(Alignment, ud).

createG_directed_test() ->
  G = adtgraph:createG(d),
  ?assertEqual(G, []),
  Alignment = util:getglobalvar(yndirected),
  ?assertEqual(Alignment, d).

addVertex_test() ->
  % Empty
  ?assertEqual(adtgraph:addVertex([], 10), [{10, []}]),
  % One vertex
  ?assertEqual(adtgraph:addVertex([{1, []}], 10), [{10, []}, {10, []}]),
  % More than one vertex
  ?assertEqual(adtgraph:addVertex([{1, []}, {2, []}], 10), [{1, []}, {2, []}, {10, []}]),
  % Vertex with attribute
  ?assertEqual(adtgraph:addVertex([{1, ['weight', 5]}], 10), [{1, ['weight']}, {2, []}, {10, []}]),
  % One Edge
  ?assertEqual(adtgraph:addVertex([{1, []}, {2, []}, {1, 2, []}], 10), [{1, []}, {2, []}, {1, 2, []}, {10, []}]),
  % More than one edge
  ?assertEqual(adtgraph:addVertex([{1, []}, {2, []}, {3, []}, {1, 2, []}, {1, 3, []}], 10), [{1, []}, {2, []}, {3, []}, {1, 2, []}, {1, 3, []}, {10, []}]),
  % Edge with attribute
  ?assertEqual(adtgraph:addVertex([{1, []}, {2, []}, {1, 2, ['weight', 2]}], 10), [{1, []}, {2, []}, {1, 2, ['weight', 2]}, {10, []}]),
  % Already present
  ?assertEqual(adtgraph:addVertex([{1, []}, {2, []}], 1), [{1, []}, {2, []}]).