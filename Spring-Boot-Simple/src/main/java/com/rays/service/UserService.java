package com.rays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.UserDAO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleService roleService;

	public long add(UserDTO dto) {

		RoleDTO role = roleService.findByPk(dto.getRoleId());

		if (role == null) {
			throw new RuntimeException("Invalid Role ID");
		}

		dto.setRoleName(role.getName());

		return userDAO.add(dto);
	}

	public void update(UserDTO dto) {

		RoleDTO role = roleService.findByPk(dto.getRoleId());

		if (role == null) {
			throw new RuntimeException("Invalid Role ID");
		}

		dto.setRoleName(role.getName());

		userDAO.update(dto);
	}

	public void delete(long id) {
		userDAO.delete(id);
	}

	public UserDTO findByPk(long id) {
		return userDAO.findByPk(id);
	}
}