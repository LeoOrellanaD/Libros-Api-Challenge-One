package com.aluracursos.proyecto_literatura.repository;

import com.aluracursos.proyecto_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTitulo(String titulo);

    @Query(value = "SELECT * FROM libros WHERE :idioma = ANY(idiomas)", nativeQuery = true)
    List<Libro> findByIdiomasContaining(String idioma);

}


