package org.avengers.capstone.hostelrenting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    private String sender;
    private String content;
    private String timestamp;
}
