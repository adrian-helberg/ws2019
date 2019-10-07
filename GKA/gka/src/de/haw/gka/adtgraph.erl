%%%---------------------------------------------------------------------
%%% @author Adrian Helberg
%%% @copyright (C) 2019, <HAW Hamburg>
%%% Created : 26. Sep 2019
%%% * Abstrakter Graph-Datentyp
%%% * Fehlerbehandlung ist durch "Ignorieren" durchzufuehren, als haette
%%%   die Operaiton nicht stattgefunden
%%% * vertex: Ganze, positive Zahl als ID
%%% * vertexlist: Bei Kanten werden diese aufeinanderfolgend dargestellt
%%%   [v1, v2, v2, v4] beschreibt eine Kante zwischen v1 und v2 und
%%%   und eine Kante zwischen v2 und v4, jedoch nicht zwischen v2 und v2
%%% * Graphen sind gerichtet oder ungerichtet, ohne Mehrfachkanten
%%%---------------------------------------------------------------------
-module(adtgraph).
-author("AdrianHelberg").
-compile(export_all).

createG(d) -> util:setglobalvar(yndirected, d), [];
createG(ud) -> util:setglobalvar(yndirected, ud), [];
createG(_) -> nil.

addVertex(G, V) ->
  if util:type_is(integer) -> G++[V];
    true -> nil
  end.