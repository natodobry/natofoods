package com.natodobry.natofoods.domain.model.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CidadeNaoEncontradaException(String msg){
        super(msg);
    }

    public CidadeNaoEncontradaException(Long estadoId){
        this(String.format("Não existe um cadastro de cidade com código %d", estadoId));
    }
}
