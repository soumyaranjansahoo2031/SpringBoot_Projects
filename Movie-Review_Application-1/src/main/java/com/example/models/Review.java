//package com.example.models;
//
//import java.util.Date;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.*;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Review {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int Id;
//	
//	private int movieId;
//	
//	@Column(nullable = false)
//	private double rating;
//	
//	private String movieReview;
//	
//	private Movie movie;
//	
//	@CreationTimestamp
//	private Date createdAt;
//	
//	@UpdateTimestamp
//	private Date updatedAt;
//}
////Review
////=================
////	1) Id
////	2) movieId (foreign Key)
////	3) rating
////	4) review
////
////	1) userId
////	2) createdAt
////	3) updatedAt
