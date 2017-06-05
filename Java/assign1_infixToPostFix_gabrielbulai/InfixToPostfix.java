/**
 * Program Description: 
 * RPN calculator that validates infix expressions and translate them to postfix expressions.
 */

import java.util.Scanner;

/**
 * @authors Gabriel Bulai
 *
 * @date October 12, 2015
 *
 */
public class InfixToPostfix {
	
	/**
	 * This subroutine/method/function takes as input a character c
	 * and returns an integer (0 or 1) corresponding to the priority of operator c
	 * -1  it returned whenever c is not an operator.
	 */
	
	static int p(char c){
		if ((c=='+') || (c=='-')) {
			return 0;
		}
		else if ((c=='/') || (c=='*')) {
			return 1;
		}
		else {
			return -1;
		} 
	}
	
	/**
	 * Description of the first subroutine: 
	 * the for loop check for each character of the input string
	 * and it evaluates the characters and places them to strings depending on the nature of the character	  
	 */

	static String infixToPostfix(String infix){
		String output = "";
		String tmp = "";
		
		//Code implemented for Task 1
		System.out.println("Please enter an arithmetic expression to evaluate ");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		
		for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);		  //Gets char from str at index of i
	        if(Character.isDigit(c)) {   //Gets character if character is a number
	        	output = output + c;	 //put character in output.
	        }
	        
	        else if(c == '('){ //if c equals a left parenthesis add to tmp
	        	tmp = c + tmp;
	        }
	        else if(c == ')') { //if c equals a right parenthesis 
	        	while(tmp.length() > 0 && tmp.charAt(0) != '(') {
	        		output += tmp.charAt(0);
	        		tmp = tmp.substring(1);
	        	}
	        	if(tmp.length() > 0 && tmp.charAt(0) == '(') tmp = tmp.substring(1);
	        	
	        }
	        else if(c == '+' || c == '-' || c == '*' || c == '/')  {
	        		while (!tmp.isEmpty() && p(c) <= p(tmp.charAt(0)) && tmp.charAt(0) != '('){
		        		output += tmp.charAt(0);
		        		tmp = tmp.substring(1);
		        	}
	        		tmp = c + tmp;
	        	}
	    }   

        while(tmp.length() > 0) {  
    		output += tmp.charAt(0);
    		tmp = tmp.substring(1);
    	}
		
//		if c_i is an operand: Transfer c_i to output.
//		if c_i is a left parentheses: Push c_i to tmp.
//		if c_i is a right parentheses: Pop elements from tmp and transfer
//		them to output until a left-parentheses
//		is met. Pop left-parentheses.
//		if c_i is an operator: Let the top tmp element be t. Pop and
//		transfer elements from infix to tmp
//		until:
//		p(t) < p(c_i) or
//		t is a left-parentheses or
//		tmp is empty.
        System.out.println("The RPN representation of your expression is " + output);
		return output;
	}
	
	/**
	 * Description of the second subroutine: 
	 * The postfix expression is being evaluated; if the character
	 * is a digit is added to tmp2 string, otherwise if it's an operator
	 * the proper calculation is made with the latest 2 elements(t1, t2) in tmp2 and
	 * the result is replaced with the string tmp2.
	 *  
	 */

	static int eval(String postfix){
		int  result = 0;
		
		//Code for the implementation of Task 2
		String tmp2="";
		
		for(int i= 0; i < postfix.length(); i++){
			char c = postfix.charAt(i);
			if(Character.isDigit(c)){
				tmp2 =  c + tmp2;
				//System.out.println("temp added " + tmp2);
			}
			else if(c == '+' || c == '-' || c == '*'|| c == '/'){
				   char first = tmp2.charAt(0);
				   char second = tmp2.charAt(1);
				   int t1 = Character.getNumericValue(first);
				   int t2 = Character.getNumericValue(second);
				   String resultString;
				   switch(c) {
				   case '*':
					   result = t1 * t2;
					   resultString = Integer.toString(result);
					   tmp2 = resultString + tmp2.substring(2,tmp2.length());
				         break;
				   case '+':
					   result = t1 + t2;
					   resultString = Integer.toString(result);
					   tmp2 = resultString + tmp2.substring(2,tmp2.length());
				         break;
				   case '-':
					   result = t2 - t1;
					   resultString = Integer.toString(result);
					   tmp2 = resultString + tmp2.substring(2,tmp2.length());
				         break;
				   case '/':
					   result = t2 / t1;
					   resultString = Integer.toString(result);
					   tmp2 = resultString + tmp2.substring(2,tmp2.length());
				         break;
				    default:
				    	System.out.println("not operator");
				    	break;
				   }
				   //System.out.println("temp is " + tmp2);
			}
		
	}
		System.out.println("The final result is " + result);
		return result;
		}	
	/**
	 * *
	 * i = 1
while i<= n
if v_i is an operand: Push v_i to tmp2.
if v_i is an operator: Apply v_i to the top two elements of
tmp2. Replace these by the result in tmp2.
i = i + 1
Output result from tmp2.
	 */
		
	
public static void main(String[] args) {
		
		String infix="";
		// Write  code to implement main task
				System.out.println("+***************************************+");
				System.out.println("*                                       *");
				System.out.println("*      Welcome to DIT948 Calculator     *");
				System.out.println("*                                       *");
				System.out.println("+***************************************+");
				
	// The result is printed to the user and everything is repeated whenever he/she wants to continue
				
	boolean gameon = true;
	while (gameon = true){
		System.out.println("Press \"E \" to exit or any other button to continue\n");
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();
		if(s.equalsIgnoreCase("E")) 
		{System.exit(0);	
		gameon = false;
		};
		
		//Subroutine infixToPostfix is called
		String postfix = infixToPostfix(infix);
		
		// Subroutine postfix is called
		int result = eval(postfix);
	}
}
}