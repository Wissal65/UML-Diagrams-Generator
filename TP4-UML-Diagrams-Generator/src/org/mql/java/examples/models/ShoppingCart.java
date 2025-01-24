package org.mql.java.examples.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<Product> products;

	public ShoppingCart() {
	}

	public ShoppingCart(List<Product> products) {
		this.products = new ArrayList<>();
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public void removeProduct(Product product) {
		products.remove(product);
	}

	public List<Product> getProducts() {
		return products;
	}
}
