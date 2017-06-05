/**
 *  Class representing a product to be purchased for the
 *  second assignment in DIT948, 2015 edition.
 */
public class Product {
	// Name of the seller declaration
	private final String seller;

	// Name of the product declaration
	private final String name;

	// Price of the product declaration
	protected final double price;

	/**
	 * Construct a new Product given the following parameters
	 * @param seller
	 * @param name
	 * @param price
	 */	
	public Product(String seller, String name, double price) {
		this.seller = seller;
		this.name = name;
		this.price = price;
	}
	/**
	 * Construct a new Product from a given product
	 * @param original
	 */	
	public Product(Product original) {
	// Use the constructor implemented above
		//call the objects in the constructor
		this.seller = original.seller;
		this.name = original.name;
		this.price = original.price;
	}

	/**
	 * Return the seller of  this product
	 * @return seller
	 */	
	public final String getSeller() {
		return this.seller;
	}

	/**
	 * Return the name of  this product
	 * @return name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Return the price of  this product
	 * @param cart
	 * @return price
	 */	
	public double getPrice(Cart cart) {
		return this.price;
	}

	/**
	 * Returns true if the price of this product
	 * can be reduced
	 * @return 
	 */	
	public boolean canBeReduced() {
		return true;
	}

	/**
	 * Return the name of the product
	 */
	public String toString() {
		return this.getName();
	}
}
