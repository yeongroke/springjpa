package com.kyr.springjpa.service.impl;

import com.kyr.springjpa.dto.GuestDto;
import com.kyr.springjpa.entity.Guest;
import com.kyr.springjpa.repository.GuestRepository;
import com.kyr.springjpa.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class GuestServiceImpl implements GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override
    public Optional<Guest> findById(long id) {
        return guestRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        guestRepository.deleteById(id);
    }

    @Override
    public Guest save(GuestDto guestDto) {
        Guest guest = new Guest();

        guest.setName(guestDto.getName());
        guest.setEmail(guestDto.getEmail());
        guest.setPhoneNumber(guestDto.getPhoneNumber());
        guest.setPassword(guestDto.getPassword());

        return guestRepository.save(guest);
    }
}
