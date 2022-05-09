package com.example.test.simpleCrudProject.Repo;

import com.example.test.simpleCrudProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("Select u from User u where u.name like %:name%")
    List<User> findUserByName(String name);

    @Query("select u from User u where u.name like %:key% or u.email like %:key%")
    List<User> findUserByString(String key);



}
