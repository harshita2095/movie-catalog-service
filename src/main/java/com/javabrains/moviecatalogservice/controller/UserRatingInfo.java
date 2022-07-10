package com.javabrains.moviecatalogservice.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javabrains.moviecatalogservice.model.Ratings;
import com.javabrains.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@Service
public class UserRatingInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://MOVIE-RATINGS-DATA-SERVICE/ratingsdata/users/" + userId, UserRating.class);
	}
	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(new Ratings("0", 2)));
		return userRating;
	}

}
