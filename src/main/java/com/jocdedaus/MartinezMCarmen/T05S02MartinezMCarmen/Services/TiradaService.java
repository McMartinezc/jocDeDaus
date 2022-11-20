package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Exception.AlreadyExist;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository.TiradaRepository;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TiradaService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TiradaRepository tiradaRepository;

    //Tirada jugador i s'afegeix a la llista de jugades del jugador
    public Tirada jugadorTiraDados (Long id){

        Optional<User> jugador = userRepository.findById(id);

        if(jugador.isEmpty()){
            throw  new AlreadyExist("No existeix jugador amb aquesta id.");
        }
        User user = jugador.get();
        Tirada tirada = new Tirada (user);
        user.addTirada(tirada);
        return tiradaRepository.save(tirada);
    }

    //Mostrar tirades d'un jugador
    public List<Tirada> getListTiradesJugador (Long id){
        Optional <User> userOptional = userRepository.findById(id);

        if(userOptional.get().getMisTiradas().isEmpty()){
            throw new AlreadyExist("Jugador no té tirades");
        }
       return userOptional.get().getMisTiradas().stream().collect(Collectors.toList());
    }
}
