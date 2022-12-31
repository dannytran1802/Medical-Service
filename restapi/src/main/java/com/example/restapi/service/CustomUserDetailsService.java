package com.example.restapi.service;

import com.example.restapi.model.entity.Account;
import com.example.restapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = null;

        if (username.contains("@"))
            user = accountRepository.findByEmail(username);
        else
            user = accountRepository.findByUsername(username);

        if (!user.isPresent())
            throw new BadCredentialsException("Bad credentials");

        new AccountStatusUserDetailsChecker().check(user.get());

        return user.get();
    }

    private List<SimpleGrantedAuthority> getAuthorities(Account account) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        try {
            String roleName = account.getRole().getName();
            switch (roleName.toUpperCase()) {
                case "USER":
                    authorities.add(new SimpleGrantedAuthority("USER"));
                    break;
                case "OWNER":
                    authorities.add(new SimpleGrantedAuthority("OWNER"));
                    break;
                case "ADMIN":
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
        }
        return authorities;
    }

}
