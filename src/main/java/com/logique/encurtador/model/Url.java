package com.logique.encurtador.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime data;

    @Type(type = "text")
    private String urlOriginal;
    private String urlEncurtada;
    private Integer usuarioId;

}

