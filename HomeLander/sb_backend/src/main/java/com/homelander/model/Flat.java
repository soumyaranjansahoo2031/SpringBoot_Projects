package com.homelander.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "flats")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flat {
	@Id
	private String id;
	private String name;
	private String joinCode;
	private List<String> memberIds;
	private String createdBy;
	
}
