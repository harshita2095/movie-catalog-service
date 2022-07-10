package com.javabrains.moviecatalogservice.model;

import java.util.List;

public class UserRating {
	
	private String userId;
	public UserRating(String userId, List<Ratings> userRating) {
		super();
		this.userId = userId;
		this.userRating = userRating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private List<Ratings> userRating;
	
	public UserRating() {
		
	}

	public List<Ratings> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Ratings> userRating) {
		this.userRating = userRating;
	}

}
