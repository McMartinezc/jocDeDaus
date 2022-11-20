package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Controller;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

//Para el uso de la anotación de crear fecha
//@EnableJpaAuditing
@RestController
@RequestMapping("/players/")
public class UserController {
    @Autowired
    private UserService userService;
    private static final Log log = LogFactory.getLog(UserController.class);

    //METODES CRUD

    //Mostra tots els jugadors
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Crear jugador
    @PostMapping("/addJugador")
    public ResponseEntity<UserDto> addJugador (@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.crearUser(userDto), HttpStatus.CREATED);
    }

    //Buscar un jugador
    @GetMapping("getOne/{id}")
    public ResponseEntity<UserDto> getOne (@PathVariable Long id){
        return new ResponseEntity<>(userService.getOne(id), HttpStatus.OK);
    }

    //Update jugador
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateJugador (@PathVariable("id") Long id, @RequestBody UserDto userDto){
        
        return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.CREATED);
    }

    //Borrar llistat de tirades d'un jugador
    @DeleteMapping("/deleteTirades/{id}")
    public ResponseEntity<UserDto> deleteTiradaJugador (@PathVariable Long id){
        userService.deleteTirades(id);
        return ResponseEntity.noContent().build();
    }

    //LLISTATS JUGADORS

    //GET /players/ranking: retorna el ranking de tots els jugadors/es del sistema.
    @GetMapping("/ranking")
    public ResponseEntity<TreeMap<String, Double>> getLlistatRankingMigJugadors(){
        return new ResponseEntity<>(userService.llistatRankingJugadors(), HttpStatus.OK);
    }
    //GET /players/: retorna el percentatge mitjà d’èxits de tots els jugadors.
    @GetMapping("/")
    public ResponseEntity<Double> getPercentatgeMitja(){
        double percentatgeExit= userService.mitjaJugadors();
        return new ResponseEntity<>(percentatgeExit, HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a  amb pitjor percentatge d’èxit.
    @GetMapping("/ranking/loser")
    public ResponseEntity<UserDto> getJugadorLoser(){
        return new ResponseEntity<>(userService.jugadorLoser(), HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a amb millor percentatge d’èxit.
    @GetMapping("/ranking/winner")
    public ResponseEntity<UserDto> getJugadorBest(){
        return new ResponseEntity<>(userService.jugadorBest(), HttpStatus.OK);
    }
}
