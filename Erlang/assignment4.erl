-module(assignment4).

% Please don't change this export list.
-export([calc_sup_start_link/0, calc_start_link/0, calc_add/2, calc_mul/2, calc_client/3, calc_test/0,
         semaphore_start_link/0, semaphore_acquire/1, semaphore_release/1, semaphore_test/0,
         area_test/0]).

% You may add your functions here.
-export([]).


% 1. Calc server

% The usual convention is to put each server in its own module,
% but here we put all our servers in one module.
% Start the supervisor of the calc server.
calc_sup_start_link() ->
  spawn_link(fun calc_sup/0).

calc_sup() ->
  process_flag(trap_exit, true),
  {ok, _Pid} = calc_start_link(),
  receive
    {'EXIT', _From, normal} ->
      ok; % Normal termination
          % Our server will never terminate normally,
          % so this is really redundant for now.
    {'EXIT', _From, _Reason} ->
      %io:format("Process ~p exited for reason ~p~n",[Pid,Reason]),
      calc_sup() % Crash: restart
  end.

% Start the server and register it when it is not registered.
% Crash otherwise.
calc_start_link() ->
  S = spawn_link(fun calc_loop/0),
  register(calc, S),
  {ok, S}.

calc_loop() ->
  receive
    {add, P, A, B} ->
      P ! {add_reply, A + B},
      calc_loop();
    {mul, P, A, B} ->
      {_, _, N} = now(),
      if N rem 5 =/= 0 -> ok end,
      P ! {mul_reply, A * B},
      calc_loop()
  end.

% Add two numbers
calc_add(A, B) ->
  calc ! {add, self(), A, B},
  receive
    {add_reply, C} -> C
  end.

% Multiply two numbers
calc_mul(A, B) ->
  calc ! {mul, self(), A, B},
  receive
    {mul_reply, C} -> C
    after 500 ->
        calc_mul(A, B)
  end.

% Compute (X * Y + 3) * Z 
calc_client(X, Y, Z) ->
  Q = calc_mul(X, Y),
  timer:sleep(500),
  R = calc_add(Q, 3),
  timer:sleep(500),
  calc_mul(R, Z).

% Requires that calc_sup_start_link() has been run.
calc_test() ->
  io:format("Running calc_client(2, 4, 5)~n"),
  R = calc_client(2, 4, 5),
  io:format("calc_client(2, 4, 5) returned ~p~n", [R]).


% 2. Downtime

% 3. Semaphore server

semaphore_start_link() ->
  spawn_link(fun () -> semaphore_loop(free) end).

semaphore_loop(free) ->
  receive
    {acquire, P} ->
      P ! acquired,
      semaphore_loop({occupied, P})
  end;
semaphore_loop({occupied, P}) ->
  receive
    {release, P} ->
      P ! released,
      semaphore_loop(free)
  end.

% Acquire the semaphore. When this succeeds,
% it means that the semaphore was free,
% and the current process is allowed to use
% the protected resource. If the semaphore
% is not free, this operation will keep
% waiting.
semaphore_acquire(S) ->
  S ! {acquire, self()},
  receive
    acquired -> ok
  end.

% Release the semaphore. Can only be called
% if the current process has locked the semaphore.
semaphore_release(S) ->
  S ! {release, self()},
  receive
    released -> ok
  end.

% Doing a few rounds of locking and unlocking.
semaphore_client1(S) ->
  timer:sleep(500),
  io:format("client1: acquiring semaphore~n"),
  semaphore_acquire(S),
  % Exclusive section starts
  io:format("client1: acquired semaphore~n"),
  timer:sleep(500),
  % Exclusive section ends
  semaphore_release(S),
  io:format("client1: released semaphore~n"),

  timer:sleep(500),
  io:format("client1: acquiring semaphore~n"),
  semaphore_acquire(S),
  % Exclusive section starts
  io:format("client1: acquired semaphore~n"),
  timer:sleep(500),
  io:format("client1: crashing...~n"),
  _ = 1/0, % Crash
  % Exclusive section ends
  semaphore_release(S),
  io:format("client1: released semaphore~n").

