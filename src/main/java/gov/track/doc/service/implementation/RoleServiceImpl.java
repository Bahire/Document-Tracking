package gov.track.doc.service.implementation;

import gov.track.doc.model.Role;
import gov.track.doc.repository.RoleRepo;
import gov.track.doc.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo repo;
    public RoleServiceImpl(RoleRepo repo){
        this.repo=repo;
    }
    @Override
    public Role searchRoleByName(String role) {
        return repo.findRoleByName(role);
    }
}
