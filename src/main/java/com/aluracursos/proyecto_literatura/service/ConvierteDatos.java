package com.aluracursos.proyecto_literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IOConvierteDatos {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            T result = mapper.readValue(json, clase);
            return result;
        } catch (JsonProcessingException e) {
            System.out.println("Error durante la deserialización: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

