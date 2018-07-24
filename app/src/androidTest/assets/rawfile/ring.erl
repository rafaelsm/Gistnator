-module(ring).
-export([start/2]).

start(N, Rounds) ->
    io:format("Creating proc ring with ~p node(s)~n", [N]),
    io:format("Passing message around ~p time(s)~n", [Rounds]),
    Pid = self(),
    statistics(wall_clock),
    H = spawn(fun() -> loop(Pid, 1, N) end),
    H ! {pass, 1, Rounds},
    master(H),
    {_, Time} = statistics(wall_clock),
    io:format("Took ~p milliseconds~n",[Time]).

master(H) ->
    receive
	{pass, T, T} ->
	    H ! die;
	    %io:format("Round ~p of ~p finished~n", [T, T]);
	{pass, C, T} ->
	    %io:format("Round ~p of ~p finished~n", [C, T]),
	    H ! {pass, C+1, T},
	    master(H)
    end.

loop(MasterPid, N, N) ->
    bounce(MasterPid);
loop(MasterPid, I, N) ->
    NextPid = spawn(fun() -> loop(MasterPid, I+1, N) end),
    bounce(NextPid).

bounce(Pid) ->
    receive
	{pass, C, Msg} ->
	    Pid ! {pass, C, Msg},
	    bounce(Pid);
	die ->
	    Pid ! die
    end.