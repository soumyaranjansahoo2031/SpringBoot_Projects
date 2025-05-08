package com.homelander.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")// Maps this class to the "users" collection in MongoDB
@Data                          // Lombok: adds getters, setters, toString(), etc.
@NoArgsConstructor             // Lombok: adds a default constructor
@AllArgsConstructor            // Lombok: adds a constructor with all fields
public class User {
	@Id                        // Marks this field as the primary key (_id in MongoDB)
	private String id;
    private String name;
    private String email;
    private String password; // we have to hash this!
    private String phone;
    private String flatId;
//    private String flatRole;
}
