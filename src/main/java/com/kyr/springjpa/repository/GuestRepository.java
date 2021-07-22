package com.kyr.springjpa.repository;

import com.kyr.springjpa.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest , Long> {

    @Query(value = "select * from guest" , nativeQuery = true)
    List<Guest> findAll();

    @Query(value = "select * from guest where id = ?" , nativeQuery = true)
    Optional<Guest> findById(Long id);

    @Query(value = "delete from guest where id = ?" , nativeQuery = true)
    void deleteById(Long id);

    Guest save(Guest guest);
}
