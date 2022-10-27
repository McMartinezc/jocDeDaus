package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString

public class UserDto {
    private Long id;
    private String nomJugador;
    private LocalDate dataRegistre;
    private List<Tirada> misTiradas;
}
