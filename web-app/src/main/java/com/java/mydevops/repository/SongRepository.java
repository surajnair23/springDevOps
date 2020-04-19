package com.java.mydevops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.mydevops.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

	@Query(value="select * from songs u where u.song_name = :song_name and u.song_artist= :song_artist",nativeQuery = true)
	List<Song> findSong(@Param("song_name") String song_name,@Param("song_artist") String song_artist);

	@Query(value="select * from songs u where u.id = :id ",nativeQuery = true)
	Song findId(@Param("id") long id);
	
}