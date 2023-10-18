package com.natodobry.natofoods.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natodobry.natofoods.api.assembler.RestauranteModelAssembler;
import com.natodobry.natofoods.api.assembler.RestauranteModelDisassembler;
import com.natodobry.natofoods.api.model.CozinhaModel;
import com.natodobry.natofoods.api.model.RestauranteModel;
import com.natodobry.natofoods.api.model.input.RestauranteInput;
import com.natodobry.natofoods.core.validation.ValidacaoException;
import com.natodobry.natofoods.domain.model.Cozinha;
import com.natodobry.natofoods.domain.model.Restaurante;
import com.natodobry.natofoods.domain.model.exception.EntidadeNaoEncontradaException;
import com.natodobry.natofoods.domain.model.exception.NegocioException;
import com.natodobry.natofoods.domain.repository.RestauranteRepository;
import com.natodobry.natofoods.domain.service.CadastroRestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;
    @Autowired
    private RestauranteModelDisassembler restauranteModelDisassembler;
    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteModel> listar() {

        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscaPorid(@PathVariable Long restauranteId) {


     Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);


        return restauranteModelAssembler.toModel(restaurante);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {


        try {
            Restaurante restaurante = restauranteModelDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput,
                                 @PathVariable Long restauranteId) {
        try {
        Restaurante restaurante = restauranteModelDisassembler.toDomainObject(restauranteInput);
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");


            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
