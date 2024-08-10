package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.entities.contact;


@Service
public interface ContactService {

    // save contact
    contact save(contact Contact);

    contact update(contact Contact);

    List<contact> getAll();

    contact getById(String id);

    void delete(String id);

    Page<contact> searchByName(String nameKeyword, int size , int page , String sortBy , String order , User user);

    Page<contact> searchByEmail(String emailKeyword , int size , int page , String sortBy , String order, User user);

    Page<contact> searchByPhoneNumber(String PhoneNumberKeyword , int size , int page , String sortBy , String order, User user) ;

    List<contact> getByUserId(String userId);

    Page<contact> getByUser(User user , int page , int size , String sortBy , String Direction);
}
