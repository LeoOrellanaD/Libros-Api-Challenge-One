package com.aluracursos.proyecto_literatura.service;

public interface IOConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
