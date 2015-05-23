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

package edu.teilar.jcrop.domain.graph;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.edge.AbstractEdge;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.Node;

/**
 * 
 * Abstract implementation of Graph
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public abstract class AbstractGraph implements Graph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5732511738175897239L;

	protected String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	private Set<Node> nodes;

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	private Set<Edge> edges;

	public Set<Edge> getEdges() {
		return edges;
	}

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	@Override
	public Edge getEdgeByName(String name) {

		for (Iterator<Edge> it = this.edges.iterator(); it.hasNext();) {
			Edge e = it.next();
			if (e.equals(new DefaultEdge(name)))
				return e;
		}

		return null;
	}

	/*
	 * comment for now, this is reasoner's stuff private KObject kobject;
	 * 
	 * public KObject getKobject() { return kobject; }
	 * 
	 * public void setKobject(KObject kobject) { this.kobject = kobject; }
	 * 
	 * private AssociatableResource associatableResource;
	 * 
	 * public AssociatableResource isAssosiatedOf() { return
	 * associatableResource; }
	 * 
	 * public void setIsAssosiatedOf(AssociatableResource associatableResource)
	 * { this.associatableResource = associatableResource; }
	 */

	public Set<Node> calculateTopNodes() {
		// TODO
		return null;
	}

	public Set<Node> calculateLeafNodes() {
		// TODO
		return null;
	}

	public Node getNodeByName(String name) {
		for (Node n : nodes) {
			if(n.getName().equals(name)) {
				return n;
			}
		}
		return null;
	}
	
	public AbstractGraph() {

	}

	public AbstractGraph(String name) {
		this.name = name;
	}
}
