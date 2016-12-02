package pens.ac.id.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pens.ac.id.model.Users;

public interface DaoUsers extends JpaRepository<Users, Long> {
	
	public Users getByEmail(String email);
	
}
