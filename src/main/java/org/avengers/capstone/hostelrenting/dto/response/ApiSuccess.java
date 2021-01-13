package org.avengers.capstone.hostelrenting.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.avengers.capstone.hostelrenting.handler.LowerCaseClassNameResolver;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccess<T> implements Serializable {
    @Getter
    private boolean isSuccess;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Getter
    private LocalDateTime timestamp;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Integer page;
    @Getter
    @Setter
    private Integer size;
    @Getter
    @Setter
    private Integer total;
    @Getter
    @Setter
    private T data;

    private ApiSuccess(){
        isSuccess = true;
        timestamp = LocalDateTime.now();
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

    public ApiSuccess(T data, String message, int size, int page){
        this();
        this.data = data;
        this.message = message;
        this.size = size;
        this.page = page;
    }

    public ApiSuccess(String message, boolean isSuccess) {
        this();
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
