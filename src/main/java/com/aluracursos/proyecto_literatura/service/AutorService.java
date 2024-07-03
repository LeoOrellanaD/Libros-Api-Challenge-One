package com.aluracursos.proyecto_literatura.service;

import com.aluracursos.proyecto_literatura.model.Autor;
import com.aluracursos.proyecto_literatura.repository.AutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private  AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> obtenerTodosLosAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(autor -> autor.getLibros().size());
        return autores;
    }

    public List<Autor> obtenerAutoresVivosPorYear(int year) {
        return autorRepository.findByYearVivo(year);
    }
}
