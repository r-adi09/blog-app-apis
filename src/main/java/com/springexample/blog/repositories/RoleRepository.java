package com.springexample.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springexample.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
