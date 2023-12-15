package gov.track.doc.service.implementation;

import gov.track.doc.model.Users;
import gov.track.doc.repository.UsersRepo;
import gov.track.doc.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepo repo;
    public UsersServiceImpl(UsersRepo repo){
        this.repo=repo;
    }
    @Override
    public Users registerUser(Users theUser) {
        return repo.save(theUser);
    }

    @Override
    public Users updateUser(Users theUser) {
        return repo.save(theUser);
    }

    @Override
    public Users deleteUser(Users theUser) {
        repo.delete(theUser);
        return null;
    }

    @Override
    public Users searchUser(Users theUser) {
        return repo.findById(theUser.getId()).get();
    }

    @Override
    public List<Users> allUsers() {
        return repo.findAll();
    }
}
