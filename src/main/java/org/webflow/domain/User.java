package org.webflow.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A user who can make order.
 */
@Entity
@Table(name = "Customer")
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6290068934936706779L;

	private String username;
	private String firstName;
	private String lastName;
	private Date dob;
	private String cardNumber;
	private String email;	
	private String password;
	private String petName;

    public User() {}

    public User(String username, String password, String firstName) {
	this.username = username;
	this.password = password;
	this.firstName = firstName;
	
    }
    
    @NotEmpty
    @Id
    public String getUsername() {
	return username;
    }
    
    //@NotEmpty
    public void setUsername(String username) {
	this.username = username;
    }
   
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getPetName() {
		return petName;
	}

    @Override
    public String toString() {
	return "User(" + firstName + ")";
    }
}
