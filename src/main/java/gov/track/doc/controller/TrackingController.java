package gov.track.doc.controller;

import gov.track.doc.model.Application;
import gov.track.doc.service.implementation.ApplicationServiceImpl;
import gov.track.doc.service.implementation.UsersServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/track")
public class TrackingController {
    private final ApplicationServiceImpl applicationService;
    private final UsersServiceImpl usersService;

    public TrackingController(ApplicationServiceImpl applicationService, UsersServiceImpl usersService) {
        this.applicationService = applicationService;
        this.usersService = usersService;
    }

    @GetMapping
    public String homePage(){
        try{
            return "track";
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    @GetMapping("/search")
    public void returnSearch(@RequestParam("uuidTrack") String track, HttpServletResponse response) {
        try {
            Application theApplication = applicationService.searchDocumentByTrackingNumber(track);
            if (theApplication != null) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");

                PrintWriter writer = response.getWriter();
                writer.write("Tracking Number: "+theApplication.getTrackingNumber() + "|\n Status: " + theApplication.getStatus());
                writer.flush();
                writer.close();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
//    @PostMapping("/track/search")
//    public String returnSearch(@RequestParam("uuidTrack") String track, HttpServletResponse response,Model model) {
//        try {
//            Application theApplication = applicationService.searchDocumentByTrackingNumber(track);
//            if (theApplication != null) {
//                model.addAttribute("data",theApplication);
//                return "track";
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "redirect:/track";
//    }
}
