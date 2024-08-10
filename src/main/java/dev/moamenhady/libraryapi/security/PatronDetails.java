package dev.moamenhady.libraryapi.security;

import dev.moamenhady.libraryapi.entity.Patron;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PatronDetails implements UserDetails {

    private final Patron patron;

    public PatronDetails(Patron patron) {
        this.patron = patron;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(patron.getRole()));
    }

    @Override
    public String getPassword() {
        return patron.getPassword();
    }

    @Override
    public String getUsername() {
        return patron.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
