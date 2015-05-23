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
package edu.teilar.jcrop.domain.builder.graph;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilderCreator;
import edu.teilar.jcrop.domain.director.XGraphNodeDirector;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.edge.IfFalseEdge;
import edu.teilar.jcrop.domain.graph.edge.IfTrueEdge;
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
public class XGraphBuilder {

	private XGraph graph;
	
	private CROPOntologyController controller;
	
	public XGraphBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	public void buildExecutionGraph() {
		this.graph = new XGraph("Default");
	}
	
	public void buildEdges(XGraph xgraph, KObject kobj) {
		Set<Edge> edges = new HashSet<Edge>();
		OWLNamedIndividual xgIndi = controller.getDataFactory()
				.getXGraphIndi(this.graph.getName(), kobj.getName());
		Set<OWLIndividual> nodesSet = xgIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual n : nodesSet) {
			//is start of edges  (it would be the same if we took is end of
			Set<OWLIndividual> isStartOf = n.getObjectPropertyValues(
					controller.getDataFactory().getIsStartOf(), 
					controller.getOwlManager().getXGraphOntology());
			for(OWLIndividual eIndi : isStartOf) {
				Set<OWLClassExpression> types = eIndi.getTypes(
						controller.getOwlManager().getOntologies());
				Edge e; 
				if(types.contains(controller.getDataFactory().getIfFalseEdge())) {
					e = new IfFalseEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
							eIndi.asOWLNamedIndividual(), xgraph, kobj));
				} else if(types.contains(controller.getDataFactory().getIfTrueEdge())) {
					e = new IfTrueEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
							eIndi.asOWLNamedIndividual(), xgraph, kobj));
				} else {
					e = new DefaultEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
							eIndi.asOWLNamedIndividual(), xgraph, kobj));
				}
				edges.add(e);
			}
		}
		this.graph.setEdges(edges);
	}
	
	
	public void buildNodes(XGraph xgraph, KObject kobj) {
		// calculate nodes 
		OWLNamedIndividual xgIndi = controller.getDataFactory()
				.getXGraphIndi(this.graph.getName(), kobj.getName());
		
		XGraphNodeBuilderCreator creator = new XGraphNodeBuilderCreator();
		XGraphNodeDirector director = new XGraphNodeDirector();
		
		Set<Node> nodes = new HashSet<Node>();
		Set<OWLIndividual> nodesSet = xgIndi.getObjectPropertyValues(
				controller.getDataFactory().getHasNode(),
				controller.getOwlManager().getXGraphOntology());
		for (OWLIndividual n : nodesSet) {
			XNodeType type = getXNodeType(n); 
			String name = null;
			switch (type) {
			case Dialogue:
				name = OntoUtil.stipPostfix(Postfix.DialogueNode, 
						n.asOWLNamedIndividual(), xgraph, kobj);
				break;

			case Control:
				name = OntoUtil.stipPostfix(Postfix.ControlNode, 
						n.asOWLNamedIndividual(), xgraph, kobj);
				break;
				
			case ParGroup:
				name = OntoUtil.stipPostfix(Postfix.ParGroup, 
						n.asOWLNamedIndividual(), xgraph, kobj);
				break;
				
			case LearningAct:
				name = OntoUtil.stipPostfix(Postfix.LearningActNode, 
						n.asOWLNamedIndividual(), xgraph, kobj);
				break;
				
			default:
				// never here
				break;
			}
			XGraphNodeBuilder builder = creator.createBuilder(controller, type);
			XNode xnode = director.createXGraphNode(builder, name, xgraph, kobj);
			nodes.add(xnode);
		}
		
		this.graph.setNodes(nodes);	
	}
	
	public XGraph getXGraph() {
		return this.graph;
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
