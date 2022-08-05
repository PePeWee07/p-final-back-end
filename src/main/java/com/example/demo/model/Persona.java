package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table (name = "persona")
public class Persona {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String apellido;
    private String interes;
	private String comentario;
	private String foto;

    public Persona(){}
	
	public Persona(int id, String nombre, String apellido, String interes, String comentario, String foto){
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.interes = interes;
		this.comentario = comentario;
		this.foto = foto;
	}

    //get and set
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getInteres() {
		return interes;
	}
	public void setInteres(String interes) {
		this.interes = interes;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
