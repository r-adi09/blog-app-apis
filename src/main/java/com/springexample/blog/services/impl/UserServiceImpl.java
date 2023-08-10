package com.springexample.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springexample.blog.exceptions.*;
import com.springexample.blog.config.AppConstants;
import com.springexample.blog.entities.Role;
import com.springexample.blog.entities.User;
import com.springexample.blog.payloads.UserDTO;
import com.springexample.blog.repositories.RoleRepository;
import com.springexample.blog.repositories.UserRepo;
import com.springexample.blog.services.UserService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.userToDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

		return this.userToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDTOs= users.stream().map(user->this.userToDTO(user)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDTO userDTO) {
		
//		System.out.println("HEllo");
		User user = this.modelMapper.map(userDTO, User.class);
		
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		return user;
		
	}
	
	public UserDTO userToDTO(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());
		return userDTO;
		
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		// TODO Auto-generated method stub
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDTO.class);
	}

}
