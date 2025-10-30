package com.primertrimestre.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "modules")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int creditsECTS; 
	
	@Column(nullable = false)
	private String name;
	
	//@Setter(AccessLevel.NONE) No hace falta porque al escribir el setter lombok lo ignora
	@Column(nullable = false)
	private String shortName;
	
	@ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
	private Set<Student> students = new HashSet<>();
	
	public void setShortName(String shortName) {this.shortName = shortName.toUpperCase();} // Lombok respeta este setter y no lo sobreescribe
	
	
	

}
