package com.natodobry.natofoods.api.controller;

import com.natodobry.natofoods.domain.model.Cidade;
import com.natodobry.natofoods.domain.model.exception.EstadoNaoEncontradoException;
import com.natodobry.natofoods.domain.model.exception.NegocioException;
import com.natodobry.natofoods.domain.repository.CidadeRepository;
import com.natodobry.natofoods.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        List<Cidade> cidade = cidadeRepository.findAll();
        return ResponseEntity.ok(cidade);
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscaId(@PathVariable Long cidadeId) {
        return cadastroCidade.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            return cadastroCidade.salvar(cidade);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable @Valid Long cidadeId,
                                       @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cadastroCidade.salvar(cidadeAtual);
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }


}

