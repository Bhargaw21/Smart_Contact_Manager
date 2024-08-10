package com.scm.services.impln;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.entities.contact;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

import lombok.var;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public contact save(contact Contact) {
       String contactId = UUID.randomUUID().toString();
       Contact.setId(contactId);
       return contactRepo.save(Contact);
    }

       @Override
    public contact update(contact Contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<contact> getAll() {
       return contactRepo.findAll();
    }

    @Override
    public contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id "+ id));
    }

    @Override
    public void delete(String id) {
        var Contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id "+ id));
        contactRepo.delete(Contact);
    }


    @Override
    public List<contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

  /*   @Override
    public Page<contact> getByUser(User user , int page , int size , String direction, String sortBy) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size ,sort);
        return contactRepo.findByUser(user,pageable);
    }*/

    @Override
    public Page<contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value '" + direction + "' for orders given; Has to be either 'desc' or 'asc' (case insensitive)");
        }

        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }


    @Override
    public Page<contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
      
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size, sort);
        return contactRepo.findByUserAndNameContaining(user,nameKeyword, pageable);

    }

    @Override
    public Page<contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size, sort);
        return contactRepo.findByUserAndEmailContaining(user,emailKeyword,pageable);
    } 

    @Override
    public Page<contact> searchByPhoneNumber(String PhoneNumberKeyword, int size, int page, String sortBy,
            String order, User user) {
                Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
                var pageable = PageRequest.of(page,size, sort);
                return contactRepo.findByUserAndPhoneNumberContaining(user,PhoneNumberKeyword, pageable);
    }
}
