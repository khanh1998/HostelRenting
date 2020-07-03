package org.avengers.capstone.hostelrenting.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD
=======
import org.avengers.capstone.hostelrenting.handler.LowerCaseClassNameResolver;
import org.springframework.http.HttpStatus;
>>>>>>> ddb5bbacfd5f10612ee0a65ca7efb42599328d52
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiSuccess<T> implements Serializable {
    private boolean isSuccess;
    private String message;
    private T data;

    private ApiSuccess(){
        isSuccess = true;
    }

    public ApiSuccess(T data){
        this();
        this.data = data;
    }

    public ApiSuccess(T data, String message){
        this();
        this.data = data;
        this.message = message;
    }

    public ApiSuccess(String message) {
        this();
        this.message = message;
    }
}
