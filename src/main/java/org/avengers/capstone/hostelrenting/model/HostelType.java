package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hosteltype")
@Data
public class HostelType implements Serializable {
    @Id
    @Column(name = "hostel_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hostelTypeId;

    @Column(name = "hostel_type_name", nullable = false)
    private String hostelTypeName;

    private String price;

    private String unit;

    private float superficiality;

    private int capacity;

    @OneToMany(mappedBy = "hostelType", cascade = CascadeType.ALL)
    private List<Booking> bookings;

//    @OneToMany(mappedBy = "hosteltype", cascade = CascadeType.ALL)
//    private List<Deal> deals;

    @ManyToOne
    @JoinColumn(name = "hostel_group_id")
    private HostelGroup hostelGroup;

//    @OneToMany(mappedBy = "hosteltype", cascade = CascadeType.ALL)
//    private List<HostelImage> hostelImages;
}
