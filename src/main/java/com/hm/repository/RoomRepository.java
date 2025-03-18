package com.hm.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hm.entity.Room;
import com.hm.entity.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
	
	@Query("SELECT r FROM Room r WHERE r.roomType.id = :roomTypeId AND r.isAvailable = true")
	List<Room> findAvailableRoomsByType(@Param("roomTypeId") Integer roomTypeId);

	List<Room> findByRoomType(RoomType roomType);

	List<Room> findByRoomTypeAndIsAvailableTrue(RoomType roomType);

	List<Room> findByLocation(String location);
}