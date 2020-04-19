package com.java.mydevops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.mydevops.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	@Query(value="select * from Feedbacks u where u.id = :id ",nativeQuery = true)
	Feedback findId(@Param("id") long id);
	
}