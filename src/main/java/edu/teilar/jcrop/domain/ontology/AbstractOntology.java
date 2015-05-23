/*
 * (C) Copyright 2010-2013 Maria Tsiakmaki.
 * 
 * This file is part of jcropeditor.
 *
 * jcropeditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) 
 * as published by the Free Software Foundation, version 3.
 * 
 * jcropeditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with jcropeditor.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.teilar.jcrop.domain.ontology;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.concept.Concept;

public abstract class AbstractOntology implements EducationalObjectivesOntology {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4258779783469752727L;
	
	
	protected String name;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	protected Set<Concept> defines;
	
	public Set<Concept> getDefines() {
		return defines;
	}

	public void setDefines(Set<Concept> defines) {
		this.defines = defines;
	}


	public void addDefines(Concept d) {
		if(this.defines != null) {
			this.defines.add(d);
		}
	}
	
	public Concept getConceptByName(String conceptName) {
		for (Concept concept : defines) {
			if(concept.getName().equals(conceptName)) {
				return concept; 
			}
		}
		return null;
	}
	

}
