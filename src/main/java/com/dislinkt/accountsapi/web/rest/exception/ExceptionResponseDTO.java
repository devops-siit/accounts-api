package com.dislinkt.accountsapi.web.rest.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExceptionResponseDTO {

    private int status;
    private String statusText;
    private String localizedErrorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date timestamp;

    public ExceptionResponseDTO(String localizedErrorMessage, HttpStatus status) {
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.localizedErrorMessage = localizedErrorMessage;
        timestamp = new Date();
    }
}