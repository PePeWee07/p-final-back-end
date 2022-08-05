package com.example.demo.controller;

import java.util.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.infraestructura.repositorio.PersonaRepository;
import com.example.demo.model.Persona;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(path = "/api")
public class personaControlador {

    @Autowired
    PersonaRepository personaRepository;
    
    // GET PERSONAS
    @GetMapping(value = "/personas")
    @ResponseStatus(HttpStatus.OK)
    public List<Persona> getPersonas(){
      List<Persona> personas = new ArrayList<Persona>(); 
      personas = personaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
      return personas;
    }

    //GET ID PERSONA
   /*  @GetMapping("/persona/{id}")
    public ResponseEntity<?> getById2(@PathVariable int id){
		Persona personaID;
    Map<String, Object> response = new HashMap<>();
      try {
        personaID = personaRepository.findById(id).get();
      } catch (DataAccessException e) {
        response.put("mensaje", "Error: no se pudo econtrar el persona ID: "
              .concat(id + " no existe en la base de datos!"));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
      
      response.put("mensaje", "La persona se ha econtrado con éxito en la BD");
		response.put("Persona", personaID);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    } */

    @GetMapping("/persona/{id}")
	public Persona getById(@PathVariable int id) {
		Persona persona = personaRepository.findById(id).get();
		return persona;
	}

    //DELETE PERSONA
    @DeleteMapping("/persona/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable int id) {

      Map<String, Object> response = new HashMap<>();
      try {
        personaRepository.deleteById(id);
      }catch(DataAccessException e) {
          response.put("mensaje", "Error: no se pudo eliminar, la persona ID: "
              .concat(id + " no existe en la base de datos!"));
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
  
      response.put("mensaje", "La persona se ha eliminado con éxito en la BD");

      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    //POST PERSONA
    @PostMapping("/persona")
	  public ResponseEntity<?> savePerson(@RequestBody Persona persona) {
		Persona personaGrabar;
		Map<String, Object> response = new HashMap<>();

		try {
			personaGrabar = personaRepository.save(persona);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el inserción en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Persona se ha insertado con éxito en la BD");
		response.put("Persona", personaGrabar);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

    //PUT PERSONA
    @PutMapping("/persona/{id}")
    public ResponseEntity<?> modificarPersona(@RequestBody Persona persona, BindingResult result, @PathVariable int id) {
		
		Map<String, Object> response = new HashMap<>();
		Persona person;

		try {
		person = personaRepository.findById(id).get();
		}catch(DataAccessException e) {
	
				response.put("mensaje", "Error: no se pudo editar, la persona ID: "
						.concat(id + " no existe en la base de datos!"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Persona personaModificada = new Persona();
		
		try {
			person.setNombre(persona.getNombre());
			person.setApellido(persona.getApellido());
			person.setInteres(persona.getInteres());
			person.setComentario(persona.getComentario());
			person.setFoto(persona.getFoto());
			
			personaModificada = personaRepository.save(person); 
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la persona en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("mensaje", "Error al actualizar la persona en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La persona se ha modificado con éxito en la BD");
		response.put("Persona", personaModificada);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

    //PAGINACION
    @GetMapping("/personas/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Persona> paginadorPersona(@PathVariable Integer page){
		if(page == 0){
       page=1;
    }
    //   Pageable pageable = PageRequest.of(page, 4,Sort.by("id"));
      Pageable pageable = PageRequest.of(page-1,4, Sort.by(Sort.Direction.DESC, "id"));
      Page<Persona> personas = personaRepository.findAll(pageable);
      return personas;
    }

}
