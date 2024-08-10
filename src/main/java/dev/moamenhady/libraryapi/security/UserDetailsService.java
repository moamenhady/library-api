package dev.moamenhady.libraryapi.security;

import dev.moamenhady.libraryapi.entity.Admin;
import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.repository.AdminRepository;
import dev.moamenhady.libraryapi.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final PatronRepository patronRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public UserDetailsService(PatronRepository patronRepository, AdminRepository adminRepository) {
        this.patronRepository = patronRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Patron> patron = patronRepository.findPatronByEmail(username);
        if (patron.isPresent()) {
            return new PatronDetails(patron.get());
        }

        Optional<Admin> admin = adminRepository.findAdminByUsername(username);
        if (admin.isPresent()) {
            return new AdminDetails(admin.get());
        }
        throw new UsernameNotFoundException("User not found");
    }
}
