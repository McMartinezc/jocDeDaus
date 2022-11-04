package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Exception.AlreadyExist;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository.TiradaRepository;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TiradaRepository tiradaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, TiradaRepository tiradaRepository) {
        this.userRepository = userRepository;
        this.tiradaRepository = tiradaRepository;
    }

    //METODES CRUD

    //Crear jugador
    public UserDto crearUser (UserDto userDto){

        //Convertim dto a entitat
        User user = convertDTOAEntitat(userDto);

        //Verifiquem condiciones si el nom és null o és buit guardem com anonim
        if(userDto.getNomJugador()== null || "".equals(userDto.getNomJugador())){
            user.setNomJugador("Anonim");
        }

       //Verifiquem que nom no existeixi a al base de dades
       if(userRepository.existsByNomJugador(userDto.getNomJugador())){
           throw new AlreadyExist("Aquest nom "+userDto.getNomJugador() +" ja existeix.");
      }
        userRepository.save(user);

        //Convertirm entitat a dto per enviar al usuari
        return convertEntitatADto(user);

    }

    //Buscar un jugador
    public UserDto getOne (Long id){

        //Busquem jugador
        Optional<User> jugador = userRepository.findById(id);

        //Si no existeix
        if(jugador.isEmpty()){
            throw new AlreadyExist("Jugador no existeix.");
        }

        //Convertim entitat a dto per retornar al usuari
        UserDto userDto = convertEntitatADto(jugador.get());
        return userDto;
    }

    //Update jugador
    public UserDto updateUser (Long id, UserDto userDto){

        //Convertim dto a entitat
        User user = convertDTOAEntitat(userDto);

        //Verifiquem que existeix el nom del jugador
        if(!userRepository.existsById(id)){
            throw new AlreadyExist("Aquest usuari no existeix");
        }
        //Si existeix, modifiquem el nom del jugador, ho guardem al repository i convertim a dto per mostrar a l'usuari
        if(userRepository.existsByNomJugador(userDto.getNomJugador())){
            throw new AlreadyExist("Aquest nom d'usuari ja existeix.");
        }

        user.setNomJugador(userDto.getNomJugador());
        userRepository.save(user);
        return convertEntitatADto(user);
    }


    //Mostra tots els jugadors
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntitatADto)//Convertim entitat a dto
                .collect(Collectors.toList());
    }

    //Borrar llistat de tirades d'un jugador
    public UserDto deletetirades(Long id){
        //Busquem jugador
        Optional<User> jugador = userRepository.findById(id);

        //Si no existeix
        if(jugador.isEmpty()){
            throw new AlreadyExist("Jugador no existeix.");
        }
        User user = jugador.get();
        user.getMisTiradas().removeAll(user.getMisTiradas());
        userRepository.save(user);
        return convertEntitatADto(user);
    }


    //Llistat de percentatges
    //Retorna el ranking mig de tots els jugadors/es del sistema.

    //Retorna la llista de tots els jugadors amb el seu percentatge d'èxit
    public List<User> getListJugadorsRanking (List<User> llistaJugadors){
        List<User> jugadors = new ArrayList<>();
        double percentatgeExit;

        for(User user: llistaJugadors){
            percentatgeExit = calculaPercentatgeExitJugador(user.getId());
            user.setPercentatge(percentatgeExit);
            jugadors.add(user);
        }
        return jugadors;
    }

    //Retorna el millor jugador
    public User getJugadorLoser(){
        List<User> llistatJugadors = userRepository.findAll();
        List<User> jugadorsRanking = getListJugadorsRanking(llistatJugadors);
        jugadorsRanking.stream().collect(Collectors.groupingBy().toList())
    }

    //CÀLCUL PERCENTATGE D'UN JUGADOR
    //Càlcul de percentatge d'èxit del jugador
    private double calculaPercentatgeExitJugador (Long id){
        double percentatgeExit =0;
        Optional <User> userOptional = userRepository.findById(id);
        int tamanyLlista = userOptional.get().getMisTiradas().size();
        int totalGuanyat =0;

        //Comprovem que la llista no està buida
        if (userOptional.get().getMisTiradas().isEmpty()){
            throw new AlreadyExist("Jugador no té tirades");
        }

        if (userOptional.get().getMisTiradas() != null && tamanyLlista > 0){
            for (Tirada tirada: userOptional.get().getMisTiradas()){
                if (tirada.isGuanya()){
                    totalGuanyat ++;
                }
            }
            percentatgeExit = (totalGuanyat *100) / tamanyLlista;
        }
        // userOptional.get().setPercentatge(percentatgeExit);

        return percentatgeExit;

    }

    //MODELMAPPERS

    //Convertim DTO a entitat utilitzan el ModelMapper
    public User convertDTOAEntitat (UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    //Convertim entitat a DTO utilitzan el ModelMapper
    public UserDto convertEntitatADto (User user){
        return modelMapper.map(user, UserDto.class);
    }

}
