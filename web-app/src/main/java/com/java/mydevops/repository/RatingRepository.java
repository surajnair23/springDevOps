package com.java.mydevops.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.mydevops.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value="select * from ratings u where u.song_name = :song_name and u.song_artist = :song_artist",nativeQuery = true)
    Set<Rating> getBySong(@Param("song_name")String song_name, @Param("song_artist")String song_artist);
    
}