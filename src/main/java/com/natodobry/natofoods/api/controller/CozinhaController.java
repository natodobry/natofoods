package com.natodobry.natofoods.api.controller;

import com.natodobry.natofoods.domain.model.Cozinha;
import com.natodobry.natofoods.domain.repository.CozinhaRepository;
import com.natodobry.natofoods.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId){
        return cadastroCozinha.buscarOuFalhar(cozinhaId);
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody @Valid Cozinha cozinha){
        Cozinha cozinha1 = cadastroCozinha.salvar(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinha1);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@RequestBody @Valid Cozinha cozinha,
                             @PathVariable Long id){
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
            return cadastroCozinha.salvar(cozinhaAtual);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
            cadastroCozinha.excluir(id);
    }
}
