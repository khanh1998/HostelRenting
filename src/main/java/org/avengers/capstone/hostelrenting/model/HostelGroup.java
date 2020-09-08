package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.*;
import org.avengers.capstone.hostelrenting.util.AddressSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostel_group")
public class HostelGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "building_no", nullable = false)
    private String buildingNo;

    @NotBlank(message = "Longitude is mandatory")
    private Double longitude;

    @NotBlank(message = "Latitude is mandatory")
    private Double latitude;

    private boolean ownerJoin;

    private String curfewTime;

    private String imgUrl;

    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelType> hostelTypes;

    @ManyToOne
    @JoinColumn(name = "street_ward_id", nullable = false)
    private StreetWard address;

    @ManyToOne
    @JoinColumn(name="vendor_id", nullable = false)
    private Vendor vendor;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @JoinTable(name = "group_service",
//            joinColumns = @JoinColumn(name = "group_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id")
//    )
//    private Set<Service> services;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "group_schedule",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedules;

    public Address getAddress() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(StreetWard.class, new AddressSerializer());
        mapper.registerModule(module);
        try {
            String serialized = mapper.writeValueAsString(address);
            Address serializedAddress = mapper.readValue(serialized, Address.class);
            return serializedAddress;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
