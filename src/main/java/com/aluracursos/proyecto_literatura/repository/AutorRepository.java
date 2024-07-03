package com.aluracursos.proyecto_literatura.repository;

import com.aluracursos.proyecto_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :year AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento > :year)")
    List<Autor> findByYearVivo(int year);
}