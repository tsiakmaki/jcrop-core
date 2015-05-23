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

package edu.teilar.jcrop.domain.builder.graph;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.node.ConceptGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.NodeBuilder;
import edu.teilar.jcrop.domain.graph.ConceptGraph;
import edu.teilar.jcrop.domain.graph.Graph;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

public class ConceptGraphBuilder implements GraphBuilder {

	private ConceptGraph graph; 
	
	private CROPOntologyController controller;
	
	public ConceptGraphBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	@Override
	public void buildGraph(KObject kobj) {
		this.graph = new ConceptGraph(kobj.getName());
		kobj.setAssociatedConceptGraph(this.graph);
	}
	
	@Override
	public void buildEdges(KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual cgIndi = controller.getDataFactory()
				.getConceptGraphIndi(this.graph.getName());
		Set<OWLIndividual> nodesSet = cgIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(), 
				controller.getOwlManager().getConceptGraphOntology());
		for (OWLIndividual n : nodesSet) {
			Set<OWLIndividual> isStartOfSet = n.getObjectPropertyValues(
					controller.getDataFactory().getIsStartOf(), 
					controller.getOwlManager().getConceptGraphOntology());
			for (OWLIndividual eIndi : isStartOfSet) {
				Edge e = new DefaultEdge(OntoUtil.stipPostfix(
						Postfix.ConceptGraphEdge,
						eIndi.asOWLNamedIndividual(), kobj));
				edges.add(e);
			}
		}
		
		this.graph.setEdges(edges);
	}

	@Override
	public void buildNodes(KObject kobj) {
		// calculate nodes 
		OWLNamedIndividual cgIndi = controller.getDataFactory()
				.getConceptGraphIndi(this.graph.getName());
		NodeBuilder nodeBuilder = new ConceptGraphNodeBuilder(controller);
		Set<Node> nodes = new HashSet<Node>();
		Set<OWLIndividual> nodesSet = cgIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getConceptGraphOntology());
		for (OWLIndividual n : nodesSet) {
			nodeBuilder.buildNode(OntoUtil.stipPostfix(
					Postfix.ConceptGraphNode, 
					n.asOWLNamedIndividual(), kobj));
			nodeBuilder.buildAssociated(kobj);
			nodeBuilder.buildIsStartOf(kobj);
			nodeBuilder.buildIsEndOf(kobj);
			nodes.add(nodeBuilder.getNode());
		}
		
		this.graph.setNodes(nodes);
	}

	@Override
	public Graph getGraph() {
		return this.graph;
	}
	
}
