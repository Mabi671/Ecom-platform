package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
	private @Id @GeneratedValue Long id;
	private String username;
	private String password;
	private String email;
	private String role;

	public User() {}

	public User(String name, String password, String email, String role) {
		this.username = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	public String getUsername() {
		return this.username;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
        return role;
	}

	public void setPassword(String password){
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(role, user.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, email, role);
	}

	@Override
	public String toString(){
		return id + " " + username + ", " + password;
	}
}
