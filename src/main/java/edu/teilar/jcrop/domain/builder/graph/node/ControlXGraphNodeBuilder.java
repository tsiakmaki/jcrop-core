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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.edge.XEdgeBuilder;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.ControlNode;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * @author m.tsiakmaki
 *
 */
public class ControlXGraphNodeBuilder implements XGraphNodeBuilder {

	private ControlNode xNode;
	
	private CROPOntologyController controller;
	
	private XEdgeBuilder xedgeBuilder; 
	
	public ControlXGraphNodeBuilder(CROPOntologyController controller) {
		this.controller = controller; 
		this.xedgeBuilder = new XEdgeBuilder(controller);
	}
	
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.XGraphNodeBuilder#buildXNode(java.lang.String)
	 */
	@Override
	public void buildXNode(String name) {
		Assert.assertNotNull("ControlXGraphNode should have name", name);
		this.xNode = new ControlNode(name);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.XGraphNodeBuilder#buildIsStartOf(edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildIsStartOf(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getControlNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = xgnIndi
				.getObjectPropertyValues(
						controller.getDataFactory().getIsStartOf(),
						controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual eIndi : edgesSet) {
			xedgeBuilder.buildEdge(eIndi, xgraph, kobj);
			edges.add(xedgeBuilder.getEdge());
		} 
		
		this.xNode.setIsStartOf(edges);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.XGraphNodeBuilder#buildIsEndOf(edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildIsEndOf(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getControlNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = xgnIndi
				.getObjectPropertyValues(
						controller.getDataFactory().getIsEndOf(),
						controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual eIndi : edgesSet) {
			xedgeBuilder.buildEdge(eIndi, xgraph, kobj);
			edges.add(xedgeBuilder.getEdge());
		} 
		
		this.xNode.setIsEndOf(edges);
	}

	
	public void buildThreshold(XGraph xgraph, KObject kobj) {
		OWLNamedIndividual controlNodeIndi = controller.getDataFactory()
				.getControlNodeIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLLiteral> literals = controlNodeIndi.getDataPropertyValues(
				controller.getDataFactory().getThreshold(), 
				controller.getOwlManager().getXGraphOntology());
		String v = literals.iterator().hasNext() ? 
				literals.iterator().next().getLiteral() : "0";
		this.xNode.setThreshold(Float.valueOf(v));
	} 
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder#buildAssociatedTargetConcept(edu.teilar.jcrop.domain.graph.XGraph, edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildAssociatedTargetConcept(XGraph xgraph, KObject kobj) {
		// do nothing, it is null
		
	}
	
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.XGraphNodeBuilder#getXNode()
	 */
	@Override
	public XNode getXNode() {
		return this.xNode;
	}

}
