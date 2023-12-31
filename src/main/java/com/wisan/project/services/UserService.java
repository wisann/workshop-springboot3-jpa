package com.wisan.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wisan.project.entities.User;
import com.wisan.project.repositories.UserRepository;
import com.wisan.project.service.exceptions.ResourceNotFundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFundException(id));
	}

	public User insert(User obj) {
		return userRepository.saveAndFlush(obj);

	}

	public void delete(Long id) {
		try {
			if (userRepository.existsById(id)) {
				userRepository.deleteById(id);
			} else {
				throw new ResourceNotFundException(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Data integrity violation occurred.");

		}
	}

	public User update(Long id, User obj) {
		try{
			User entity = userRepository.getReferenceById(id);
		updateData(entity, obj);
		return userRepository.saveAndFlush(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFundException(id);
		}
		
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());

	}

}
