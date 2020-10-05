package org.avengers.capstone.hostelrenting.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.*;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;
import org.avengers.capstone.hostelrenting.model.serialized.ServiceFull;
import org.avengers.capstone.hostelrenting.util.AddressSerializer;
import org.avengers.capstone.hostelrenting.util.ServiceSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "hostel_group")
public class HostelGroup {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "building_no", nullable = false)
    private String buildingNo;

//    @NotBlank(message = "Longitude is mandatory")
    private Double longitude;

//    @NotBlank(message = "Latitude is mandatory")
    private Double latitude;

    private boolean ownerJoin;

    private String curfewTime;

    private String imgUrl;

    @OneToMany(mappedBy = "hostelGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HostelType> hostelTypes;

    @OneToMany(mappedBy = "hGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<ServiceDetail> serviceDetails;

    @ManyToOne
    @JoinColumn(name = "street_ward_id", nullable = false)
    private StreetWard address;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "hGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<HGSchedule> hgSchedules;

    private String managerName;

    private String managerPhone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

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
            AddressFull serializedAddressFull = mapper.readValue(serialized, AddressFull.class);
            return serializedAddressFull;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<ServiceFull> getServiceDetails() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(ServiceDetail.class, new ServiceSerializer());
        mapper.registerModule(module);

        return serviceDetails
                .stream()
                .filter(serviceDetail -> serviceDetail.isActive())
                .map(service -> {
                    String serialized = null;
                    try {
                        serialized = mapper.writeValueAsString(service);
                        ServiceFull serializedServiceFull = mapper.readValue(serialized, ServiceFull.class);
                        return serializedServiceFull;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

    }
}
