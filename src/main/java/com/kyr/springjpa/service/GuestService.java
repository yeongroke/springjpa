package com.kyr.springjpa.service;

import com.kyr.springjpa.dto.GuestDto;
import com.kyr.springjpa.entity.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    List<Guest> findAll();

    Optional<Guest> findById(long id);

    void deleteById(Long id);

    <S extends Guest> S save(GuestDto guestDto);
}
