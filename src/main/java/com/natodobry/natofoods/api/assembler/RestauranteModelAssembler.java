package com.natodobry.natofoods.api.assembler;

import com.natodobry.natofoods.api.model.CozinhaModel;
import com.natodobry.natofoods.api.model.RestauranteModel;
import com.natodobry.natofoods.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {


    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinhaModel(cozinhaModel);
        return restauranteModel;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
