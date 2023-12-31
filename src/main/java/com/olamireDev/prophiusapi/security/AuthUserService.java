package com.olamireDev.prophiusapi.security;
import com.olamireDev.prophiusapi.entity.User;
import com.olamireDev.prophiusapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
                email + " was not found"));
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),roles);
    }
}


