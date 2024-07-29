package com.example.dto;

import com.example.models.Genre;
import com.example.models.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieCreateRequest {
	
	private String title;
	
	private Genre genre;
	
	public Movie toMovie() {
		return Movie.builder()
				.title(title)
				.genre(genre)
				.rating(0)
				.reviews(null)
				.build();
	}
	
}
//MovieCreateRequest
//=================
//	1) title
//	2) Genre
//	
//	3) length
//	4) cast
//	5) director
//	
