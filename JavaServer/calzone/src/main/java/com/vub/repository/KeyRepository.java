package com.vub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vub.model.Key;

/**
 * 
 * @author Sam
 *
 */
@Repository
public interface KeyRepository extends JpaRepository<Key, Integer> {
	@Query(value="SELECT k FROM Key k WHERE k.keyString = :key")
	public Key findKeyByKeyString(@Param("key") String keyString);
	
	@Query(value="SELECT k FROM Key k WHERE k.userId = :userId")
	public List<Key> findKeysAssignedToUser(@Param("userId") int userId);
}