package com.natodobry.natofoods.domain.repository;

import com.natodobry.natofoods.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante,Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join r.cozinha")
    List<Restaurante> findAll();
    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
  //  List<Restaurante> nomeContainingAndCozinhaId(String nome, Long cozinha);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
    List<Restaurante> findTop2RestauranteByNomeContaining(String nome);

    int countByCozinhaId(Long id);

}