package dev.moamenhady.libraryapi.security;


import dev.moamenhady.libraryapi.entity.Admin;
import dev.moamenhady.libraryapi.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findAdminByUsername(username);
        if (admin.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new AdminDetails(admin.get());
    }
}
