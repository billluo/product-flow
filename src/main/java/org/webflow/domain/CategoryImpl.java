package org.webflow.domain;

import java.io.Serializable;


//import javax.persistence.Access;
//import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name ="Category")
public class CategoryImpl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8929461109918406559L;

	
	private Long id;

    private String name;

    private String description;
 
    public CategoryImpl(){}

    public CategoryImpl(String name, String description){
    	this.name=name;
    	this.description=description;
    }

    @Id
    @GeneratedValue
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}
