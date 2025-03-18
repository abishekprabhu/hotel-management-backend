package com.hm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.entity.RoomType;


@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
