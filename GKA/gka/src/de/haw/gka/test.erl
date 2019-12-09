%%%-------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(test).
-author("Main").
-include_lib("eunit/include/eunit.hrl").

-export([test/0]).

test() ->
  X = 5,
  Result = if
    X == 5 -> X;
    true -> nil
  end,
  Result.

r([], T) -> T;
r(_, false) -> false;
r([H|T], _) ->
  {Name, _} = H,
  r(T, Name == 'Marke').

