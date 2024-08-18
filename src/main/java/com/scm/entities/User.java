package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class User implements UserDetails {

    @Id
    private String userId;
    @Column(name="user_Name" , nullable = false)
    private String Name;
    @Column(unique = true , nullable = false)
    private String email;
    @Column(columnDefinition ="TEXT")
    private String about;
    @Getter(value = AccessLevel.NONE )
    private String password;
    @Column(columnDefinition = "TEXT")
    private String profilepic;
    private String phoneNumber;

    @Getter(value = AccessLevel.NONE)

    // information
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneNummberverified = false;

    @Enumerated(value = EnumType.STRING)
    // SELF,GOOGLE,FACEBOOK,GITHUB,LINKEDN
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List<contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolelist = new ArrayList<>();


    private String emailToken;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = rolelist.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
       return this.password;
    }

    


   
    
    





}
