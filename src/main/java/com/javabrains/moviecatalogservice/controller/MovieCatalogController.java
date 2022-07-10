package com.javabrains.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.javabrains.moviecatalogservice.model.CatalogItem;
import com.javabrains.moviecatalogservice.model.Movie;
import com.javabrains.moviecatalogservice.model.Ratings;
import com.javabrains.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MovieInfo movieInfo;

	@Autowired
	private UserRatingInfo userRatingInfo;
	
	@Value("${my.greeting}")
	private String myGreeting;
	
	Logger logger = LoggerFactory.getLogger(MovieCatalogController.class);

	//https://api.themoviedb.org/3/movie/550?api_key=0289fb882414b15ba9cd6f1a0dec2d37
	//CAN CHANGE THE ID AND GET DIFFERENT RESULTS
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog") no need of it now as both the service
	//has their own fallback methods now
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
    logger.info("Hi I am in getCatalog");
    
//		RestTemplate restTemplate = new RestTemplate();
//		List<Ratings> ratings = Arrays.asList(new Ratings("101", 4), new Ratings("201", 3), new Ratings("301", 5));
        UserRating ratings = userRatingInfo.getUserRating(userId);
		
		return ratings
				.getUserRating()
				.stream()
				.map(r -> {
			         return movieInfo.getCatalogItem(r);
		          })
				.collect(Collectors.toList());

//		return Collections.singletonList(
//				new CatalogItem("Transformer", "Good Movie", 4)
//				
//				) ;

	}
	
	@GetMapping("/externalProp/{externalProp}")
	public String testingExternalProperties(@PathVariable String externalProp) {
		return myGreeting;
	}
	
	
/*	
	public List<CatalogItem> getFallbackCatalog(@PathVariable String userId){
		return Arrays.asList(new CatalogItem("No movie","",0));
	}
*/
}
