package com.javabrains.moviecatalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javabrains.moviecatalogservice.model.CatalogItem;
import com.javabrains.moviecatalogservice.model.Movie;
import com.javabrains.moviecatalogservice.model.Ratings;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Ratings r) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + r.getMovieId(), Movie.class);
		 return new CatalogItem(movie.getName(), movie.getDescription(), r.getRating());
	}
	public CatalogItem getFallbackCatalogItem(Ratings r) {
		return new CatalogItem("No movie found", "", r.getRating());
	}

}
