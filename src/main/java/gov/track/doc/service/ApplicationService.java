package gov.track.doc.service;

import gov.track.doc.model.Application;
import gov.track.doc.model.Users;

import java.util.List;

public interface ApplicationService {
    Application recordData(Application theApplication);
    Application updateData(Application theApplication);
    Application deleteData(Application theApplication);
    Application searchDocumentByStatus(Application theApplication);
    Application searchDocumentByUsers(Application theApplication);
    Application searchDocumentByDocumentId(Application theApplication);
    List<Application> allApplications();
    List<Application> allLoggedApplications(Users theUser);

    Application changeApplicationStatus(Application theApplication);

    Application searchDocumentByTrackingNumber(String track);
}
