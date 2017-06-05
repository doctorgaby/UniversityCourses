%%% Name:   Gabriel Marian Bulai  
%%% e-mail: gusbulaga@student.gu.se
-module(assignment3).
-record(db_entry, {pid = undefined, key = undefined, value = undefined}).

%% DO NOT CHANGE THE EXPORT STATEMENT!
-export([start/0, init/0, stop/0, store/2, fetch/1, flush/0,
         task/1, dist_task/1, pmap/2, faulty_task/1
        ]).
%% End of no-change area

start() -> %% Start the server returning the Pid
case whereis(sts) of
  undefined ->  register(sts,spawn(assignment3,init,[])),
                {ok,whereis(sts)};
  Pid -> {ok,Pid} 
end.

stop() -> %% If the process is registered -> stop the server
          %% if it is not registered -> inform the user. 
case whereis(sts) of 
  undefined -> already_stopped;
  Pid -> true = exit(Pid,stopp), stopped
end.

store(Key, Value) ->
  case whereis(sts) of
        undefined -> no_value;
        Pid -> Pid ! {self(),store,Key,Value},
        receive
          Result-> Result                  
                  end
   end.

fetch(Key) ->
  case whereis(sts) of
        undefined -> no_value;
      Pid -> Pid ! {self(),fetch,Key},
       receive
         Result -> Result 
      end
end.
            
flush() ->
case whereis(sts) of
      undefined -> no_value;
    Pid -> Pid ! {self(),flush},
      receive
        Result -> Result 
     end
end.

init() -> loop([]).
loop(Db) ->
    receive 
      {Pid,store,Key,Value} ->
        case fetch(Pid,Key,Db) of
          {ok,PreviousValue} ->
             TempDb = lists:delete(#db_entry{pid=Pid,key=Key,value=PreviousValue},Db),
             NewDb = [#db_entry{pid=Pid,key=Key,value=Value} |TempDb];
                %Pid ! {ok,PreviousValue},
                %ok;
   {error,not_found} ->
      PreviousValue = no_value,
      NewDb = [#db_entry{pid=Pid,key=Key,value=Value} |Db]
                %Pid ! {ok,PreviousValue},
               % loop(NewDb). 
            end,
            %NewDb = [#db_entry{pid=Pid,key=Key,value=Value} |Db],
            Pid ! {ok,PreviousValue},
            loop(NewDb); 

  {Pid,fetch,Key} -> Pid ! fetch(Pid,Key,Db), loop(Db);

  {Pid,flush} -> NewDb = flush(Pid,Db,[]), Pid ! {ok,flushed}, loop(NewDb)
    end,
       loop(Db).

 fetch(Pid, Key, [H|T]) ->
        case H of
   #db_entry{pid=Pid, key=Key}-> {ok, H#db_entry.value};
                           H -> fetch(Pid,Key,T)
    end;
fetch(_,_,[]) -> {error,not_found}.

flush(Pid,[H|T],Db) ->
    case H of
  #db_entry{pid=Pid} -> flush(Pid,T,Db);
                   H -> flush(Pid,T,[H|Db])
    end;

flush(_,_,Db) -> Db.                    

%%
%% Problem 2
%%

%% Do not change the following two functions!
task(N) when N < 0; N > 100 -> exit(parameter_out_of_range);
task(N) -> timer:sleep(N * 2), 256 + 17 *((N rem 13) + 3).

faulty_task(N) when N < 0; N > 100 -> exit(parameter_out_of_range);
faulty_task(N) -> timer:sleep(N * 2), {_,_,X} = now(),
    case X < 100 of
        false ->
            256 + 17 *((N rem 13) + 3);
        true  ->
            throw(unexpected_error)
    end.
    
%% End of no-change area

dist_task(L) -> S = self(), 
 
 Pids = [spawn(fun () -> 
 S ! {self(), catch task(X)} end) 
 || X <- L],
 
[receive {P, Y} -> Y end || P <- Pids].
 
pmap(F,L) -> S = self(), 
 
 Pids = [spawn(fun () -> 
 S ! {self(), catch F (X)} end) || X <- L],
 
[receive {P, Y} -> Y end || P <- Pids].

%%
%% Problem 2
%% I do not have time to complete it.
