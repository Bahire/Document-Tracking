package gov.track.doc.service.implementation;

import gov.track.doc.model.Department;
import gov.track.doc.repository.DepartmentRepo;
import gov.track.doc.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo repo;
    public DepartmentServiceImpl(DepartmentRepo repo){
        this.repo=repo;
    }
    @Override
    public Department registerDepartment(Department dep) {
        return repo.save(dep);
    }

    @Override
    public Department updateDepartment(Department dep) {
        return null;
    }

    @Override
    public Department deleteDepartment(Department dep) {
        return null;
    }

    @Override
    public Department searchDepartment(Department dep) {
        return repo.findById(dep.getDepId()).get();
    }

    @Override
    public List<Department> departments() {
        return repo.findAll();
    }
}
