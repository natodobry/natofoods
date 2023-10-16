package com.natodobry.natofoods.domain.model;

import com.natodobry.natofoods.domain.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subTotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataEntrega;

    @Embedded
    private Endereco endereco;

    private StatusPedido status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedido = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;









}
