package com.natodobry.natofoods.domain.model.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String msg){
        super(msg);
    }

    public RestauranteNaoEncontradoException(Long estadoId){
        this(String.format("Não existe um cadastro de restaurante com código %d", estadoId));
    }
}
