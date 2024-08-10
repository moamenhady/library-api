package dev.moamenhady.libraryapi.security;

import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatronDetailsService implements UserDetailsService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronDetailsService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Patron> patron = patronRepository.findPatronByEmail(email);
        if (patron.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        return new PatronDetails(patron.get());
    }
}
