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
package edu.teilar.jcrop.domain.builder.graph.group;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.edge.XEdgeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilderCreator;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.XGraphNodeDirector;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.ConceptGraphNode;
import edu.teilar.jcrop.domain.graph.node.KRCNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;
import edu.teilar.jcrop.utils.OntoUtil.XNodeType;

/**
 * @author m.tsiakmaki
 *
 */
public class ParGroupXGraphNodeBuilder implements XGraphNodeBuilder {

	private ParXGroup xNode;
	
	private CROPOntologyController controller;
	
	private XEdgeBuilder xedgeBuilder; 
	
	public ParGroupXGraphNodeBuilder(CROPOntologyController controller) {
		this.controller = controller;
		this.xedgeBuilder = new XEdgeBuilder(controller);
	}
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildNode(java.lang.String)
	 */
	@Override
	public void buildXNode(String name) {
		Assert.assertNotNull("XNode should have name", name);
		this.xNode = new ParXGroup(name); 
	}

	
	public void buildAssociated(XGraph xgraph, KObject kobj) {
		// the associated krcnode of the par group
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getParGroupIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		Set<OWLIndividual> associatedSet = xgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasAssociated(),
				controller.getOwlManager().getXGraphOntology());
		
		Assert.assertEquals(xgnIndi + " should have one associated krc node", 
				1, associatedSet.size());
		
		OWLIndividual krcNodeIndi = associatedSet.iterator().next();
		Set<OWLClassExpression> types = krcNodeIndi.getTypes(
				controller.getOwlManager().getKRCOntology());
		
		
		Assert.assertTrue(krcNodeIndi + " should have one type, i.e. the KRCNode type", 
				(types.size() == 1) && 
				types.contains(controller.getDataFactory().getKRCNode()));
		
		String krcNodeName = OntoUtil.stipPostfix(Postfix.KRCNode, 
				krcNodeIndi.asOWLNamedIndividual(), kobj);
		this.xNode.setAssociated(
				kobj.getAssociatedKRCGraph().getNodeByName(krcNodeName));
	}
	
	// the inside the node edges, if any
	public void buildEdges(XGraph xgraph, KObject kobj) {
		Set<Edge> result = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getParGroupIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		
		Set<OWLIndividual> edges = xgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getIsStartOf(),
				controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual eIndi : edges) {
			xedgeBuilder.buildEdge(eIndi, xgraph, kobj);
			result.add(xedgeBuilder.getEdge());
		}
		this.xNode.setEdges(result);
	}

	
	// the inside the graph nodes
	public void buildNodes(XGraph xgraph, KObject kobj) {
		// same as XGraph.buildNodes() 
		// calculate nodes 
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getParGroupIndi(this.xNode.getName(), 
						xgraph.getName(), kobj.getName());
		
		XGraphNodeBuilderCreator creator = new XGraphNodeBuilderCreator();
		XGraphNodeDirector director = new XGraphNodeDirector();
		
		Set<Node> nodes = new HashSet<Node>();
		Set<OWLIndividual> nodesSet = xgnIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual n : nodesSet) {
			// learning act | control | dialogue | xgroup (unimplemented)
			XNodeType type = getXNodeType(n); 
			String name = OntoUtil.stipPostfix(type, n.asOWLNamedIndividual(), 
					xgraph, kobj);
			XGraphNodeBuilder builder = creator.createBuilder(controller, type);
			XNode xnode = director.createXGraphNode(builder, name, xgraph, kobj);
			nodes.add(xnode);
		}
		
		this.xNode.setNodes(nodes);
	}

	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#buildIsStartOf(edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildIsStartOf(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgnIndi = controller.getDataFactory()
				.getParGroupIndi(this.xNode.getName(), 
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
				.getParGroupIndi(this.xNode.getName(), 
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

	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder#buildAssociatedTargetConcept(edu.teilar.jcrop.domain.graph.XGraph, edu.teilar.jcrop.domain.resource.KObject)
	 */
	@Override
	public void buildAssociatedTargetConcept(XGraph xgraph, KObject kobj) {
		KRCNode krcNode = (KRCNode)this.xNode.getAssociated();
		this.xNode.setAssociatedTargetEducationalObjective(
				(Concept)((ConceptGraphNode)krcNode.getAssociated()).getAssociated());
	}
	
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.NodeBuilder#getNode()
	 */
	@Override
	public XNode getXNode() {
		return this.xNode;
	}

	
	/**
	 * Get the type of an xnode individual 
	 * @see ExecutionGraph
	 * @param i xnode individual
	 * @return
	 */
	private XNodeType getXNodeType(OWLIndividual i) {
		Set<OWLClassExpression> types = i.getTypes(
				controller.getOwlManager().getXGraphOntology());
		if(types.contains(controller.getDataFactory().getDialogueNode())) {
			return XNodeType.Dialogue;
		} else if(types.contains(controller.getDataFactory().getControlNode())) {
			return XNodeType.Control;
		} else if(types.contains(controller.getDataFactory().getParGroup())) {
			return XNodeType.ParGroup;
		} else if(types.contains(controller.getDataFactory().getLearningActNode())) {
			return XNodeType.LearningAct;
		}
		
		Assert.assertTrue("XNode type not found for " + i, false);
		return null;
	}


	
}
