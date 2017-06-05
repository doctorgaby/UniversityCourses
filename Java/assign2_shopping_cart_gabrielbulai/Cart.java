/**
 *  Class representing a shopping cart for the
 *  second assignment in DIT948, 2015 edition.
 */

public class Cart  {
	
	// array of products (max 100 products)
	private final Product[] products = new Product[100];

	//position of the first free cell to add a product
	int position =0;
	
	/**
	 * Return the list of products
	 * @return
	 */
	public Product[] getProducts(){
		return products;
	}
	
	/**
	 * Add a product to the list
	 * @param product
	 */
	public void addProduct(Product product) {
		 products[position]= product;
	//loop through the array of products
	      ++position;
	}

	/**
	 * Add an array of products to the list
	 * @param products
	 */
	public void addProducts(Product[] products) {
	       // add the products to the array
		for(int i=position; i < products.length; i++){
	    	   this.addProduct(products[i]);
	       }
	}
	/**
	 * Adds a product several times
	 * @param product
	 * @param howManyTimes number of times to add product 
	 */
	public void addProduct(Product product, int howManyTimes) {
	       // checks the array and adds the products many times
		       for(int i=1; i <= howManyTimes; i++){
		    	   this.addProduct(product);
		       }
	}
	/**
	 * Calculate the total price
	 * @return
	 */
	public double totalPrice(){
		double price=0;
		for(int i=0; i < position; i++){
			//calculate the total price, checking for all the products in the array
			double sumPrice = products[i].getPrice(this);
			//add the sumPrice to the variable price and return it
			price += sumPrice;
		}
		return price;
	}
	/**
	 * String representation of products in a shopping 
	 * cart
	 * Example: 
	 * CD of Leonard Cohen 22.50 SEK. Sold by Javier
         * TV [discounted by 20.00%]    4000.00 SEK. Sold by Maria
	 */
	public String toString() {
		String result = "";
	       // loops through the array and returns the string "result", parsing the products at index i and the respective price
		for(int i=0; i < position; i++){
		String productString = products[i].toString()+" "+String.valueOf(products[i].getPrice(this))+" SEK. Sold by "+products[i].getSeller();
		result+="\n"+productString;
		}
		return result;
	}
}
