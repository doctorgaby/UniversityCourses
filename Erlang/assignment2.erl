-module(assignment2).

-export([sum/1, sum_interval/2, interval/2,
	 sum_interval2/2, adj_duplicates/1,
	 even_print/1, even_odd/1, even_print2/1,
	 normalize/1, normalize2/1,
         all_fruit/1, num_235/0,
         price/1, stretch_if_square/1, sum2/1, last/1,
         digitize/1, is_happy/1, all_happy/2,
         expr_eval/1, expr_print/1
        ]).


% 1. Basic functions, lists and tuples

% Do not modify this function
sum([])     -> 0;
sum([X|Xs]) -> X + sum(Xs).


sum_interval(N, M) when N < M ->
    N + sum_interval(N + 1, M);
sum_interval(N, M) when N == M -> N;
sum_interval(N, M) when N > M -> 0.

interval(N,M) when N > M -> [];
interval(N,M) -> [N | interval(1+N,M)].

sum_interval2(N, M) when N > M -> 0;
sum_interval2(N, M) -> sum(interval(N,M)).

adj_duplicates([N|[N|T]]) ->
[N|adj_duplicates([N|T])];
adj_duplicates([_|T]) -> adj_duplicates(T);
adj_duplicates([]) -> [].

even_print([]) -> ok;
even_print([H|T]) when H rem 2=:=0 -> io:format("~p~n", [H]), even_print(T);
even_print([_H|T]) -> even_print(T).

even_odd(N) when N rem 2 == 0 -> even;
even_odd(_) -> odd.

even_print2([]) -> ok;
even_print2(_L) -> even_print2([io:format("~p~n", [X]) ||X <- _L, even_odd(X) == even]).


normalize(List) -> [X / max_val(List) || X <- List].

max_val([H| T]) -> max_val(H, T).
max_val(H, []) ->H;
max_val (H, [T | T1]) when H >= T -> max_val(H, T1);
max_val (_, [T| T1]) -> max_val(T, T1).



%normalize2(_L) -> ok.
map(_ , []) -> [];
map(F , [H|T]) -> 
	[H/ F | map (F, T)].

normalize2(L) -> 
	map(max_val(L), L).


% 3. List comprehensions

all_fruit([]) -> [].
all_fruit(L) -> 
    [F || {F, N} <- L, _Y < interval(1, N)].

num_235() -> 
     M = interval(0, 100),
     [X || X <- M, X rem 2 =/= 0, X rem 3 =/= 0, X rem 5 =/= 0].


% 4. Refactoring

price({A, N}) ->
  if A == apple  -> 11 * N;
     A == orange -> 15 * N + 2 end;
price({A, B, N}) ->
  if A == banana ->
       if B == costarica -> 8 * N;
          B == equador   -> 9 * N + 2 end end.


% Do not modify this function
rect_to_square({rect, A, A}) -> {square, A};
rect_to_square({rect, _, _}) -> not_square.

stretch_if_square(R) ->
  X = rect_to_square(R),
  if X == not_square -> R;
     true -> {square, S} = X,
             {rect, S, S*2}
  end.

sum2(L) -> 
    case L of
      [H | T] ->
        H + sum(T);
      [] -> 0
    end.

last(Xs) ->
  if length(Xs) == 1 -> hd(Xs);
     true            -> last(tl(Xs))
  end.

% 5. Digitify a number 
digitize(N) when N > 0 -> digitize1(N, []).
digitize1(N, A) when N > 0 -> digitize1(N div 10, [N rem 10| A]);
digitize1(N, A) when N == 0 -> A.

% 6. Happy numbers
is_happy(N) ->
    case M = sum(square(digitize(N))) of
	1 -> true;
	4 -> false;
	_ -> is_happy(M)
    end.

%all_happy(_N, _M) -> ok.
all_happy(N,M) -> List = interval(N,M), [X || X <- List, is_happy(X) == true].

square([H|T]) -> [H*H|square(T)];
square([]) -> [].   

reverse([]) -> [];
reverse([H|T]) -> reverse(T) ++ [H].


% 7. Expressions

%expr_eval(_Expr) -> ok.
expr_eval({num,N}) -> N;
expr_eval({plus, N, M})  -> expr_eval(N) + expr_eval(M);
expr_eval({minus, N, M}) -> expr_eval(N) - expr_eval(M);
expr_eval({mul,N, M}) -> expr_eval(N)*expr_eval(M).

%expr_print(_Expr) -> ok.
expr_print({num, 0}) -> "0";
expr_print({num,N}) -> [X + 48 || X <- digitize(N)];
expr_print({plus, N, M})  -> "("++ expr_print(N) ++ "+" ++ expr_print(M)++")";
expr_print({minus, N, M}) -> "("++expr_print(N) ++ "-" ++ expr_print(M)++")";
expr_print({mul,N, M}) -> "("++expr_print(N) ++ "*" ++ expr_print(M)++")".

