package org.avengers.capstone.hostelrenting.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.dto.category.CategoryDTO;
import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.serialized.AddressFull;
import org.avengers.capstone.hostelrenting.model.serialized.ServiceFull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupDTOResponse implements Serializable {
    private int groupId;

    @JsonProperty("address")
    private AddressFull addressFull;

    private Long vendorId;

    private String groupName;

    private String buildingNo;

    private CategoryDTO category;

    private Double longitude;

    private Double latitude;

    private String managerName;

    private String managerPhone;

    /**
     * curfewTime that limit the time to come back home
     */
    private String curfewTime;

    private boolean ownerJoin;

    private String imgUrl;

    private float downPayment;

    @JsonProperty("services")
    private Collection<GroupServiceDTOResponse> groupServices;

    @JsonProperty("regulations")
    private Collection<GroupRegulationDTOResponse> groupRegulations;

    @JsonProperty("schedules")
    private Collection<GroupScheduleDTOResponse> groupSchedules;

    public Collection<GroupRegulationDTOResponse> getGroupRegulations() {
        if (groupRegulations != null)
            return groupRegulations.stream().filter(GroupRegulationDTOResponse::isActive).collect(Collectors.toList());
        return null;
    }

    public Collection<GroupScheduleDTOResponse> getGroupSchedules() {
        if (groupSchedules != null)
            return groupSchedules.stream()
                    .collect(Collectors.groupingBy(GroupScheduleDTOResponse::getScheduleId))
                    .values().stream()
                    .map(scheduleDTO -> GroupScheduleDTOResponse
                            .builder().scheduleId(scheduleDTO.get(0).getScheduleId())
                            .schedule(scheduleDTO.get(0).getSchedule())
                            .startTime(scheduleDTO.get(0).getStartTime())
                            .endTime(scheduleDTO.get(0).getEndTime())
                            .timeRange(scheduleDTO
                                    .stream()
                                    .map(dto -> {
                                        ArrayList<String> timeRange = new ArrayList<>();
                                        timeRange.add(dto.getStartTime() + " - " + dto.getEndTime());
                                        return timeRange;
                                    })
                                    .flatMap(Collection::stream)
                                    .collect(Collectors.toList())).build()).collect(Collectors.toList());
        return null;
    }
}