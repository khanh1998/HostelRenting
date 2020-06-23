package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hostelgroup")
@Data
public class HostelGroup implements Serializable {
    @Id
    @Column(name = "hostel_group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hostelGroupId;

    @Column(name = "hostel_group_name", nullable = false)
    private String hostelGroupName;

    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;

    private String longitude;

    private String latitude;

    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL)
    private List<HostelType> hostelTypes;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

//    @ManyToOne
//    @JoinColumn(name = "province_id")
//    private Province province;
//
//    @ManyToOne
//    @JoinColumn(name = "district_id")
//    private District district;
}
