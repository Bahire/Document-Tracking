package gov.track.doc.service.implementation;

import gov.track.doc.controller.security.UserCustomDetails;
import gov.track.doc.model.Users;
import gov.track.doc.repository.UsersRepo;
import gov.track.doc.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImp  {
    @Autowired
    UsersRepo userRepository;

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userRepository.findByUsername(username);
//        return null;
//    }
}
