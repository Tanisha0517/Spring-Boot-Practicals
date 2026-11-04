package com.rays.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.rays.dto.UserDTO;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	// Add User
	public long add(UserDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}

	// Update User
	public void update(UserDTO dto) {
		entityManager.merge(dto);
	}

	// Delete User
	public void delete(long id) {
		UserDTO dto = findByPk(id);

		entityManager.remove(dto);
	}

	// Find User By Primary Key
	public UserDTO findByPk(long id) {
		UserDTO dto = entityManager.find(UserDTO.class, id);

		return dto;
	}
}