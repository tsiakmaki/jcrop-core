/*
 * (C) Copyright 2010-2013 m.tsiakmaki.
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
package edu.teilar.jcrop.domain.director;

import java.util.ArrayList;
import java.util.List;

import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.resource.KObject;

/**
 * @author m.tsiakmaki
 *
 */
public class KObjectDirector {

	public KObjectDirector() {
		
	}
	
	public KObject createLearningObject(KObjectBuilder builder, String name) {
		
		// init a learning object
		builder.buildKObject(name);
		// bottom up build
		// build content ontology, 
		if(builder.getKObject().getAssociatedContentOntology() == null) {
			builder.buildContentOntology();
		}
		
		if(builder.getKObject().getTargetEducationalObjective() == null) {
			builder.buildTarget();
		}
		
		if(builder.getKObject().getPrerequisites() == null) {
			builder.buildPrerequisites();
		}
		
		// first a simple lom 
		if(builder.getKObject().getDescribedBy() == null) {
			builder.buildLOM();
		}
		
		// 
		if(builder.getKObject().getAssociatedConceptGraph() == null) {
			builder.buildConceptGraph();
		}
		
		if(builder.getKObject().getAssociatedKRCGraph() == null) {
			builder.buildKRC();
		}
		
		if(builder.getKObject().getExecutionModel() == null) {
			builder.buildExecutionModel();
		}
		
		return builder.getKObject();
	}
	
	/**
	 * 
	 * @param finder
	 * @param builder
	 * @param targetConcept
	 * @return
	 */
	public List<KObject> createLearningObjectsWithTarget(KObjectFinder finder,
			KObjectBuilderCreator builderCreator, String targetConcept) {
		
		List<KObject> result = new ArrayList<>();
		
		List<String> kobjects = finder.findKObjectsWithTarget(targetConcept); 
		for (String kobjName : kobjects) {
			KObjectBuilder builder = builderCreator.createBuilder(kobjName);
			
			builder.buildKObject(kobjName);
			
			if(builder.getKObject().getAssociatedContentOntology() == null) {
				builder.buildContentOntology();
			}
			
			if(builder.getKObject().getTargetEducationalObjective() == null) {
				builder.buildTarget();
			}
			
			if(builder.getKObject().getPrerequisites() == null) {
				builder.buildPrerequisites();
			}
			// no need to build further here
			
			result.add(builder.getKObject());
		}
		
		return result;
	}
	
	
	public KObject createLearningObjectTillPrerequisites(KObjectBuilder builder,
			String name) {
		
		// init a learning object
		builder.buildKObject(name);
		// bottom up build
		// build content ontology, 
		if(builder.getKObject().getAssociatedContentOntology() == null) {
			builder.buildContentOntology();
		}
		
		if(builder.getKObject().getTargetEducationalObjective() == null) {
			builder.buildTarget();
		}
		
		if(builder.getKObject().getPrerequisites() == null) {
			builder.buildPrerequisites();
		}
		// no need to build further 
		
		// first a simple lom 
		builder.buildLOM();

		
		return builder.getKObject();
	}
	
}
