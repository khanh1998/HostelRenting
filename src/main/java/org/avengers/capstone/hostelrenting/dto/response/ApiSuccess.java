package org.avengers.capstone.hostelrenting.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApiSuccess<T>  implements Serializable {
    private boolean isSuccess;
    private String message;
    private T data;

    public ApiSuccess(T data){
        this.data = data;
        this.isSuccess = true;
    }

    public ApiSuccess(T data, String message){
        this.data = data;
        this.message = message;
        this.isSuccess = true;
    }
}
