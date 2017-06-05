import static dit948.SimpleIO.*;

/**
 * @author Gabriel Bulai
 * @date November 11, 2015
 */
/**
 *  Class representing a shopping cart application for the
 *  second assignment in DIT948, 2015 edition.
 *  This is the main class for the application, interacting
 *  with the customer, adding to the cart different product to 
 *  be purchased and finally calculating the total amount to be payed
 */

public class Main {
	
	/**
	 *  Allows a shopkeeper to enter the details for a product
	 *  to be purchased by a customer 
	 *  @param cart the shopping cart of a given customer
	 */
	 public static void askCustomer(Cart cart){
		 // Code to read from console the product name, seller,
		 // price, number of products, discount and 
		 // if Buy2Take 3 applies. 			
	
		// variables declaration
		 String name;
		 String seller;
		 double price;
		 int prodNum;
		 double discount;
		 boolean buy2take3=false;
		 boolean discountApplied=false;
		 Product product;
		 DiscountedProduct discProduct;
		 Buy2Take3Product buyTwoTakeThree;
		 
		 //get/read input from console and assign them to the variables
		 System.out.println("Product name: ");
		 name = readString();
		 println("Seller: ");
		 seller = readString();
		 println("Price: ");
		 price = readDouble();
		 println("How many: ");
		 prodNum = readInt();
		 
		 System.out.println("Discount (enter 0 if no discount applies): ");
		 discount = readDouble();
		 if(discount!=0){
			 discountApplied = true;
			 
		 }
		 else if (discount==0){
			 discountApplied = false;
		 }
		 
		 System.out.println("Does Buy2Take3 apply? Y/N: ");
		 if(readChar()=='y' || readChar()=='Y'){
			 buy2take3=true;
			 readLine();
			
		 }
		 else {
			 buy2take3=false;
			 readLine();
		 }
		 
		 //Create the product and add one or more products to the cart
		 if(discountApplied==false && buy2take3==false){
			 product = new Product(seller,name,price);
			 if(prodNum==1){
				 cart.addProduct(product);}
			  else {
					 cart.addProduct(product, prodNum);
				 }
		 }
		     
		 if(discountApplied==true && buy2take3==false){
			 product = new Product(seller,name,price);
			 discProduct = new DiscountedProduct(product,discount);
			 if(prodNum==1){
				 cart.addProduct(discProduct);}
			  else {
					 cart.addProduct(discProduct, prodNum);
				 }
		 }
		 
		 if(discountApplied==false && buy2take3==true){
			 product = new Product(seller,name,price);
			 buyTwoTakeThree = new Buy2Take3Product(product);
			 if(prodNum==1){
				 cart.addProduct(buyTwoTakeThree);}
			  else {
					 cart.addProduct(buyTwoTakeThree, prodNum);
				 }
		 }
		 
		 if(discountApplied==true && buy2take3==true){
			 product = new Product(seller,name,price);
			 discProduct = new DiscountedProduct(product,discount);
			 buyTwoTakeThree = new Buy2Take3Product(discProduct);
			 }
			 
		// Then create a product of the correct type
		// and add it to the shopping cart	
	 }

	// Main method to interact with a customer 
	public static void main(String[] args) {
		 
		 println("Welcome to DIT958 shop");
		 println("What's your name?");
		 String customer = readLine();
		 println("Hi "+customer+". Please choose one of the following options:");
		 println("");
		 //create a new object cart
		 Cart cart = new Cart();
		 boolean bol=true;
		 //Implement the user interface here
		 // Ask the user to choose 0 (buy product) or 
		 // 1 (checkout), depending on  what they want to do
		 // See TestCases.txt for several examples
		 
		 while(bol){
			
			println("Enter 1 to buy a product");
		    println("Enter 0 to checkout and proceed with payment");
		    char answer = readChar();
		    switch(answer){
	        case '1':
	        	askCustomer(cart);
	        break;
	        case '0':
	        println(cart.toString());
			println("In total you have to pay "+ cart.totalPrice()+" SEK");
	        bol=false;
	        break;
	        default:
	        System.out.println("(Invalid choice, please type 1 to add product or 0 to checkout)");
	        break;
	        }
	       }
		 //Implement the user interface here
		 // Ask the user to choose 0 (buy product) or 
		 // 1 (checkout), depending on  what they want to do
		 // See TestCases.txt for several examples

	}
}
