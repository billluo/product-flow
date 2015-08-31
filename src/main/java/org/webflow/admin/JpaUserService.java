package org.webflow.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webflow.admin.UserRepository;
import org.webflow.domain.User;

/**
 * This data service queries on user table and add/update/delete user
 * @author bluo
 *
 */
@Service("jpaUserService")
@Transactional
public class JpaUserService  {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findByName(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public User create(User user) {
		return userRepository.save(user);
	}

	public User findUserById(Long id) {
		return userRepository.findOne(id);
	}

	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email,password);
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
}
