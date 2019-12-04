%%%-------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(test).
-author("Main").
-include_lib("eunit/include/eunit.hrl").

-export([test/0]).

test() ->
  fordfulkerson:graphToDot('graph_01'),
  timer:sleep(1000),
  fordfulkerson:dotToPNG('graph_01').