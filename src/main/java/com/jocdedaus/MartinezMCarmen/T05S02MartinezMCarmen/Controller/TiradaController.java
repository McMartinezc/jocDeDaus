package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Controller;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services.TiradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players/")
public class TiradaController {

    @Autowired
    private TiradaService tiradaService;

    //JOC
    //Jugador tira els daus
    @PostMapping("/tirada/{id}")
    public ResponseEntity <Tirada> jugadorTiraDaus (@PathVariable("id") Long id){

        Tirada tirada = tiradaService.jugadorTiraDados(id);

        if (tirada == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>(tirada, HttpStatus.OK);
    }

    //RETORNA LLISTATS

    //Retorna el llistat de jugades d'un jugador
    @GetMapping("/tiradesJugador/{id}")
    public ResponseEntity<List<Tirada>> mostrarTiradesJugador (@PathVariable("id") Long id){

       if (tiradaService.getListTiradesJugador(id) == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return  ResponseEntity.ok(tiradaService.getListTiradesJugador(id));
    }


}