% Doing a few rounds of locking and unlocking.
semaphore_client2(S) ->
  timer:sleep(600),
  io:format("client2: acquiring semaphore~n"),
  semaphore_acquire(S),
  % Exclusive section starts
  io:format("client2: acquired semaphore~n"),
  timer:sleep(500),
  % Exclusive section ends
  semaphore_release(S),
  io:format("client2: released semaphore~n"),

  timer:sleep(500),
  io:format("client2: acquiring semaphore~n"),
  semaphore_acquire(S),
  % Exclusive section starts
  io:format("client2: acquired semaphore~n"),
  timer:sleep(500),
  % Exclusive section ends
  semaphore_release(S),
  io:format("client2: released semaphore~n").

% This test case does not perform clean up.
% You may end up with a running server and a client
% left by it.
semaphore_test() ->
  S = semaphore_start_link(),
  % We assume here that the clients are completely
  % independent from each other, so they cannot
  % be linked together.
  spawn(fun () -> semaphore_client1(S) end),
  spawn(fun () -> semaphore_client2(S) end).


% 4. Area server

% Server that provides constants.
% It starts giving wrong results after a number
% of queries.
consts_start_link() ->
  case whereis(consts) of
    undefined ->
      S = spawn_link(fun () -> consts_loop(0, consts_state()) end),
      register(consts, S),
      {ok, S};
    S ->
      {ok, S}
  end.

consts_state() ->
  [{pi, 3.14}, {half, 0.5}].

maybe_wrong(N, X) when N < 17 -> X;
maybe_wrong(_, _) -> blah.

consts_loop(N, L) ->
  receive
    {const, P, C} ->
      P ! {const_reply, maybe_wrong(N, proplists:get_value(C, L))},
      consts_loop(N+1, L)
  end.

consts_get(C) ->
  consts ! {const, self(), C},
  receive
    {const_reply, X} -> X
  end.


area_sup_start_link(Name) ->
  spawn_link(fun () -> area_sup(Name) end).

area_sup(Name) ->
  process_flag(trap_exit, true),
  {ok, Pid} = area_start_link(),
  register(Name, Pid),
  receive
    {'EXIT', _From, normal} ->
      ok; % Normal termination
          % Our server will never terminate normally,
          % so this is really redundant for now.
    {'EXIT', _From, _Reason} ->
      %io:format("Process ~p exited for reason ~p~n",[Pid,Reason]),
      area_sup(Name) % Crash: restart
  end.

% Compute areas of shapes, using constants from a constants server
area_start_link() ->
  S = spawn_link(fun () -> area_loop() end),
  {ok, S}.

area_loop() ->
  receive
    {circle, P, R} ->
      PI = consts_get(pi),
      P ! {circle_reply, PI * R * R},
      area_loop();
    {triangle, P, A, H} ->
      HALF = consts_get(half),
      P ! {triangle_reply, HALF * A * H},
      area_loop()
  end.

area_circle(S, R) ->
  S ! {circle, self(), R},
  receive
    {circle_reply, C} -> C
  end.

area_triangle(S, A, H) ->
  S ! {triangle, self(), A, H},
  receive
    {triangle_reply, C} -> C
  end.


area_client(Name, ResultTo) ->
  A1 = area_circle(whereis(Name), 10),
  timer:sleep(100),
  A2 = area_triangle(whereis(Name), 4, 5),
  ResultTo ! {result, A1 + A2}.

% Running this function repeatedly will might not work, because the old servers
% might be registered. Killing the shell process (to which the processes are linked)
% is a way to clean them up.
area_test() ->
  % We need a consts server and 2 area servers
  consts_start_link(),
  area_sup_start_link(area1),
  area_sup_start_link(area2),

  % This code runs a number of client processes, making sure that
  % two of them are running at a time. We start a new one each time
  % a result comes.
  Self = self(),
  spawn_link(fun () -> area_client(area1, Self) end),
  spawn_link(fun () -> area_client(area2, Self) end),

  Res = [ receive
            {result, N} ->
              spawn_link(fun () -> area_client(Name, Self) end),
              N
          end || _ <- lists:seq(1,5), Name <- [area1, area2]],

  Res2 = [receive {result, N} -> N end || _ <- [ok, ok] ],

  % The final result is the sum of all numbers we got.
  lists:sum(Res) + lists:sum(Res2).

