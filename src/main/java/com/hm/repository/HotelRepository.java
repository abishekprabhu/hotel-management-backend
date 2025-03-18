package com.hm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hm.dto.HotelDTO;
import com.hm.entity.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByName(String name);
    List<Hotel> findByDescriptionContaining(String description);
    List<Hotel> findByNameContainingIgnoreCase(String name);
    List<HotelDTO> getHotelsByDescription(String description);

    
//    @Query(value = """
//            SELECT h.* FROM hotel h
//            JOIN hotel_amenity ha ON h.hotel_id = ha.hotel_id
//            WHERE ha.amenity_id = :amenityId
//        """, nativeQuery = true)
//        List<Hotel> findHotelsByAmenityId(@Param("amenityId") Integer amenityId);

}