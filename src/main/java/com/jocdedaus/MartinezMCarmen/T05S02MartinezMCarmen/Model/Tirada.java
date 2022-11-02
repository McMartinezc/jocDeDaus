package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Data

@Entity
@Table(name= "tirada")

public class Tirada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTirada;
    @Column(name="dau1")
    private int dau1;
    @Column(name="dau2")
    private int dau2;
    @Column(name="Partida_Guanyada")
    private boolean guanya;

    //Relació molts a un (moltes tirades, només un jugador)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_jugador")
    private User user;

    //Constructors
    public Tirada() {
    }

    public Tirada(int idTirada, int dau1, int dau2, boolean guanya, User user) {
        this.idTirada = idTirada;
        this.dau1 = dau1;
        this.dau2 = dau2;
        this.guanya = guanya;
        this.user = user;
    }

    public Tirada (User user) {
        this.dau1 = generarTirada();
        this.dau2 = generarTirada();
        this.guanya = resultatTirada();
        this.user = user;
    }

    //Valor random dels daus
    public int generarTirada(){
        int random = (int) Math.floor(Math.random()* 6 + 1); //Metode que genera un número random del 1 al 6
        return random;
    }

    //Metode que comprova el resultat sigui 7 guanya, dona true si ha guanyat o false si ha perdut
    public boolean resultatTirada(){
        boolean resultat;

        if(this.dau1 + this.dau2 == 7){
            resultat = true;
        }else{
            resultat = false;
        }
        return resultat;
    }
}
