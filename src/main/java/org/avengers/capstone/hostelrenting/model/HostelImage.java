package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hostelimage")
@Data
public class HostelImage implements Serializable {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int imageId;

    @Column(name = "resource_url")
    private String resourceUrl;

//    @ManyToOne
//    @JoinColumn(name = "hostel_type_id")
//    private HostelType hostelType;
}
