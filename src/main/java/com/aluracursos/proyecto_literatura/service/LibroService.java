package com.aluracursos.proyecto_literatura.service;

import com.aluracursos.proyecto_literatura.model.Autor;
import com.aluracursos.proyecto_literatura.model.Libro;
import com.aluracursos.proyecto_literatura.repository.AutorRepository;
import com.aluracursos.proyecto_literatura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public Libro guardarOActualizarLibro(Libro nuevoLibro) {
        Libro libro = libroRepository.findByTitulo(nuevoLibro.getTitulo())
                .orElse(nuevoLibro);

        List<Autor> autoresActualizados = nuevoLibro.getAutores().stream()
                .map(autor -> autorRepository.findByNombre(autor.getNombre())
                        .orElse(autor))
                .collect(Collectors.toList());

        libro.setAutores(autoresActualizados);
        return libroRepository.save(libro);
    }

    public List<Libro> obtenerLibrosRegistrados(){
        return libroRepository.findAll();
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
       return libroRepository.findByIdiomasContaining(idioma);
    }



}
