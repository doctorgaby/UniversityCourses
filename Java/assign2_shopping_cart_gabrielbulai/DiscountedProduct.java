/**
 *  Subclass representing a  discounted product to be 
 *  purchased for the second assignment in DIT948, 2015 edition.
 *  It extends the <tt>Product<tt> class with two instance 
 *  variables
 */

public class DiscountedProduct extends Product {

	// Original product
	private final Product original;

	// Discount in percent (%)
	private final double discount;

	/**
	 * Construct a discounted product
	 * @param original
	 * @param discount
	 */
	public DiscountedProduct(Product original, double discount) {
	       // if the price can not be reduced you should print a message and
	       // terminate the program. Use "System.exit(0);" to terminate. 	
	       // 
		//original and discount object is instantiated
		super(original); 
		   this.original=original;
		   this.discount=discount;
		   if(original instanceof Buy2Take3Product){
		    	System.out.println("The product cannot be discounted twice");
		    	 System.exit(0);
		   }
	}

	/**
	 * Return the price of this discounted product
	 * @param cart shopping cart
	 * @return discounted price 
	 */
	public double getPrice(Cart cart) {
	       //calculates the percentage for the discount and returns the discounted price
		double discountedPrice=((original.getPrice(cart)-(original.getPrice(cart)*discount)/100));
	      return discountedPrice;
	}
	/**
	 * Return the string representation of the product
	 * Example: CD [discounted 20 %] 
	 */
	public String toString() {
	       // creates the string representing the product name following by its respective discount
		String representationString = original.getName()+" [product discounted by "+discount+" %]";
	       return representationString;
	}
}
