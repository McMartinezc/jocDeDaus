package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Controller;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players/")
public class UserController {
    @Autowired
    private UserService userService;


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
    public ResponseEntity<UserDto> updateJugador (@PathVariable Long id, @RequestBody UserDto userDto){
        //No existe jugador
        if(userDto.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    //Borrar llistat de tirades d'un jugador
    @DeleteMapping("/deleteTirades/{id}")
    public ResponseEntity<UserDto> deleteTiradaJugador (@PathVariable Long id){
        userService.deletetirades(id);
        return ResponseEntity.noContent().build();
    }

    //JOC

    //Jugador tira els daus
    @GetMapping("/tirada/{id}")
    public ResponseEntity<Tirada> jugadorTiraDaus (@PathVariable Long id){
        return ResponseEntity.ok(userService.jugadorTiraDaus(id));
    }

    //Percentatge d'èxit d'un jugador
    @GetMapping("/games/{id}")
    public ResponseEntity<List<Tirada>> percentatgeExitJugador(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
