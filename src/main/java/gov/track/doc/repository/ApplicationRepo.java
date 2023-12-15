package gov.track.doc.repository;

import gov.track.doc.model.Application;
import gov.track.doc.model.EApplicationStatus;
import gov.track.doc.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Integer> {
    @Query("select a from Application a where a.status =:status")
    Application findByStatus(@Param("status")EApplicationStatus status);
//    Application findByUsers(Users users);
    List<Application> findByUsers(Users users);

    @Query("select doc from Application doc where doc.trackingNumber =:track")
    Application findByTrackNumber(@Param("track") String track);
}
