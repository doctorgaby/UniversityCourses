-module(assignment1).

-export([measure/3, any_2_equal/3, even_odd/1,
         range_overlap/2, rect_overlap/2,
         print_0_n/1, print_n_0/1, print_sum_0_n/1,
         lg/1, count_one_bits/1,
         print_bits/1, print_bits_rev/1]).

measure(X, N, N) ->     
  X + 1;
measure(X, N, M) -> 
  X + N - M.

% checks all possible cases of 2 equal integers and prints true if they are equal, false if they are different.
any_2_equal(A, A, _) -> true;
any_2_equal(A, _, A) -> true;
any_2_equal(_, A, A) -> true;
any_2_equal(_, _, _) -> false.

% prints even if the remainder is 0, otherwise print odd.
even_odd(A) when A rem 2 == 0 -> even;
even_odd(_) -> odd.

%                                    
range_overlap({A1, A2}, {B1, B2}) when B1 >= A1, A2 > B1 ->
{overlap, {B1, min(A2, B2)}};
range_overlap({A1, A2}, {B1, B2}) when B1 =< A1, A1 < B2 ->
{overlap, {A1, min(A2, B2)}};
range_overlap(_, _) -> no_overlap.

rect_overlap(_, _) -> ok.

print_0_n(N, N) -> io:format("~p~n", [N]);
print_0_n(N, I) ->
  io:format("~p~n", [I]),
  print_0_n(N, I+1).

print_0_n(N) -> print_0_n(N, 0).

print_n_0(0) -> io:format("~p~n", [0]);
print_n_0(N) -> io:format("~p~n", [N]),
print_n_0(N-1).


print_sum_0_n(N, N, M) -> io:format("~p~n", [N]), M+N;
print_sum_0_n(N, I, M) ->
  io:format("~p~n", [I]),
  print_sum_0_n(N, I+1, I+M).
  
print_sum_0_n(N) -> 
  print_sum_0_n(N, 0, 0).

lg(0) -> 0;
lg(N) -> 1 + lg(N div 2).

count_one_bits(0) -> 0;
count_one_bits(N) ->
  (N rem 2) + count_one_bits(N div 2).

print_bits(0) -> ok;
print_bits(N) -> 
  print_bits(N div 2),
  io:format("~p~n", [N rem 2]).
 
print_bits_rev(0) -> ok;
print_bits_rev(N) -> 
  io:format("~p~n", [N rem 2]),
  print_bits_rev(N div 2).
  

