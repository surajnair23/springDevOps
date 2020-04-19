package com.java.mydevops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.mydevops.entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value="select * from playlists u where u.playlist_name = :playlist_name",nativeQuery = true)
	List<Playlist> getByName(@Param("playlist_name")String playlist_name);
    
    @Transactional
    @Modifying
    @Query(value="delete from playlists p where p.id = :idplst",nativeQuery = true)
    int delPlay(@Param("idplst")String idplst);
    
    @Transactional
    @Modifying
    @Query(value="update playlists p SET p.playlist_url = :plUrl where p.id =:plId",nativeQuery = true)
    int updateUrl(@Param("plUrl")String plUrl,@Param("plId")Long plId);
}