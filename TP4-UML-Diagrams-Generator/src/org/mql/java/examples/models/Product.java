package org.mql.java.examples.models;

import org.mql.java.examples.enums.ProductCategory;

public class Product {

	private String name;
	private double price;
	private ProductCategory category;

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String name, double price, ProductCategory category) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public void applyReduction(Reduction reduction) {
		this.price -= reduction.getAmount();
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", category=" + category + "]";
	}

}
