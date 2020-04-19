package com.java.mydevops.utility;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	
	public UserNotFoundException(long user_id) {
		super(String.format("User is not found with id : '%s'", user_id));
    }
}