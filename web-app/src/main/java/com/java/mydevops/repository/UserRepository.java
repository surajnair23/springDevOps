package com.java.mydevops.repository;

import com.java.mydevops.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value="select * from users c where c.user_name = :user_name",nativeQuery = true)
	//@Query(value="from users where user_name = :user_name",nativeQuery = true)
	User existOrNot(@Param("user_name") String user_name);
	
	@Query(value="select * from users u where u.email = :email",nativeQuery = true)
	User emailValidOrNot(@Param("email")String email);

}