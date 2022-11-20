package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class T05S02MartinezMCarmenApplication {

	public static void main(String[] args) {
		SpringApplication.run(T05S02MartinezMCarmenApplication.class, args);
	}

	//Utilitzem modelmapper per poder mapejar per convertir d'identitat a dto, o al contrari
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
