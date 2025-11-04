package com.primertrimestre.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teachers")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private Set<Module> modules = new HashSet<>();

    @Override
    public String toString() {
        return fullName != null ? fullName : username;
    }
}
