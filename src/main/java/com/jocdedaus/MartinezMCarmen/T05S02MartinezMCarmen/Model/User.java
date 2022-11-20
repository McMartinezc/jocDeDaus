package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Exception.AlreadyExist;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@Data

//Anotación para poder usar el crear fecha de registro
//@EntityListeners(AuditingEntityListener.class)
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
    //@CreateDate
    //Anotación que crea la fecha
    @CreationTimestamp
    private LocalDate dataRegistre;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tirada> misTiradas;

    private double percentatge;

    //Constructors
    public User() {
    }

    public User(Long id, String nomJugador, LocalDate dataRegistre) {
        this.id = id;
        this.nomJugador = nomJugador;
        this.dataRegistre = dataRegistre;
        misTiradas = new ArrayList<Tirada>();
    }

   //Càlcul de percentatge d'èxit del jugador
    public double calculaPercentatgeExitJugador(){
        int totalGuanyat =0;
        int tamanyLlista = misTiradas.size();

        //Comprovem que la llista no està buida
        if (misTiradas.isEmpty()){
            throw new AlreadyExist("Jugador no té tirades");
        }

        if (misTiradas != null && tamanyLlista > 0){
            for (Tirada tirada: misTiradas){
                if (tirada.isGuanya()){
                    totalGuanyat ++;
                }
            }
            percentatge = (totalGuanyat *100) / tamanyLlista;
        }
        return percentatge;
    }

    //Guardem la tirada del jugador a la llista de les seves tirades
    public void addTirada (Tirada miTirada){

        if (misTiradas ==null){
            misTiradas = new ArrayList<Tirada>();
        }
        misTiradas.add(miTirada);
    }

}
