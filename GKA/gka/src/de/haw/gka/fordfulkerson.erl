%%%-------------------------------------------------------------------
%%% Ford-Fulkerson algorithm
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%%-------------------------------------------------------------------
-module(fordfulkerson).

fordfulkerson(Filename, Q, S) -> adtgraph:importG(Filename, d).