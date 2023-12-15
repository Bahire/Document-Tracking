package gov.track.doc.service.implementation;

import gov.track.doc.model.Application;
import gov.track.doc.model.Users;
import gov.track.doc.repository.ApplicationRepo;
import gov.track.doc.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepo repo;

    public ApplicationServiceImpl(ApplicationRepo repo) {
        this.repo = repo;
    }

    @Override
    public Application recordData(Application theApplication) {
        return repo.save(theApplication);
    }

    @Override
    public Application updateData(Application theApplication) {
        Application application = searchDocumentByDocumentId(theApplication);
        if(!Objects.isNull(application)){
//            application.setApplicationLetter(theApplication.getApplicationLetter());
//            application.setBusinessPlan(theApplication.getBusinessPlan());
//            application.setShareHolderPlan(theApplication.getShareHolderPlan());
            return repo.save(application);
        }
        return null;
    }

    @Override
    public Application deleteData(Application theApplication) {
        Application application = searchDocumentByDocumentId(theApplication);
        if(!Objects.isNull(application)){
            application.setActive(false);
            repo.delete(application);
            return application;
        }
        return null;
    }

    @Override
    public Application searchDocumentByStatus(Application theApplication) {
        return repo.findByStatus(theApplication.getStatus());
    }

    @Override
    public Application searchDocumentByUsers(Application theApplication) {
//        return repo.findByUsers(theApplication.getUsers());
        return null;
    }

    @Override
    public Application searchDocumentByDocumentId(Application theApplication) {
        return repo.findById(theApplication.getId()).get();
    }

    @Override
    public List<Application> allApplications() {
        return repo.findAll();
    }

    @Override
    public List<Application> allLoggedApplications(Users theUser) {
        // find user
        return repo.findByUsers(theUser);
    }

    @Override
    public Application changeApplicationStatus(Application theApplication) {
        Application application = searchDocumentByDocumentId(theApplication);
        if(application!=null){
            application.setStatus(theApplication.getStatus());
            return repo.save(application);
        }
        return null;
    }

    @Override
    public Application searchDocumentByTrackingNumber(String track) {
        return repo.findByTrackNumber(track);
    }
}
