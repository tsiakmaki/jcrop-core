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

package edu.teilar.jcrop.domain.resource;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.concept.EducationalObjective;
import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.lom.Lom;
import edu.teilar.jcrop.domain.ontology.ContentOntology;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public interface LearningObject extends AssociatableResource {

	public String getName();

	public void setName(String name);
	
	public Lom getDescribedBy();

	public void setDescribedBy(Lom describedBy);
	
	public ContentOntology getAssociatedContentOntology();

	public void setAssociatedContentOntology(
			ContentOntology associatedContentOntology);
	
	public EducationalObjective getTargetEducationalObjective();

	public void setTargetEducationalObjective(
			EducationalObjective targetEducationalObjective);
	
	public Set<EducationalObjective> getPrerequisites(); 
	
	public void setPrerequisites(Set<EducationalObjective> prerequisites); 
	
	public void setExecutionModel(ExecutionModel model);
	
	public ExecutionModel getExecutionModel();
}
