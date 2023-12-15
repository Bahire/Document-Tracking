package gov.track.doc.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "dep_id")
    private Department department;
    @Column(name = "application_letter_file_name")
    private String applicationLetterFileName;
    @Column(name = "business_plan_file_name")
    private String businessPlanFileName;
    @Column(name = "share_holder_plan_file_name")
    private String shareHolderPlanFileName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private boolean active = true;
    private EApplicationStatus status;
    @Column(name = "application_date")
    private Date applicationDate = new Date();
    @Column(name = "tracking_number")
    private String trackingNumber;
//    @Lob
//    @Column(name = "application_letter")
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] applicationLetter;
//    @Lob
//    @Column(name = "business_plan")
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] businessPlan;
//    @Lob
//    @Column(name = "share_holder_plan")
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] shareHolderPlan;

    public Application(Integer id) {
        this.id = id;
    }
}
