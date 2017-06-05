import java.util.Objects;
/**
 *  Subclass representing a  product to be  purchased 
 *  (using the formula "buy 2 take 3") for the second 
 *  assignment in DIT948, 2015 edition.
 *  It extends the <tt>Product<tt> class with one instance 
 *  variable
 */

public class Buy2Take3Product extends Product {

	//original product
	private final Product original;
    
	/**
	 * Construct a Buy2Take3 product
	 * If the price of this product can not be reduced
	 * you should print a message to the user and terminate the 
	 * program
	 * @param original
	 */
	public Buy2Take3Product(Product original) {
	       // if the price can not be reduced you should print a message and
	       // terminate the program. Use "System.exit(0);" to terminate. 	
	       // code here
		 super(original);
		    this.original=original;
		    if(original instanceof DiscountedProduct){
		    	System.out.println("The product cannot be discounted twice");
		    	System.exit(0);
	}
	}
	/**
	 * Return false if the product price can not be
	 * reduced
	 * @return 
	 */
	public boolean canBeReduced() {
		// You can not discount the price of Buy2Take3 product
		return false;
	}

	/**
	 * Return the unit price of a product using the
	 * formula "Buy2Take3"
	 * @param cart shopping cart
	 * @return unit price 
	 */
	public double getPrice(Cart cart) {

		// calculate unit price of this product purchased
		// as Buy2Take3
		// code here
		double initOriginalPrice = original.price;
		int counter=0; //counter for theeProducts that is reset every time we reach 3
		int threeProducts=0; //counter of triplets
		int totalCounter = 0; //total number of products in the cart
		Product[] products= cart.getProducts();
		//check for all the products in the cart/array
		for(int i=0; i<cart.position; i++){
			//if the objects are equal
			if(Objects.equals(products[i].toString(),original.toString())){
				counter++;
				totalCounter++;
				if(counter==3){
					threeProducts++;
					counter=0;
				}
	}			
}
		//find the total Price by multiplying the original price and the number of products.
		double totalPrice = ((double)initOriginalPrice) * ((double)totalCounter);
		//calculate the discount if we have 3 products
		double discount = ((double)initOriginalPrice) * ((double)threeProducts);
		//calculate the difference=amount to pay.
		double discountedProduct = ((double)totalPrice)-((double)discount);
		//find the unit price dividing the amount to pay per the totalCounter 
		double unitPrice = ((double)discountedProduct)/((double)totalCounter);
		return unitPrice;
	}
	
}
