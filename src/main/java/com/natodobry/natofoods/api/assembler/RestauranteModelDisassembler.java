package com.natodobry.natofoods.api.assembler;

import com.natodobry.natofoods.api.model.input.RestauranteInput;
import com.natodobry.natofoods.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput){

        return modelMapper.map(restauranteInput, Restaurante.class);

    }
}
