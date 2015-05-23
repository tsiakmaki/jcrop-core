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

import java.util.HashSet;
import java.util.Set;

import edu.teilar.jcrop.domain.concept.Concept;

public class DomainOntology extends AbstractOntology {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2175612142082993538L;

	
	private Set<ContentOntology> subOntologies; 
	
	public Set<ContentOntology> getSubOntologies() {
		return subOntologies;
	}

	public void setSubOntologies(Set<ContentOntology> subOntologies) {
		this.subOntologies = subOntologies;
	}

	
	public DomainOntology() {
		
	}
	
	public DomainOntology(String name) {
		this.name = name;
		this.defines = new HashSet<Concept>();
		subOntologies = new HashSet<>();
	}
	
}
