-module(versuch1).

-export([start/0]).

start() ->
  test2([105,110,115,120,125,130,135,140,145,150]).
%%  test2([10,20,30,40,50,60,70,80,90,100]).

test1([]) -> ok;
test1([VH|VT]) ->
  gengraph:gengraph(60, 59, 59, 1, VH, 'versuch1.graph'),
  G = adtgraph:importG('versuch1',d),
  V = lists:sort(adtgraph:getVertices(G)),
  [Q|_] = V,
  [S|_] = lists:reverse(V),
  StartFF = get_timestamp(),
  fordfulkerson:fordfulkersonT(G, Q, S),
  EndFF = get_timestamp(),

  StartEK = get_timestamp(),
  edmondskarp:edmondskarpT(G, Q, S),
  EndEK = get_timestamp(),
  erlang:display({'FF', EndFF-StartFF, 'EK', EndEK-StartEK}),
  test1(VT).

test2([]) -> ok;
test2([VH|VT]) ->
  gengraph:gengraph(VH,VH-1,VH-1,1,1,'versuch2.graph'),
  G = adtgraph:importG('versuch2',d),
  V = lists:sort(adtgraph:getVertices(G)),
  [Q|_] = V,
  [S|_] = lists:reverse(V),

  StartFF = get_timestamp(),
  fordfulkerson:fordfulkersonT(G, Q, S),
  EndFF = get_timestamp(),

  StartEK = get_timestamp(),
  edmondskarp:edmondskarpT(G, Q, S),
  EndEK = get_timestamp(),
  erlang:display({'FF', EndFF-StartFF, 'EK', EndEK-StartEK}),
  test2(VT).

get_timestamp() ->
  {Mega, Sec, Micro} = os:timestamp(),
  (Mega*1000000 + Sec)*1000 + round(Micro/1000).