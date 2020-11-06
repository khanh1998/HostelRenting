package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.*;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;
import org.avengers.capstone.hostelrenting.model.serialized.ServiceFull;
import org.avengers.capstone.hostelrenting.util.AddressSerializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "group_hostel")
public class Group {
    /**
     * group id
     */
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * group name
     */
    @Column(name = "group_name", nullable = false)
    private String groupName;

    /**
     * street ward object
     */
    @ManyToOne
    @JoinColumn(name = "street_ward_id", nullable = false)
    private StreetWard address;

    /**
     * building number
     */
    @Column(name = "building_no", nullable = false)
    private String buildingNo;

    /**
     * longitude of the group
     */
    @Column(nullable = false)
    private Double longitude;

    /**
     * latitude of the group
     */
    @Column(nullable = false)
    private Double latitude;

    /**
     * manager name of this group
     */
    private String managerName;

    /**
     * manager phone number of this group
     */
    private String managerPhone;

    /**
     * living in the same building with the owner or not
     */
    private boolean ownerJoin;

    /**
     * curfew time range. Ex: 23:00 - 05:00
     */
    private String curfewTime;

    /**
     * image url of hostel group
     */
    private String imgUrl;

    /**
     * Down payment for keeping a particular room in 7 days
     */
    @Column(nullable = false, columnDefinition = "float(4) DEFAULT 0")
    private float downPayment;

    /**
     * vendor object
     */
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * List of hostel types
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Type> types;

    /**
     * list of service details of group
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GroupService> groupServices;

    /**
     * list of group_schedules
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GroupSchedule> groupSchedules;

    /**
     * list of group_schedules
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GroupRegulation> groupRegulations;

    /**
     * creating timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    /**
     * updating timestamp
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * Serialize StreetWard to Address model
     *
     * @return Address obj
     */
    public AddressFull getAddress() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(StreetWard.class, new AddressSerializer());
        mapper.registerModule(module);
        try {
            String serialized = mapper.writeValueAsString(address);
            return mapper.readValue(serialized, AddressFull.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
