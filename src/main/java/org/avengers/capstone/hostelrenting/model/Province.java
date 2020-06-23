package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "province")
@Data
public class Province implements Serializable {
    @Id
    @Column(name = "province_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int provinceId;

    @Column(name = "province_name", nullable = false)
    private String provinceName;

//    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
//    private List<HostelGroup> hostelGroups;
}
