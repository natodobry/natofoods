package com.natodobry.natofoods.api.controller;

import com.natodobry.natofoods.domain.model.Estado;
import com.natodobry.natofoods.domain.repository.EstadoRepository;
import com.natodobry.natofoods.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> estados() {
        return estadoRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Estado buscaPorId(@PathVariable Long id){
      return estadoService.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody @Valid Estado estado){
        Estado salvar = estadoService.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping(value = "/{id}")
    public Estado atualizar(@RequestBody @Valid Estado estado,
                            @PathVariable Long id){
        Estado estadoAtual = estadoService.buscarOuFalhar(id);
            BeanUtils.copyProperties(estado, estadoAtual, "id");
            return estadoService.salvar(estadoAtual);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
         estadoService.excluir(id);

    }
}
