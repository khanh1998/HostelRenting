package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "district")
@Data
public class District implements Serializable {
    @Id
    @Column(name = "district_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

//    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
//    private List<HostelGroup> hostelGroups;
}
