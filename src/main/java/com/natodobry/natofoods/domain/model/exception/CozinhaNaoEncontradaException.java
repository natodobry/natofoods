package com.natodobry.natofoods.domain.model.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CozinhaNaoEncontradaException(String msg){
        super(msg);
    }

    public CozinhaNaoEncontradaException(Long estadoId){
        this(String.format("Não existe um cadastro de cozinha com código %d", estadoId));
    }
}
