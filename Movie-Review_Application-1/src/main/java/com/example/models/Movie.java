package com.example.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable =false)
	private String title;
	
	@Column(nullable =false)
	private Genre genre;
	
	private double rating;
	
	private List<String> reviews;
	
	// -----------------------------
//	private String length;
//	
//	private List<String> cast;
//	
//	private String director;
}
//Movie
//=================
//	1) Id
//	2) title
//	3) genre
//	4) rating
//	5) <reviews>
//
//	6) length
//	7) cast
//	8) director
//	
//	9) family_movie
//	10) audience_type
