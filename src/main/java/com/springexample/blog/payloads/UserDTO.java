package com.springexample.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import com.springexample.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDTO {
	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be 4 characters min")
	private String name;
	
	@NotEmpty
	@Email(message = "Email address invalid")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be 3 characters min and 10 characters max")
	@Pattern(regexp = "[0-9]+" )
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + "]";
	}

}
