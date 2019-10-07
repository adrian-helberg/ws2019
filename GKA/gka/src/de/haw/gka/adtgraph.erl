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

% TODO
deleteVertex([], _) -> [];
deleteVertex([V,X|_], V) -> case X == nil of
                              % Vertex is not connected
                              true -> T;
                              % Vertex is connected
                              false -> nil
                            end;
deleteVertex([X,V|_], V) ->
deleteVertex([_,X|T], V) -> deleteVertex([X|T], V).

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

% TODO
deleteVertexP(G, L, V) -> case findVertex(G, V, 0) rem 2 == 0 of
                            % Index of vertex is even
                            true -> nil;
                            % Index of vertex is odd
                            false -> nil
                          end.