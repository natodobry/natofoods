package com.natodobry.natofoods.domain.service;

import com.natodobry.natofoods.domain.model.Cozinha;
import com.natodobry.natofoods.domain.model.Restaurante;
import com.natodobry.natofoods.domain.model.exception.EntidadeNaoEncontradaException;
import com.natodobry.natofoods.domain.model.exception.RestauranteNaoEncontradoException;
import com.natodobry.natofoods.domain.repository.CozinhaRepository;
import com.natodobry.natofoods.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long RestauranteId){
        return restauranteRepository.findById(RestauranteId)
                .orElseThrow(()-> new RestauranteNaoEncontradoException(RestauranteId));

    }
}
