%%%-------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(test).
-author("Main").
-include_lib("eunit/include/eunit.hrl").

-export([test/0]).

test() ->
  L = [{'Marke', {'X'}},{'Marke', {'Y'}},{'Marke', {'Z'}}, {'Test', {'T'}}],
  r(L, true).

r([], T) -> T;
r(_, false) -> false;
r([H|T], _) ->
  {Name, _} = H,
  r(T, Name == 'Marke').