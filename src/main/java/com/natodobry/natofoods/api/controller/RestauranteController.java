package com.natodobry.natofoods.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natodobry.natofoods.api.assembler.RestauranteModelAssembler;
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
            Restaurante restaurante = toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput,
                                 @PathVariable Long restauranteId) {
        try {
        Restaurante restaurante = toDomainObject(restauranteInput);
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");


            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

//    @PatchMapping("/{restauranteId}")
//    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
//                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//        Optional<Restaurante> restauranteatual = restauranteRepository.findById(restauranteId);
//        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//        merge(campos, restauranteAtual, request);
//        validate(restauranteAtual, "restaurante");
//        return atualizar(restauranteAtual, restauranteId);
//    }
//
//    private void validate(Restaurante restaurante, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName );
//        validator.validate(restaurante, bindingResult);
//
//        if (bindingResult.hasErrors()){
//            throw new ValidacaoException(bindingResult);
//        }
//    }

//    private void merge(Map<String, Object> dadosOringem, Restaurante restauranteDestino, HttpServletRequest request) {
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOringem, Restaurante.class);
//            dadosOringem.forEach((nomePropriedade, valorPropriedade) -> {
//                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//                field.setAccessible(true);
//
//                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//
//                ReflectionUtils.setField(field, restauranteDestino, novoValor);
//            });
//        }catch (IllegalArgumentException e){
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//    }



    private Restaurante toDomainObject(RestauranteInput restauranteInput){
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);
        return  restaurante;
    }
}
