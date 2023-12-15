package gov.track.doc.repository;

import gov.track.doc.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {
    @Query("select u from Users u where u.email=:email")
    Users findByEmail(String email);

//    Users findByUsername(String username);
}
