package com.example.model;

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import lombok.*;

//import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable{
	
//	private static final long serialVersionUID = 5644617730378584265L;

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Column(unique = true , nullable = false)
	private String contact_no;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private Integer age;
	
	@CreationTimestamp
	private Date createdOn;
	
	@UpdateTimestamp
	private Date updatedOn;
	
}


//<dependencies>
//<dependency>
//    <groupId>org.springframework.boot</groupId>
//    <artifactId>spring-boot-starter-security</artifactId>
//</dependency>
//</dependencies>
