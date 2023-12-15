package gov.track.doc.controller.security;


import gov.track.doc.model.Users;
import gov.track.doc.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserCustomDetailsService implements UserDetailsService {
    @Autowired private UsersRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user  = new Users();
        if(email != null){
            System.out.println("AM IN !!!!!!!!!>>>> Security");
            user = repo.findByEmail(email);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserCustomDetails(user);
    }
}
