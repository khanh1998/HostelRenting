package capstone.tdhostel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "deal")
@Data
public class Deal implements Serializable {
    @Id
    @Column(name = "deal_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dealId;

    @Column(name = "offered_price")
    private int offeredPrice;

//    @ManyToOne
//    @JoinColumn(name = "renter_id")
//    private Renter renter;
//
//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendor;

//    @ManyToOne
//    @JoinColumn(name = "hostel_type_id")
//    private HostelType hostelType;
}
