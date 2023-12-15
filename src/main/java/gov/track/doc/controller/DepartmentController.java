package gov.track.doc.controller;

import gov.track.doc.model.Department;
import gov.track.doc.service.implementation.DepartmentServiceImpl;
import gov.track.doc.service.implementation.UsersServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dep")
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;
    private final UsersServiceImpl usersService;

    public DepartmentController(DepartmentServiceImpl departmentService, UsersServiceImpl usersService) {
        this.departmentService = departmentService;
        this.usersService = usersService;
    }

    @GetMapping("/new")
    public String newDepartmentPage(Model model){
        try{
            model.addAttribute("department", new Department());
            return "newDepartment";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/new")
    public String persistDepartment(@ModelAttribute("department") Department theDepartment){
        try{
            Department dep = departmentService.registerDepartment(theDepartment);
            if(dep!=null){
                return "redirect:/admin/home";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;

    }
}
