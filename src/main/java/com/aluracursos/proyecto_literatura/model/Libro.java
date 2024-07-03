package com.aluracursos.proyecto_literatura.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    private List<String> idiomas = new ArrayList<>();

    private Double numeroDescargar;




    public Libro(){}
    public Libro(DatosLibros datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autores = datosLibro.autores().stream()
                .map(datoAutor -> new Autor(datoAutor.nombre(), datoAutor.fechaDeNacimiento(), datoAutor.fechaDeFallecimiento()))
                .collect(Collectors.toList());
        this.idiomas = new ArrayList<>(datosLibro.idiomas());
        this.numeroDescargar = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autor) {
        this.autores = autor;
    }

    public Double getNumeroDescargar() {
        return numeroDescargar;
    }

    public void setNumeroDescargar(Double numeroDescargar) {
        this.numeroDescargar = numeroDescargar;
    }

    @Override
    public String toString() {
        return
                ", titulo='" + titulo + '\'' +
                ", autor='" + autores + '\'' +
                ", idiomas=" + idiomas +
                ", numeroDescargar=" + numeroDescargar;
    }
}
