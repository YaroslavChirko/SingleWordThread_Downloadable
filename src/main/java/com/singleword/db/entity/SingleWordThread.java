package com.singleword.db.entity;

public class SingleWordThread {
	private int id;
	private String name;
	
	public synchronized int getId() {
		return id;
	}
	public synchronized void setId(int id) {
		this.id = id;
	}
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SingleWordThread [name=" + name + "]";
	}
	
	
	
}
