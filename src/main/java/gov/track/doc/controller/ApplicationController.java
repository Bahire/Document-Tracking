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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationServiceImpl applicationService;
    private final UsersServiceImpl usersService;
    private final DepartmentServiceImpl departmentService;
    public ApplicationController(ApplicationServiceImpl applicationService, UsersServiceImpl usersService, DepartmentServiceImpl departmentService){
        this.applicationService = applicationService;
        this.usersService = usersService;
        this.departmentService  = departmentService;
    }
    @GetMapping({"/new","","/"})
    public String applicationForm(Model model){
        try{
            Users theUser = new Users();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
                theUser = usersService.searchUser(userDetails.getUser());
            }
            if(theUser !=null){
                model.addAttribute("applications",applicationService.allLoggedApplications(theUser));
                model.addAttribute("departments",departmentService.departments());
                model.addAttribute("app", new Application());
                model.addAttribute("username",theUser.getFirstName()+theUser.getLastName());
                return "application";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/new")
    public String persistDoc(@ModelAttribute("application")Application theApplication ,
                             @RequestParam("letter") MultipartFile applicationLetter ,
                             @RequestParam("business") MultipartFile businessPlan,
                             @RequestParam("shareholder") MultipartFile shareHolder){
        try{
            Users theUser = new Users();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
                theUser = usersService.searchUser(userDetails.getUser());
            }
            if(theUser !=null){
                theApplication.setApplicationLetterFileName(applicationLetter.getOriginalFilename());
                theApplication.setBusinessPlanFileName(businessPlan.getOriginalFilename());
                theApplication.setShareHolderPlanFileName(shareHolder.getOriginalFilename());
//                theApplication.setApplicationLetter(applicationLetter.getBytes());
//                theApplication.setBusinessPlan(businessPlan.getBytes());
//                theApplication.setShareHolderPlan(shareHolder.getBytes());
                theApplication.setStatus(EApplicationStatus.WAITING);
                theApplication.setTrackingNumber(UUID.randomUUID().toString());
                theApplication.setUsers(theUser);
                Application application = applicationService.recordData(theApplication);
                if(application!=null){
                    /**
                     * we call and save this to a new directory of folder to be referenced later;
                     * but we need to generate code for document tracking
                     */
                    String uploadDir = "src/main/resources/static/client_pdf_file/"+theUser.getFirstName()+"_"+theUser.getLastName()+"_"+theUser.getId();
                    Path uploadPath = Paths.get(uploadDir);
                    if(!Files.exists(uploadPath)){
                        Files.createDirectories(uploadPath);
                    }
                    saveClientApplicationData(uploadDir,applicationLetter);
                    saveClientApplicationData(uploadDir,businessPlan);
                    saveClientApplicationData(uploadDir,shareHolder);
                    return "redirect:/application/new";
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private void saveClientApplicationData(String uploadDir , MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(uploadDir, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }
}
