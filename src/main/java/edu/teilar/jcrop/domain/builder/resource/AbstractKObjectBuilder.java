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
package edu.teilar.jcrop.domain.builder.resource;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.ConceptGraphBuilder;
import edu.teilar.jcrop.domain.builder.graph.GraphBuilder;
import edu.teilar.jcrop.domain.builder.graph.KRCGraphBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.DefaultExecutionModelBuilder;
import edu.teilar.jcrop.domain.builder.ontology.ContentOntologyBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.concept.EducationalObjective;
import edu.teilar.jcrop.domain.director.ContentOntologyDirector;
import edu.teilar.jcrop.domain.graph.ConceptGraph;
import edu.teilar.jcrop.domain.graph.KRCGraph;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

/**
 * @author m.tsiakmaki
 *
 */
public class AbstractKObjectBuilder implements KObjectBuilder {

	protected CROPOntologyController controller; 
	
	protected KObject kobject;
	
	public AbstractKObjectBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildKObject(java.lang.String)
	 */
	@Override
	public void buildKObject(String name) {
		Assert.assertNotNull(name, "KObject should have name");
		this.kobject = controller.getLearningObjectByName(name);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildContentOntology()
	 */
	@Override
	public void buildContentOntology() {
		OWLNamedIndividual kResourceIndi = controller.getDataFactory()
				.getKObjectIndi(this.kobject.getName());
		Set<OWLIndividual> associated = kResourceIndi
				.getObjectPropertyValues(
						controller.getDataFactory().getHasAssociated(),
						controller.getOwlOntology());
		for (OWLIndividual i : associated) {
			Set<OWLClassExpression> types = i.getTypes(controller.getOwlManager().getConceptGraphOntology());
			
			if(types.contains(controller.getDataFactory().getContentOntology())) {
				
				// the associated Content Ontology indi
				int beginIndx = i.asOWLNamedIndividual().getIRI().toString().indexOf("#") + 1;
				String ontologyName = i.asOWLNamedIndividual().getIRI().toString().substring(beginIndx); 
				
				ContentOntologyDirector director = new ContentOntologyDirector(); 
				ContentOntologyBuilder builder = new ContentOntologyBuilder(controller);
				this.kobject.setAssociatedContentOntology(
						director.createContentOntology(builder, ontologyName));
				return;
			}
		}
		Assert.fail("Content Ontology not found for " + this.kobject.getName());
		
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildTarget()
	 */
	@Override
	public void buildTarget() {
		String kObjectName = kobject.getName();
		OWLNamedIndividual kobjIndi = controller.getDataFactory()
				.getKObjectIndi(kObjectName);
		Set<OWLIndividual> targetSet = kobjIndi.getObjectPropertyValues(
				controller.getDataFactory().getTargets(),
				controller.getOwlOntology());
		
		Assert.assertTrue("Each object targets to ONE concept",	(targetSet.size() == 1));
		
		OWLIndividual targets = targetSet.iterator().next();
		String target = OntoUtil.stipPostfix(Postfix.Concept, targets.asOWLNamedIndividual());
		// get Concept for content ontology
		Concept c = this.kobject.getAssociatedContentOntology().getConceptByName(target);
		this.kobject.setTargetEducationalObjective(c);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildPrerequisites()
	 */
	@Override
	public void buildPrerequisites() {
		String kObjectName = kobject.getName();
		OWLNamedIndividual kobjIndi = controller.getDataFactory()
				.getKObjectIndi(kObjectName);
		Set<OWLIndividual> prerequisitesSet = kobjIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasPrerequisite(),
				controller.getOwlOntology());
		Set<EducationalObjective> prerequisites = new HashSet<EducationalObjective>();
		for (OWLIndividual i : prerequisitesSet) {
			String prerequisite = OntoUtil.stipPostfix(Postfix.Concept, i.asOWLNamedIndividual());
			Concept p = this.kobject.getAssociatedContentOntology()
					.getConceptByName(prerequisite);
			prerequisites.add(p);
		}
		this.kobject.setPrerequisites(prerequisites);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildConceptGraph()
	 */
	@Override
	public void buildConceptGraph() {
		GraphBuilder gBuilder = new ConceptGraphBuilder(controller);
		gBuilder.buildGraph(this.kobject);
		gBuilder.buildEdges(this.kobject);
		gBuilder.buildNodes(this.kobject);
		
		//cg.setNodes(nodes);
		this.kobject.setAssociatedConceptGraph((ConceptGraph)gBuilder.getGraph());
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildKRC()
	 */
	@Override
	public void buildKRC() {
		KRCGraphBuilder builder = new KRCGraphBuilder(controller);
		builder.buildGraph(getKObject());
		builder.buildEdges(getKObject());
		builder.buildNodes(getKObject());
		
		this.kobject.setAssociatedKRCGraph((KRCGraph)builder.getGraph());
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildLOM()
	 */
	@Override
	public void buildLOM() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#buildExecutionModel()
	 */
	@Override
	public void buildExecutionModel() {
		DefaultExecutionModelBuilder builder = 
				new DefaultExecutionModelBuilder(this.controller);
		builder.buildExecutionModel(); 
		this.kobject.setExecutionModel(builder.getExecutionModel());	
		
		// 
		builder.buildExecutionManager();
		builder.buildExecutionGraph(this.kobject);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.resource.KObjectBuilder#getKObject()
	 */
	@Override
	public KObject getKObject() {
		return this.kobject;
	}

}
