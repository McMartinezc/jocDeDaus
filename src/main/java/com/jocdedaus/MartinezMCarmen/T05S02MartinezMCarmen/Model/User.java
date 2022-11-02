package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Data

@Entity
@Table(name="jugador")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_jugador")
    private Long id;

    @Column(name ="nom_jugador")
    private String nomJugador;

    @Column(name="data_registre")
    private LocalDate dataRegistre;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tirada> misTiradas;

    //Constructors
    public User() {
    }

    public User(Long id, String nomJugador, LocalDate dataRegistre) {
        this.id = id;
        this.nomJugador = nomJugador;
        this.dataRegistre = LocalDate.now();
        misTiradas = new ArrayList<Tirada>();
    }


    //Guardem la tirada del jugador a la llista de les seves tirades
    public void addTirada (Tirada miTirada){

        if (misTiradas ==null){
            misTiradas = new ArrayList<Tirada>();
        }
        misTiradas.add(miTirada);
    }



}
