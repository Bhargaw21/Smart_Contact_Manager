package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;
import com.scm.entities.contact;

@Repository
public interface ContactRepo extends JpaRepository<contact,String> {

    //find the contact by user
    //custom finder method

    Page<contact> findByUser(User user , Pageable pageable);

    // custom query method
    @Query("select c from contact c where c.user.id = :userId ORDER BY c.name ASC")
    List<contact> findByUserId(@Param("userId") String userId);

    Page<contact> findByUserAndNameContaining(  User user,String nameKeyword ,Pageable pageable);
    Page<contact> findByUserAndEmailContaining( User user,String emailKeyword ,Pageable pageable);
    Page<contact> findByUserAndPhoneNumberContaining(  User user,String PhoneNumberKeyword ,Pageable pageable);
} 
