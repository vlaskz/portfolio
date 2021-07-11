package io.vlaskz.portfolio.service;

import io.vlaskz.portfolio.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {
    private final SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return Optional.ofNullable(sysUserRepository.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("SysUser not found"));
    }
}
