package com.primertrimestre.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "student_modules",
	  joinColumns = @JoinColumn(
			  name = "student_id",
			  foreignKey = @ForeignKey(name = "fk_student_modules_students")),
	  inverseJoinColumns = @JoinColumn(
			  name = "module_id",
			  foreignKey = @ForeignKey(name = "fk_student_modules_modules")),
	  uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","module_id"}))
	private Set<Module> modules = new HashSet<>();

}
