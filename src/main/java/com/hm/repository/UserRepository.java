package com.hm.repository;
 
import com.hm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
 
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
 
 
//package com.hm.repository;
//
//import com.hm.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByUsername(String username);
//}