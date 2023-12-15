package gov.track.doc.service;

import gov.track.doc.model.Users;

import java.util.List;

public interface UsersService {
    Users registerUser(Users theUser);
    Users updateUser(Users theUser);
    Users deleteUser(Users theUser);
    Users searchUser(Users theUser);
    List<Users> allUsers();
}
