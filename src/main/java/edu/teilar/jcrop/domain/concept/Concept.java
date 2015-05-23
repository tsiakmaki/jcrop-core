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

package edu.teilar.jcrop.domain.concept;

import java.util.Set;

import edu.teilar.jcrop.domain.ontology.ContentOntology;
/**
 * 
 * A knowledge Concept is defined (disambiquated) in a Domain Ontology. 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class Concept implements EducationalObjective {

	/**
	 * 
	 */
	private static final long serialVersionUID = -654654269586256975L;

	private String name; 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	private Set<Concept> prerequisites; 
	
	public Set<Concept> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Set<Concept> prerequisites) {
		this.prerequisites = prerequisites;
	}

	
	private ContentOntology definedBy;
	
	public ContentOntology getDefinedBy() {
		return definedBy;
	}

	public void setDefinedBy(ContentOntology definedBy) {
		this.definedBy = definedBy;
	} 
	
	public Concept() {
		
	}
	public Concept(String name) {
		this.name = name;
	}
	
}
