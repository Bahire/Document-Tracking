package gov.track.doc.service;

import gov.track.doc.model.Department;

import java.util.List;

public interface DepartmentService {
    Department registerDepartment(Department dep);
    Department updateDepartment(Department dep);
    Department deleteDepartment(Department dep);
    Department searchDepartment(Department dep);
    List<Department> departments();

}
