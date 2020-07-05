package org.avengers.capstone.hostelrenting.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.handler.LowerCaseClassNameResolver;

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
