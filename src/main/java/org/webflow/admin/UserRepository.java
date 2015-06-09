package org.webflow.admin;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.webflow.domain.User;

public interface UserRepository extends CrudRepository<User,Long> {
	
	public User findByUsername(String username);
	
	public List<User> findByFirstNameLike(String firstName);

	public User findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

}
