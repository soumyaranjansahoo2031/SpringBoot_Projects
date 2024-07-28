package com.example.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.example.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
	
	private String name;
	
	@NotEmpty
	private String contact;
	
	private String email;
	
	@Min(18)
	private Integer age;
	
	public User to() {
		return User.builder()
				.name(this.name)
				.contact_no(this.contact)
				.age(this.age)
				.email(this.email)
				.build();
	}

}
