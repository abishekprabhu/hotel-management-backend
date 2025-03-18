package com.hm.repository;

import com.hm.dto.AmenityDTO;
import com.hm.entity.Amenity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

	//List<Amenity> findByHotel_Hotel_id(Integer hotel_id);
	List<Amenity> findByHotel_Id(Integer hotelId);

}
