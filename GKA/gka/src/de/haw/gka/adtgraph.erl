%%%---------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%% Created : 26. Sep 2019
%%% Interne Repräsentation eines (vollständigen) Graphen:
%%% * nondirected:  [
%%%                   {1, ['size', 5, 'capacity', 3]}, {2, ['size', 2]}, {3, []}, (Knoten)
%%%                   {1, 2, ['weight', 7, 'pass', 1]}, {1, 3, []}, {2, 3, ['weight', 8]} (Kanten)
%%%                 ]
%%% * directed:     [
%%%                   {1, ['size', 5, 'capacity', 3]}, {2, ['size', 2]}, {3, []}, (Knoten)
%%%                   {1, 2, ['weight', 7, 'pass', 1]}, {1, 3, []}, {2, 3, ['weight', 8]} (Kanten)
%%%                   {2, 1, ['weight', 1, 'pass', 3]}, {3, 1, []}, {3, 2, ['weight', 2]}
%%%                 ]
%%% TODO: directed
%%%---------------------------------------------------------------------
-module(adtgraph).
-author("AdrianHelberg").
-compile(export_all).

createG(d) -> util:setglobalvar(yndirected, d), [];
createG(ud) -> util:setglobalvar(yndirected, ud), [];
createG(_) -> nil.

addVertex(G, V) ->
  case hasVertex(G, V) of
    true -> G;
    false -> G++[{V, []}]
  end.

deleteVertex([], _) -> [];
deleteVertex([{V, _}|T], V) -> deleteVertex(T, V);
deleteVertex([{V, _, _}|T], V) -> deleteVertex(T, V);
deleteVertex([{_, V, _}|T], V) -> deleteVertex(T, V);
deleteVertex([H|T], V) -> [H|deleteVertex(T, V)].

addEdge(G, V1, V2) ->
  case V1 == V2 of
    true -> G;
    false -> case hasEdge(G, V1, V2) of
               true -> G;
               false -> [addVertex(addVertex(G, V1), V2)|{V1, V2, []}]
            end
  end.

deleteEdge([], _, _) -> [];
deleteEdge([{V1, V2, _}|T], V1, V2) -> deleteEdge(T, V1, V2);
deleteEdge([{V2, V1, _}|T], V1, V2) -> deleteEdge(T, V1, V2);
deleteEdge([H|T], V1, V2) -> [H|deleteEdge(T, V1, V2)].

% --- Helper
hasVertex([], _) -> false;
hasVertex([{V, _}|_], V) -> true;
hasVertex([_|T], V) -> hasVertex(T, V).

hasEdge([], _, _) -> false;
hasEdge([{V1, V2, _}|_], V1, V2) -> true;
hasEdge([{V2, V1, _}|_], V1, V2) -> true;
hasEdge([_|T], V1, V2) -> hasEdge(T, V1, V2).

print(G) ->
  {ok, S} = file:open("graph.dot", [write]),
  io:format(S, "~s~n", [io:format("graph {\n~s\n}", [toString(G)])]),
  os:cmd("dot -Tpng graph.dot -o graph.png").

toString([H1, H2|_]) -> io:format("~b--~b", [H1, H2]).