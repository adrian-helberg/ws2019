%%%---------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%% Created : 26. Sep 2019
%%% Interne Repräsentation eines (vollständigen) Graphen:
%%% * nondirected:  [1,2,1,3,1,4,2,3,2,4,3,4]
%%% * directed:     [1,2,1,3,1,4,2,1,2,3,2,4,3,1,3,2,3,4,4,1,4,2,4,3]
%%% Zwei aufeinanderfolgende Knoten beschreiben eine Kante
%%% Auch Einzelne Knoten werden als Kante dargestellt ([1,nil,2,nil])
%%%   (ein Knoten mit id 1 und ein Knoten mit id 2, keine Kanten)
%%%---------------------------------------------------------------------
-module(adtgraph).
-author("AdrianHelberg").
-compile(export_all).

createG(d) -> util:setglobalvar(yndirected, d), [];
createG(ud) -> util:setglobalvar(yndirected, ud), [];
createG(_) -> nil.

addVertex(G, V) ->
  case is_integer(V) of
    true -> case hasVertex(G, V) of
              true -> G;
              false -> G++[V, nil]
            end;
    false -> G
  end.

% TODO: directed
deleteVertex([], _) -> [];
% Vertex is first head and not connected
deleteVertex([V, nil|T], V) -> deleteVertex(T, V);
% Vertex is second head and not connected
deleteVertex([nil, V|T], V) -> deleteVertex(T, V);
% Vertex is first head and not connected
deleteVertex([V, W|T], V) -> [nil, W|deleteVertex(T, V)];
% Vertex is second head and not connected
deleteVertex([W, V|T], V) -> [W, nil|T];
% Vertex is neither first, nor second head
deleteVertex([H|T], V) -> [H|deleteVertex(T, V)].


% --- Helper
hasVertex([], _) -> false;
hasVertex([V|_], V) -> true;
hasVertex([_|T], V) -> hasVertex(T, V).

hasEdge([], _, _) -> false;
hasEdge([V1, V2|_], V1, V2) -> true;
hasEdge([_|T], V1, V2) -> hasEdge(T, V1, V2).

findVertex([], _, _) -> nil;
findVertex([V|_], V, C) -> C;
findVertex([_|T], V, C) -> findVertex(T, V, C+1).

print(G) ->
  {ok, S} = file:open("graph.dot", [write]),
  io:format(S, "~s~n", [io:format("graph {\n~s\n}", [toString(G)])]),
  os:cmd("dot -Tpng graph.dot -o graph.png").

toString([H1, H2|T]) -> io:format("~b--~b", [H1, H2]).