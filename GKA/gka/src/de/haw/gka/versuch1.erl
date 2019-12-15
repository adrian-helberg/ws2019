-module(versuch1).

-export([start/0]).

start() ->
  test([10,20,30,40,50,60,70,80,90,100]).

test([]) -> ok;
test([VH|VT]) ->
  gengraph:gengraph(VH,VH-1,VH-1,1,10,'test.graph'),
  G = adtgraph:importG('test',d),
  V = lists:sort(adtgraph:getVertices(G)),
  [Q|_] = V,
  [S|_] = lists:reverse(V),
  erlang:display(['Anzahl Ecken',lists:flatlength(adtgraph:getVertices(G))]),
  erlang:display(['Anzahl Kanten',lists:flatlength(adtgraph:getEdges(G))]),

  StartFF = get_timestamp(),
  fordfulkerson:fordfulkersonT(G, Q, S),
  EndFF = get_timestamp(),

  StartEK = get_timestamp(),
  edmondskarp:edmondskarpT(G, Q, S),
  EndEK = get_timestamp(),
  erlang:display({'FF', EndFF-StartFF, 'EK', EndEK-StartEK}),
  test(VT).

get_timestamp() ->
  {Mega, Sec, Micro} = os:timestamp(),
  (Mega*1000000 + Sec)*1000 + round(Micro/1000).