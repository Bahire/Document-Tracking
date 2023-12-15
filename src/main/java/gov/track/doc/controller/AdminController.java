package gov.track.doc.controller;

import gov.track.doc.controller.security.UserCustomDetails;
import gov.track.doc.model.Application;
import gov.track.doc.model.EApplicationStatus;
import gov.track.doc.model.Users;
import gov.track.doc.service.implementation.ApplicationServiceImpl;
import gov.track.doc.service.implementation.DepartmentServiceImpl;
import gov.track.doc.service.implementation.UsersServiceImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ApplicationServiceImpl applicationService;
    private final UsersServiceImpl usersService;
    private final DepartmentServiceImpl departmentService;

    public AdminController(ApplicationServiceImpl applicationService, UsersServiceImpl usersService, DepartmentServiceImpl departmentService) {
        this.applicationService = applicationService;
        this.usersService = usersService;
        this.departmentService = departmentService;
    }

    @GetMapping({"","/","/home"})
    public String homePage(Model model){
        try{
            Users theUser = new Users();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
                theUser = usersService.searchUser(userDetails.getUser());
            }
            List<Application> applications = applicationService.allApplications();
            model.addAttribute("applications",applications);
            model.addAttribute("totalNumber",applications.size());
            model.addAttribute("departments", departmentService.departments());
            model.addAttribute("clients", usersService.allUsers());
            model.addAttribute("username",theUser.getFirstName()+theUser.getLastName());
            return "admin";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/changeStatusReceived")
    public String changeDocumentStatus(@RequestParam("id") Integer id){
        try{
            Application theApplication = applicationService.searchDocumentByDocumentId(new Application(id));
            if(theApplication!=null){
                theApplication.setStatus(EApplicationStatus.RECEIVED);
                theApplication = applicationService.changeApplicationStatus(theApplication);
                if(theApplication!=null){
                    return "redirect:/admin/home";
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/changeStatusApproved")
    public String changeDocumentStatusApproved(@RequestParam("id") Integer id){
        try{
            Application theApplication = applicationService.searchDocumentByDocumentId(new Application(id));
            if(theApplication!=null){
                theApplication.setStatus(EApplicationStatus.APPROVED);
                theApplication = applicationService.changeApplicationStatus(theApplication);
                if(theApplication!=null){
                    return "redirect:/admin/home";
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
;