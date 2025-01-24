package org.mql.java.examples.models;

import java.io.Serializable;

import org.mql.java.examples.annotations.CustomAnnotation;
import org.mql.java.examples.enums.Status;

@CustomAnnotation
public class User extends Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private Status status;
	private Address address;
	private ShoppingCart cart;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, int age, Status status, Address address) {
		super(name, age);
		this.status = status;
		this.address = address;
		this.cart = new ShoppingCart();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

}
