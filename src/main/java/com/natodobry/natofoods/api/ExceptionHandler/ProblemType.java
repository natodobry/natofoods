package com.natodobry.natofoods.api.ExceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-nao-compreensivel", "Mensagem incompreensivel"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-inválidos", "Dados inválidos");


    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://natofood.com.br" + path;
        this.title = title;
    }
}
