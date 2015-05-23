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

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.ontology.ContentOntology;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
/**
 * 
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public interface EducationalObjective extends Serializable, AssociatableResource {

	public Set<Concept> getPrerequisites();

	public void setPrerequisites(Set<Concept> prerequisites);
	
	public ContentOntology getDefinedBy();

	public void setDefinedBy(ContentOntology definedBy);
	
	public String getName(); 

	public void setName(String name); 

}
