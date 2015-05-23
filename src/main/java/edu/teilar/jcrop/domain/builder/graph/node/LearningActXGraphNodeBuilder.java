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
package edu.teilar.jcrop.domain.builder.graph.node;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.edge.XEdgeBuilder;
import edu.teilar.jcrop.domain.builder.resource.PhysicalLocationBuilder;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

/**
 * @author m.tsiakmaki
 *
 */
public class LearningActXGraphNodeBuilder implements XGraphNodeBuilder {

	private LearningActNode xNode; 
	
	private CROPOntologyController controller;
	
	private XEdgeBuilder xedgeBuilder; 
	
	public LearningActXGraphNodeBuilder(CROPOntologyController controller) {
		this.controller = controller;
		this.xedgeBuilder = new XEdgeBuilder(controller);
	}
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildNode(java.lang.String)
	 */
	@Override
	public void buildXNode(String name) {
		Assert.assertNotNull("LearningAct should have name", name);
		this.xNode = new LearningActNode(name);
	}


	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildIsStartOf(edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildIsStartOf(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getLearningActNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = xgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsStartOf(),
				controller.getOwlManager().getXGraphOntology());
		
		for (OWLIndividual eIndi : edgesSet) {
			xedgeBuilder.buildEdge(eIndi, xgraph, kobj);
			edges.add(xedgeBuilder.getEdge());
		} 
		
		this.xNode.setIsStartOf(edges);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildIsEndOf(edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildIsEndOf(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getLearningActNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = xgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsEndOf(),
				controller.getOwlManager().getXGraphOntology());
		
		for (OWLIndividual eIndi : edgesSet) {
			xedgeBuilder.buildEdge(eIndi, xgraph, kobj);
			edges.add(xedgeBuilder.getEdge());
		} 
		
		this.xNode.setIsEndOf(edges);
	}

	
	public void buildAssociated(XGraph xgraph, KObject kobj) {
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getLearningActNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> associatedSet = xgnIndi
				.getObjectPropertyValues(controller.getDataFactory().getHasAssociated(),
						controller.getOwlOntology());
		
		Assert.assertTrue("A Learning act has one associated resource " 
				+ xgnIndi, associatedSet.size() == 1);
		
		OWLIndividual i = associatedSet.iterator().next();
		
		Set<OWLClassExpression> types = i.getTypes(controller.getOwlOntology());
		// 
		if(types.contains(controller.getDataFactory().getKProduct()) | 
				types.contains(controller.getDataFactory().getSupportResource()) | 
				types.contains(controller.getDataFactory().getAssessmentResource())) {
			String name = OntoUtil.stipPostfix(Postfix.KObject, 
					i.asOWLNamedIndividual());
			// the associated resource is a kobject
			this.xNode.setAssociated(controller.getLearningObjectByName(name));
		} else {
			// the associated resource is a physical location
			String name = OntoUtil.stipPostfix(Postfix.PhysicalLocation, 
					i.asOWLNamedIndividual(), kobj);
			PhysicalLocationBuilder b = new PhysicalLocationBuilder(controller);
			b.buildPhysicalLocation(name, kobj);
			this.xNode.setAssociated(b.getPhysicalLocation());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#getNode()
	 */
	@Override
	public XNode getXNode() {
		return this.xNode;
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder#buildAssociatedTargetConcept(edu.teilar.jcrop.domain.graph.XGraph, edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildAssociatedTargetConcept(XGraph xgraph, KObject kobj) {
		AssociatableResource r = this.xNode.getAssociated();
		if(r instanceof PhysicalLocation) {
			this.xNode.setAssociatedTargetEducationalObjective(
					((KObject)kobj).getTargetEducationalObjective());
		} else if(r instanceof KObject) {
			this.xNode.setAssociatedTargetEducationalObjective(
					((KObject)r).getTargetEducationalObjective());
		}
	}

}
