package com.natodobry.natofoods.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public EstadoNaoEncontradoException(String msg){
        super(msg);
    }

    public EstadoNaoEncontradoException(Long estadoId){
        this(String.format("Não existe um cadastro de estado com código %d", estadoId));
    }
}
