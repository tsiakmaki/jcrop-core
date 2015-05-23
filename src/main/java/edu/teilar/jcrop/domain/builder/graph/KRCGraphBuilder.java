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

import edu.teilar.jcrop.domain.builder.graph.node.KRCGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.NodeBuilder;
import edu.teilar.jcrop.domain.graph.Graph;
import edu.teilar.jcrop.domain.graph.KRCGraph;
import edu.teilar.jcrop.domain.graph.edge.AbstractEdge;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

public class KRCGraphBuilder implements GraphBuilder {

	private KRCGraph graph; 
	
	private CROPOntologyController controller;
	
	public KRCGraphBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	@Override
	public void buildGraph(KObject kobj) {
		this.graph = new KRCGraph(kobj.getName());
		kobj.setAssociatedKRCGraph(this.graph);
	}

	@Override
	public void buildEdges(KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual krcIndi = controller.getDataFactory()
				.getKRCGraphIndi(this.graph.getName());
		
		Set<OWLIndividual> nodesSet = krcIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getKRCOntology());
		for (OWLIndividual n : nodesSet) {
			//is start of edges  (it would be the same if we took is end of
			Set<OWLIndividual> isStartOfSet = n.getObjectPropertyValues(
					controller.getDataFactory().getIsStartOf(),
					controller.getOwlManager().getKRCOntology());
			for (OWLIndividual eIndi : isStartOfSet) {
				Edge e = new DefaultEdge(OntoUtil.stipPostfix(
							Postfix.KRCEdge,
							eIndi.asOWLNamedIndividual(), kobj));
				edges.add(e);
			}
		}
		
		this.graph.setEdges(edges);
		
	}

	@Override
	public void buildNodes(KObject kobj) {
		// calculate nodes 
		OWLNamedIndividual krcIndi = controller.getDataFactory()
				.getKRCGraphIndi(this.graph.getName());
		NodeBuilder nodeBuilder = new KRCGraphNodeBuilder(controller);
		Set<Node> nodes = new HashSet<Node>();
		Set<OWLIndividual> nodesSet = krcIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getKRCOntology());
		for (OWLIndividual n : nodesSet) {
			nodeBuilder.buildNode(OntoUtil.stipPostfix(
					Postfix.KRCNode,
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
