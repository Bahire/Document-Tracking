package gov.track.doc.controller;

import gov.track.doc.model.Role;
import gov.track.doc.model.Users;
import gov.track.doc.service.implementation.RoleServiceImpl;
import gov.track.doc.service.implementation.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UsersServiceImpl usersService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;
    /**
     * Using Constructor Injector instead of Field Injector by help of AUtowired
     * @param usersService
     */
    public UserController(UsersServiceImpl usersService, RoleServiceImpl roleService, PasswordEncoder encoder){
        this.usersService=usersService;
        this.roleService = roleService;
        this.encoder = encoder;
    }
    @GetMapping("/login")
    public String loginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }else {
            return "application";
        }
    }
    @GetMapping({"/","/home"})
    public String allUsers(Model model){
        try{
            model.addAttribute("users",usersService.allUsers());
            return "";
        }catch (Exception ex){
            ex.printStackTrace();
        }
            return null;
    }
    @GetMapping("/new")
    public String registrationForm(Model model){
        try{
            model.addAttribute("user", new Users());
            return "createaccount";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    @PostMapping("/new")
    public String persistData(@ModelAttribute("user") Users theUser){
        try{
            Role theRole = roleService.searchRoleByName("CLIENT");
            Set<Role> roles = new HashSet<>();
            if(theRole!=null)
                roles.add(theRole);
            theUser.setRoles(roles);
            theUser.setPassword(encoder.encode(theUser.getPassword()));
            Users userObj = usersService.registerUser(theUser);
            if(userObj!=null){
                return "redirect:/user/login";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "error";
    }

    @GetMapping("/error")
    public String errorPage(){
        try{
            return "error";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "error";
    }
}
