package com.aluracursos.proyecto_literatura.principal;

import com.aluracursos.proyecto_literatura.model.Autor;
import com.aluracursos.proyecto_literatura.model.Datos;
import com.aluracursos.proyecto_literatura.model.DatosLibros;
import com.aluracursos.proyecto_literatura.model.Libro;
import com.aluracursos.proyecto_literatura.service.AutorService;
import com.aluracursos.proyecto_literatura.service.ConsumoAPI;
import com.aluracursos.proyecto_literatura.service.ConvierteDatos;
import com.aluracursos.proyecto_literatura.service.LibroService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private LibroService libroService;
    private AutorService autorService;

    public Principal(LibroService libroService , AutorService autorService){
        this.libroService = libroService;
        this.autorService = autorService;
    }


    public void muestraMenu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """ 
                    1 - Buscar Libro por titulo
                    2 - Mostrar Libros registrados
                    3 - Mostrar Autores registrados
                    4 - Mostrar Autores vivos en un determinado año
                    5 - Mostrar Libros por idioma
              
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorYear();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("Opcion Invalida");
            }
        }
    }



    private void buscarLibro() {
        System.out.println("Ingrese el titulo del libro:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ", "+"));
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroSearch = datos.resultados().stream().filter(t -> t.titulo().toUpperCase().contains(tituloLibro.toUpperCase())).findFirst();
        if(libroSearch.isPresent()){
            DatosLibros libro = libroSearch.get();
            System.out.println("****** LIBRO ******");
            System.out.println("Título: " + libro.titulo());
            String autores = libro.autores().stream()
                    .map(autor -> autor.nombre() + " (" + autor.fechaDeNacimiento() + ")")
                    .collect(Collectors.joining(", "));
            System.out.println("Autor(es): " + autores);
            System.out.println("Idioma(s): " + String.join(", ", libro.idiomas()));
            System.out.println("Número de descargas: " + libro.descargas());
            System.out.println("*******************");
            Libro librofinal = new Libro(libro);
            librofinal  = libroService.guardarOActualizarLibro(librofinal);
            System.out.println("Libro guardado o actualizado con éxito: " + libro);

        }else{
            System.out.println("libro no encontrado");
        }
    }

    private void mostrarLibrosRegistrados() {

        List<Libro> libros = libroService.obtenerLibrosRegistrados();
        if (libros.isEmpty()){
            System.out.println("No hay libros registrados");
        }else{
            System.out.println("****** LIBROS REGISTRADOS ******");
            libros.forEach(libro -> {
                System.out.println("****** LIBRO ******");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutores().stream()
                        .map(Autor::getNombre)
                        .collect(Collectors.joining(", ")));
                System.out.println("Idioma(s): " + String.join(", ", libro.getIdiomas()));
                System.out.println("Número de descargas: " + libro.getNumeroDescargar());
                System.out.println("---------------------------------");
            });
        }

    }

    public void mostrarAutoresRegistrados(){
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("****** AUTORES REGISTRADOS ******");
            autores.forEach(autor -> {
                System.out.println("****** AUTOR ******");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println("Libros:");
                autor.getLibros().forEach(libro -> System.out.println(" - " + libro.getTitulo()));
                System.out.println("---------------------------------");
            });
        }
    }

    public void mostrarAutoresVivosPorYear(){
        System.out.println("Ingrese el año para verificar autores vivos:");
        int year = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorService.obtenerAutoresVivosPorYear(year);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + year);
        } else {
            System.out.println("Autores vivos en el año " + year + ":");
            autoresVivos.forEach(autor -> {
                System.out.println("****** AUTOR ******");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println("Libros:");
                autor.getLibros().forEach(libro -> System.out.println(" - " + libro.getTitulo()));
                System.out.println("---------------------------------");
            });
        }
    }


    private void buscarLibrosPorIdioma(){
        System.out.println("Ingrese el idioma que desea buscar:");

        var menu = """ 
                    es - espanol
                    en - ingles
                    fr - frances
                    pt - portugues
                    """;
        System.out.println(menu);
        String codigoIdioma = teclado.nextLine().trim();
        List<Libro> librosPorIdioma = libroService.obtenerLibrosPorIdioma(codigoIdioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            System.out.println("****** LIBROS EN " + codigoIdioma.toUpperCase() + " ******");
            librosPorIdioma.forEach(libro -> {
                System.out.println("****** LIBRO ******");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutores().stream()
                        .map(Autor::getNombre)
                        .collect(Collectors.joining(", ")));
                System.out.println("Idioma(s): " + String.join(", ", libro.getIdiomas()));
                System.out.println("Número de descargas: " + libro.getNumeroDescargar());
                System.out.println("---------------------------------");
            });
        }
    }



}


