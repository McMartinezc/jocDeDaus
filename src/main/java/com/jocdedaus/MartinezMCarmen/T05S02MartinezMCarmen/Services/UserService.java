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

import java.util.*;
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
        if(user.getNomJugador()== null || "".equals(user.getNomJugador())){
            user.setNomJugador("Anonim");
        }
       //Verifiquem que nom no existeixi a al base de dades
       if(userRepository.existsByNomJugador(user.getNomJugador())){
           throw new AlreadyExist("Aquest nom ja existeix.");
      }
       //En el cas que sigui anònim guardem
       if(user.getNomJugador().equalsIgnoreCase("anonim")){
           user.setNomJugador(user.getNomJugador());
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
    public UserDto updateUser (Long id, UserDto userDto) {

        if (userRepository.existsByNomJugador(userDto.getNomJugador())) {
            throw new AlreadyExist("El nom del jugador ja existeix");
        }

        //Convertim dto a entitat
        User user = convertDTOAEntitat(userDto);

        Optional<User> userOptional = userRepository.findById(id);
        userOptional.get().setNomJugador(user.getNomJugador());

        //Guardem al repository
        userRepository.save(userOptional.get());

        //Retornem un dto
        return convertEntitatADto(userOptional.get());
    }
    //Mostra tots els jugadors
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntitatADto)//Convertim entitat a dto
                .collect(Collectors.toList());
    }

    //Borrar llistat de tirades d'un jugador
    public UserDto deleteTirades(Long id){
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


    //METODES PERCENTATGES JUGADORS

    //Retorna el llistat de jugadors amb el seu percentatge d'exits per mostrar per pantalla ordenat per orde alfabetic(TreeMap)
    public TreeMap<String, Double> llistatRankingJugadors(){
        //Creem llistat de tots el jugadors del repository
        List<User> llistatJugadors = userRepository.findAll();
        //Creem un Map per guardar nom de jugador i percentatge
        TreeMap<String, Double> llistatPercentatgeJugadors = new TreeMap<>();

        if(!llistatJugadors.isEmpty()){
            List<Tirada> tiradaJugadorActual;

            for(User jugador : llistatJugadors){
                tiradaJugadorActual = tiradaRepository.getTiradasByUserId(jugador.getId());

                if(!tiradaJugadorActual.isEmpty()){
                    String nomJugador = jugador.getNomJugador();
                    Double percentatge = jugador.calculaPercentatgeExitJugador();
                    llistatPercentatgeJugadors.put(nomJugador, percentatge);
                }else {
                    llistatPercentatgeJugadors.put(jugador.getNomJugador(), jugador.getPercentatge());
                }
            }
        }
        return llistatPercentatgeJugadors;
    }

    //Llista de tots els jugadors amb el seu percentatge, per poder buscar millor i pitjor jugador
    public List<User> getListJugadorsRanking (List<User>llistaJugadors){
        List<User> jugadors = new ArrayList<>();
        double percentatge;

        for(User user: llistaJugadors){
            percentatge = user.calculaPercentatgeExitJugador();
            user.setPercentatge(percentatge);
            jugadors.add(user);
            userRepository.save(user);
        }
        return jugadors;
    }
    //Calcula la mitja del jugadors
    public double mitjaJugadors(){
        List<User> llistatJugadors = userRepository.findAll();
        double mitja = llistatJugadors.stream().mapToDouble(User::calculaPercentatgeExitJugador).average().orElse(0.0);
        return Math.round(mitja *100.0)/100.0;
    }

    //Retorna el millor jugador
    public UserDto jugadorBest() {
        List<User> llistatJugadors = userRepository.findAll();
        List<User> jugadors = getListJugadorsRanking(llistatJugadors);
        jugadors.sort(Comparator.comparing(User::calculaPercentatgeExitJugador));
        return convertEntitatADto (jugadors.get(jugadors.size() - 1));
    }
    //Retorna el pitjor jugador
    public UserDto jugadorLoser(){
        List<User> llistatJugadors =userRepository.findAll();
        List<User> jugadors = getListJugadorsRanking(llistatJugadors);
        jugadors.sort(Comparator.comparing(User::calculaPercentatgeExitJugador));
        return convertEntitatADto(jugadors.get(0));
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
