-module(zeit2m).
-export([zeitmessung/0,zeitmessung/1,zeitmessung/2]).

-define(StartEcke,1).

%-----------------------------------------------------------------------------
%-----------------------------------------------------------------------------
zeitmessung( ) -> io:format("\n zeitmessung(<d|ud>,<Anzahl Graphen graph-*.graph>)\n\n"),
			   c:flush().
zeitmessung(_) -> io:format("\n zeitmessung(<d|ud>,<Anzahl Graphen graph-*.graph>)\n\n"),
			   c:flush().
zeitmessung(Direction,Max) ->
		% Name der csv Datei
		FileName = util:attachStamp(results,csv),
		% Datei öffnen		
		Return = file:open(FileName, write),
		case Return of
			{ok, File} -> 
		% Titelzeile eintragen
				io:format(File, "~s~n",["graph;dijkstra;bellmannford;milliSec"]),
		% Zeitmessung vornehmen
				zeitmessung(1,Max,Direction,File),
				file:close(File);
			{error, Reason} -> 
				io:format("Fehler beim öffnen der Datei ~p\n\t ~p\n", [FileName,Reason]),
				io:format("Die Zeitmessung kann nicht durchgeführt werden.\n")
		end.

% Misst die Zeit der einzelnen Algorithmen
zeitmessung(I,Max,Direction,File) when I =< Max -> 
		% Name der Graphdatei erstellen
		GraphName = list_to_atom(lists:concat(["graph-",util:to_String(I)])),
		% Graph importieren
		Graph = adtgraph:importG(GraphName,Direction),
		% Zeitmessung vornehmen
		% Dijkstra
		StartDi = erlang:timestamp(),
			dijkstra:dijkstra(Graph,?StartEcke,Direction),
		EndeDi = erlang:timestamp(),
		DiffmsDi = util:float_to_int(timer:now_diff(EndeDi,StartDi)/1000),
		% Bellmann-Ford
		StartBe = erlang:timestamp(),
			bellmannford:bellmannford(Graph,?StartEcke,Direction),
		EndeBe = erlang:timestamp(),
		DiffmsBe = util:float_to_int(timer:now_diff(EndeBe,StartBe)/1000),
		% Zeit in Datei ausgeben
		io:format(File, "~s~n", [util:to_String(GraphName) ++ ";" ++ 
								 util:to_String(DiffmsDi) ++ ";" ++ 
								 util:to_String(DiffmsBe)]),
		zeitmessung(I+1,Max,Direction,File);						 
zeitmessung(_I,_Max,_Direction,_File) -> ok.
