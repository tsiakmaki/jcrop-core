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

package edu.teilar.jcrop.domain.graph.node;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.resource.AssociatableResource;

public abstract class AbstractNode implements Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6259640760202747284L;
	
	protected String name; 
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	private AssociatableResource associated;
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.graph.node.Node#isAssociatedOf()
	 */
	public AssociatableResource getAssociated() {
		return this.associated;
	}
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.graph.node.Node#setIsAssociatedOf(edu.teilar.jcrop.domain.resource.AssociatableResource)
	 */
	public void setAssociated(AssociatableResource isAssociatedOf) {
		this.associated = isAssociatedOf;
	}
	
	
	private Set<Edge> isEndOf;
	
	public Set<Edge> getIsEndOf() {
		return isEndOf;
	}

	public void setIsEndOf(Set<Edge> isEndOf) {
		this.isEndOf = isEndOf;
	}
	
	private Set<Edge> isStartOf;
	
	public Set<Edge> getIsStartOf() {
		return isStartOf;
	}

	public void setIsStartOf(Set<Edge> isStartOf) {
		this.isStartOf = isStartOf;
	}
	
	
	
	public Set<Node> calculateToNodes() {
		/*Set<Node> result = new HashSet<Node>(); 
		for(Edge e : isStartOf) {
			//result.add(e.getToNode());
		}
		return result;*/
		return null;
	}
	
	public Node calculateDefaultToNode() {
		/*for(Edge e : isStartOf) {
			if(e instanceof DefaultEdge || e instanceof IfTrueEdge) 
				return e.getToNode();
		}*/
		return null;
	}
	
	public AbstractNode() {
	}
	
	public AbstractNode(String name) {
		this.name = name; 
	}
}
