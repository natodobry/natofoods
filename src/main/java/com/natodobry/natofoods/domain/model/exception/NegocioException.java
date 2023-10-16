package com.natodobry.natofoods.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NegocioException extends RuntimeException{

    public NegocioException(String msg){
        super(msg);
    }

    public NegocioException(String msg, Throwable causa){
        super(msg, causa);
    }
}
