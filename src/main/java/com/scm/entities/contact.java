package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String PhoneNumber;
    private String address;
    private String picture;
    @Column(columnDefinition = "TEXT")
    private String Description;
    private String websitelink;
    private String LinkednLink;
    private boolean favorite = false;

    private String cloudianryImagePublicId;

    @ManyToOne
    @JsonIgnore
    private User user;

    
    @OneToMany(mappedBy = "Contact" , cascade = CascadeType.ALL , fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();




}
