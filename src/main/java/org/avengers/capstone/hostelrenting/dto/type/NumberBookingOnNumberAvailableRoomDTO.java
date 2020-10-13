package org.avengers.capstone.hostelrenting.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumberBookingOnNumberAvailableRoomDTO {
    private Integer numberBookingWithIncomingStatus;
    private Integer numberAvailableRoom;
}
