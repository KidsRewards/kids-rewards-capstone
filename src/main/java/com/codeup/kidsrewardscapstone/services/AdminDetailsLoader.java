package com.codeup.kidsrewardscapstone.services;

import com.codeup.kidsrewardscapstone.models.Admin;
import com.codeup.kidsrewardscapstone.models.AdminWithRoles;
import com.codeup.kidsrewardscapstone.repositories.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Defines a service that Spring Security will use to load the authentication and authorization information of users
@Service
public class AdminDetailsLoader implements UserDetailsService {
    private final AdminRepository admins;

    public AdminDetailsLoader(AdminRepository admins){
        this.admins = admins;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = admins.findByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("No user found for " + username);
        }
        return new AdminWithRoles(admin);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
