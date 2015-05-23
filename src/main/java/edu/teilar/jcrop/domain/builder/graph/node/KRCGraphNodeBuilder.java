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

package edu.teilar.jcrop.domain.builder.graph.node;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.resource.PhysicalLocationBuilder;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.ConceptGraphNode;
import edu.teilar.jcrop.domain.graph.node.KRCNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

public class KRCGraphNodeBuilder implements NodeBuilder {

	private KRCNode node; 
	
	private CROPOntologyController controller;
	
	public KRCGraphNodeBuilder(CROPOntologyController controller) {
		this.controller = controller; 
	}
	
	@Override
	public void buildNode(String name) {
		Assert.assertNotNull("KRCGraphNode should have name", name);
		this.node = new KRCNode(name);
	}

	@Override
	public void buildAssociated(KObject kobj) {
		// attach the associated
		
		// a krc node is associated to ONE concept graph node, 
		// and one kobjects, or one physical location
		// if kresource to one physical location
		OWLNamedIndividual krcnIndi = controller.getDataFactory()
				.getKRCNodeIndi(this.node.getName(), kobj.getName());
		
		Set<OWLIndividual> associatedKObjectsOrPhLoc = 		
				krcnIndi.getObjectPropertyValues(
					controller.getDataFactory().getHasAssociated(),
					controller.getOwlOntology());
		
		Assert.assertTrue(krcnIndi + " should have 1 physical location or >=1 associated KObjects.", 
				associatedKObjectsOrPhLoc.size() >= 1);
		Set<AssociatableResource> associated = new HashSet<AssociatableResource>(); 
		for (OWLIndividual a : associatedKObjectsOrPhLoc) {
			Set<OWLClassExpression> types = a.getTypes(controller.getOwlOntology());
			if(types.contains(controller.getDataFactory().getPhysicalLocation())) {
				PhysicalLocationBuilder builder = new PhysicalLocationBuilder(controller);
				builder.buildPhysicalLocation(OntoUtil.stipPostfix(
						Postfix.PhysicalLocation, a.asOWLNamedIndividual(), kobj), kobj);
				associated.add(builder.getPhysicalLocation());
			} else if(types.contains(controller.getDataFactory().getKProduct())
					|| types.contains(controller.getDataFactory().getSupportResource())
					|| types.contains(controller.getDataFactory().getAssessmentResource())) {
				//
				associated.add(controller.getLearningObjectByName(
						OntoUtil.stipPostfix(Postfix.KObject,
								a.asOWLNamedIndividual())));
			} else {
				Assert.assertTrue(types + " Should never be here", false);
			}
		}
		this.node.setAssociatedResources(associated);
		
		//the associated ConceptGraphNode 
		Set<OWLIndividual> associatedCGNode = krcnIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasAssociated(),
				controller.getOwlManager().getKRCOntology());
		
		Assert.assertEquals(krcnIndi + " krc node should have 1 assosiated concept graph node", 
				1, associatedCGNode.size());
		
		OWLIndividual cgNode = associatedCGNode.iterator().next();
		Node nd = kobj.getAssociatedConceptGraph().getNodeByName(
				OntoUtil.stipPostfix(Postfix.ConceptGraphNode,
						cgNode.asOWLNamedIndividual(), kobj));
		Assert.assertNotNull("The associated ConceptGraphNode not found for the " + cgNode, nd);
		this.node.setAssociated((ConceptGraphNode)
				kobj.getAssociatedConceptGraph().getNodeByName(nd.getName()));
		
		
		Assert.assertTrue(krcnIndi + " should be associated with "
				+ "one Physical Location or one or more KObject " , 
				this.node.getAssociatedResources().size() >= 1);
	}

	@Override
	public void buildIsStartOf(KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual krcnIndi = controller.getDataFactory()
				.getKRCNodeIndi(this.node.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = krcnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsStartOf(),
				controller.getOwlManager().getKRCOntology());
		for (OWLIndividual e : edgesSet) {
			String edgeName = OntoUtil.stipPostfix(Postfix.KRCEdge, 
					e.asOWLNamedIndividual(), kobj);
			Edge edge = kobj.getAssociatedConceptGraph().getEdgeByName(edgeName); 
			edges.add(edge);
		}
		this.node.setIsStartOf(edges);
	}

	@Override
	public void buildIsEndOf(KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual krcnIndi = controller.getDataFactory()
				.getKRCNodeIndi(this.node.getName(), kobj.getName());
		Set<OWLIndividual> edgesSet = krcnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsEndOf(),
				controller.getOwlManager().getKRCOntology());
		for (OWLIndividual e : edgesSet) {
			String edgeName = OntoUtil.stipPostfix(Postfix.KRCEdge, 
					e.asOWLNamedIndividual(), kobj);
			Edge edge = kobj.getAssociatedConceptGraph().getEdgeByName(edgeName); 
			edges.add(edge);
		}
		this.node.setIsEndOf(edges);
	}

	@Override
	public Node getNode() {
		return this.node;
	}

}
