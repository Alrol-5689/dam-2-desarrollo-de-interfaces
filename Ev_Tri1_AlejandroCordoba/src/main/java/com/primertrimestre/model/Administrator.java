package com.primertrimestre.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "administrators")

// Lombok --> Como no tiene atributos estaría creando idénticos constructores y por eso da error 

public class Administrator extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;

}
