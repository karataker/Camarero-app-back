package com.es.tfm.ms_camarero_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private String correoElectronico;
    private String telefono;
    private int numeroComensales;
    private String zonaPreferida;
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;
}
