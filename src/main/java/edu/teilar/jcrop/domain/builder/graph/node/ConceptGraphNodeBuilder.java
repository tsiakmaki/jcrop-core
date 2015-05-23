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
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.ConceptGraphNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

/**
 * @author m.tsiakmaki
 *
 */
public class ConceptGraphNodeBuilder implements NodeBuilder {

	private ConceptGraphNode node; 
	
	private CROPOntologyController controller; 
	
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildNode(java.lang.String)
	 */
	@Override
	public void buildNode(String name) {
		Assert.assertNotNull("ConceptGraphNode should have name", name);
		this.node = new ConceptGraphNode(name);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildAssociated()
	 */
	@Override
	public void buildAssociated(KObject kobj) {
		// attach the associated concept
		OWLNamedIndividual cgnIndi = controller.getDataFactory()
				.getConceptGraphNodeIndi(this.node.getName(), kobj.getName());
		Set<OWLIndividual> associated = cgnIndi.getObjectPropertyValues( 
				controller.getDataFactory().getHasAssociated(), 
				controller.getOwlManager().getConceptGraphOntology());
		Assert.assertTrue("No associated concept found for " + cgnIndi +  
				" in  ConceptGraphOntology", (associated.size()==1));
		String c = OntoUtil.stipPostfix(Postfix.Concept, 
				associated.iterator().next().asOWLNamedIndividual());
		this.node.setAssociated(
				kobj.getAssociatedContentOntology().getConceptByName(c));
	}

	
	
	

	/* 
	 * e.g. ComplexNumberDefinition_complex1_ConceptGraphNode
	 * (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildIsStartOf()
	 */
	@Override
	public void buildIsStartOf(KObject kobj) {
		
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual cgnIndi = controller.getDataFactory()
				.getConceptGraphNodeIndi(this.node.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = cgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsStartOf(),
				controller.getOwlManager().getConceptGraphOntology());
		for (OWLIndividual e : edgesSet) {
			String edgeName = OntoUtil.stipPostfix(Postfix.ConceptGraphEdge, 
					e.asOWLNamedIndividual(), kobj);
			Edge edge = kobj.getAssociatedConceptGraph().getEdgeByName(edgeName); 
			edges.add(edge);
		}
		this.node.setIsStartOf(edges);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildIsEndOf()
	 */
	@Override
	public void buildIsEndOf(KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual cgnIndi = controller.getDataFactory()
				.getKRCNodeIndi(this.node.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = cgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsEndOf(),
				controller.getOwlManager().getConceptGraphOntology());
		for (OWLIndividual e : edgesSet) {
			String edgeName = OntoUtil.stipPostfix(Postfix.ConceptGraphEdge, 
					e.asOWLNamedIndividual(), kobj);
			Edge edge = kobj.getAssociatedConceptGraph().getEdgeByName(edgeName); 
			edges.add(edge);
		}
		this.node.setIsEndOf(edges);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#getNode()
	 */
	@Override
	public Node getNode() {
		return this.node;
	}

	/**
	 * @param controller
	 */
	public ConceptGraphNodeBuilder(CROPOntologyController controller) {
		super();
		this.controller = controller;
	}
}
