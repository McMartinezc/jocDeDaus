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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nom_jugador")
    private String nomJugador;

    @Column(name="data_registre")
    private LocalDate dataRegistre;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Tirada> misTiradas;

    //Constructors
    public User() {
    }

    public User(Long id, String nomJugador, LocalDate dataRegistre, List<Tirada> misTiradas) {
        this.id = id;
        this.nomJugador = nomJugador;
        this.dataRegistre = dataRegistre;
        this.misTiradas = misTiradas;
    }


    //Guardem la tirada del jugador a la llista de les seves tirades
    public void addTirada(Tirada miTirada){
        if (misTiradas ==null){
            misTiradas = new ArrayList<Tirada>();
        }
        misTiradas.add(miTirada);
    }
}
