package org.avengers.capstone.hostelrenting.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> implements Serializable {
    private boolean isSuccess;
    private String message;
    private T data;
    @JsonIgnore
    private Object errors;

    public static <T> Response<T> ok(T data, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
