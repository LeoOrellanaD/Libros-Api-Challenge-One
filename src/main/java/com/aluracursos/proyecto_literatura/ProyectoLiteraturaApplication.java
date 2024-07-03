package com.aluracursos.proyecto_literatura;

import com.aluracursos.proyecto_literatura.principal.Principal;
import com.aluracursos.proyecto_literatura.service.AutorService;
import com.aluracursos.proyecto_literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoLiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroService libroService;

	@Autowired
	private AutorService autorService;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroService ,autorService);
		principal.muestraMenu();
	}
}
