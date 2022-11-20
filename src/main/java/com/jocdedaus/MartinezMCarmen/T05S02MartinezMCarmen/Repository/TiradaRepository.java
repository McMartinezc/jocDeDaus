package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiradaRepository extends JpaRepository <Tirada, Long> {
List<Tirada> getTiradasByUserId(Long id);

}
