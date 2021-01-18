package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.dto.groupRegulation.GroupRegulationDTOResponse;
import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOResponse;
import org.avengers.capstone.hostelrenting.dto.schedule.GroupScheduleDTOResponseV2;
import org.avengers.capstone.hostelrenting.dto.schedule.ScheduleDTOV2;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTOResponse;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupDTOResponseV3 implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private AddressFull addressFull;

    private UUID vendorId;

    private String groupName;

    private String buildingNo;

    private CategoryDTO category;

    private Double longitude;

    private Double latitude;

    private UUID managerId;

    private String managerName;

    private String managerPhone;

    /**
     * curfewTime that limit the time to come back home
     */
    private String curfewTime;

    /**
     * appendix contract of each group
     */
    private String appendixContract;

    private boolean ownerJoin;

    private String imgUrl;

    private float downPayment;

    @JsonProperty("services")
    private Collection<GroupServiceDTOResponse> groupServices;

    @JsonProperty("regulations")
    private Collection<GroupRegulationDTOResponse> groupRegulations;

    @JsonProperty("schedules")
    private Collection<GroupScheduleDTOResponseV2> groupSchedules;

    @JsonProperty("vendor")
    private VendorDTOResponse vendor;

    public Collection<GroupRegulationDTOResponse> getGroupRegulations() {
        if (groupRegulations != null)
            return groupRegulations;
        return null;
    }

    public Collection<GroupScheduleDTOResponseV2> getGroupSchedules() {
        if (groupSchedules != null)
            return groupSchedules.stream()
                    .collect(Collectors.groupingBy(GroupScheduleDTOResponseV2::getScheduleId))
                    .values().stream()
                    .map(scheduleDTO -> GroupScheduleDTOResponseV2
                            .builder().scheduleId(scheduleDTO.get(0).getScheduleId())
                            .id(scheduleDTO.get(0).getId())
                            .schedule(scheduleDTO.get(0).getSchedule())
                            .startTime(scheduleDTO.get(0).getStartTime())
                            .endTime(scheduleDTO.get(0).getEndTime())
                            .timeRanges(scheduleDTO
                                    .stream()
                                    .map(dto -> {
                                        ArrayList<ScheduleDTOV2> timeRange = new ArrayList<>();
                                        ScheduleDTOV2 scheduleDTOV2 = new ScheduleDTOV2();
                                        scheduleDTOV2.setGroupScheduleId(dto.getId());
                                        scheduleDTOV2.setTimeRange(dto.getStartTime() + " - " + dto.getEndTime());
                                        timeRange.add(scheduleDTOV2);
                                        return timeRange;
                                    })
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.toList())).build())
                    .collect(Collectors.toList());
        return null;
    }

    public Collection<GroupServiceDTOResponse> getGroupServices() {
        return groupServices.stream().filter(groupServiceDTOResponse -> groupServiceDTOResponse.isActive())
                .collect(Collectors.toList());
    }
}
