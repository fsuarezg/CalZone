package com.vub.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vub.model.Floor;

/**
 * 
 * @author Sam
 *
 */
@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {
	@Query(value="SELECT f FROM Floor f INNER JOIN f.building b INNER JOIN b.institution i WHERE f.floor = :floor AND b.buildingName = :building AND i.institutionName = :institution")
	public Floor getFloor(@Param("floor") int floor, @Param("building") String building, @Param("institution") String institution);
}