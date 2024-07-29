package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.MovieCreateRequest;
import com.example.models.Movie;
import com.example.repository.MovieRepository;

@Service
public class AdminService {
	
	@Autowired
	MovieRepository movieRepository;

	public Movie addMovie(MovieCreateRequest movieCreateRequest) {
		Movie movie = movieCreateRequest.toMovie();
		System.out.println(movie.getId());
		System.out.println(movie.getTitle());
		System.out.println(movie.getGenre());
		
		try {
			movieRepository.save(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}
	
}
