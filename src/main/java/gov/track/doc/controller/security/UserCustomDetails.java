package gov.track.doc.controller.security;


import gov.track.doc.model.Role;
import gov.track.doc.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserCustomDetails implements UserDetails {
    private Users theUser;

    public UserCustomDetails(Users theUser) {
        this.theUser = theUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = theUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return theUser.getPassword();
    }

    @Override
    public String getUsername() {
        return theUser.getEmail();
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
        return theUser.isActive();
    }

    public String getFullNames(){
        return theUser.getFirstName() + theUser.getLastName();
    }
    public Integer getCustomer_id(){
        return theUser.getId();
    }

    public Users getUser() {
        return theUser;
    }
    public boolean hasRole(String roleName) {
        return this.theUser.hasRole(roleName);
    }
}
