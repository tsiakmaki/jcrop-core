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

import edu.teilar.jcrop.domain.concept.EducationalObjective;
import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.graph.ConceptGraph;
import edu.teilar.jcrop.domain.graph.KRCGraph;
import edu.teilar.jcrop.domain.lom.Lom;
import edu.teilar.jcrop.domain.ontology.ContentOntology;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public abstract class KObject implements LearningObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3330788456584125778L;

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

	private Lom describedBy;
	
	public Lom getDescribedBy() {
		return describedBy;
	}

	public void setDescribedBy(Lom describedBy) {
		this.describedBy = describedBy;
	}
	
	private ContentOntology associatedContentOntology;

	public ContentOntology getAssociatedContentOntology() {
		return associatedContentOntology;
	}

	public void setAssociatedContentOntology(
			ContentOntology associatedContentOntology) {
		this.associatedContentOntology = associatedContentOntology;
	}
	
	private EducationalObjective targetEducationalObjective;
	
	public EducationalObjective getTargetEducationalObjective() {
		return targetEducationalObjective;
	}

	public void setTargetEducationalObjective(EducationalObjective targetEducationalObjective) {
		this.targetEducationalObjective = targetEducationalObjective;
	} 
	
	private ConceptGraph associatedConceptGraph; 
	
	public ConceptGraph getAssociatedConceptGraph() {
		return associatedConceptGraph;
	}

	public void setAssociatedConceptGraph(ConceptGraph associatedConceptGraph) {
		this.associatedConceptGraph = associatedConceptGraph;
	}

	private KRCGraph associatedKRCGraph;
	
	public KRCGraph getAssociatedKRCGraph() {
		return associatedKRCGraph;
	}

	public void setAssociatedKRCGraph(KRCGraph associatedKRCGraph) {
		this.associatedKRCGraph = associatedKRCGraph;
	}

	private ExecutionModel executionModel;
	
	public ExecutionModel getExecutionModel() {
		return executionModel;
	}

	public void setExecutionModel(ExecutionModel executionModel) {
		this.executionModel = executionModel;
	}

	private Set<EducationalObjective> prerequisites; 

	public Set<EducationalObjective> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Set<EducationalObjective> prerequisites) {
		this.prerequisites = prerequisites;
	}

	
	public KObject() {
		
	}
	
	public KObject(String name) {
		this.name = name;
	}
}
