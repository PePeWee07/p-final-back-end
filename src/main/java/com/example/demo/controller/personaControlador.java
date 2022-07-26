package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.infraestructura.repositorio.PersonaRepository;
import com.example.demo.model.Persona;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class personaControlador {

    @Autowired
    PersonaRepository personaRepository;
    
    @GetMapping("/personas")
    @ResponseStatus(HttpStatus.OK)
    public List<Persona> getPersonas(){
      List<Persona> persona = new ArrayList<Persona>(); 
      persona = personaRepository.findAll(); 
      return persona;
    }

    // @GetMapping("/personas")
    // public ArrayList<Persona> getPersonas() {
    //   return (ArrayList<Persona>) personaRepository.findAll();
    // }

}
