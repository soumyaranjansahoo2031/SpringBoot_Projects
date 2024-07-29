package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

}
