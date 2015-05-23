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

package edu.teilar.jcrop.domain.graph.group;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.concept.EducationalObjective;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.AssociatableResource;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public class XGroup extends XGraph implements XNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
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

	
	
	/** krc node */
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
	public void setAssociated(AssociatableResource associated) {
		this.associated = associated;
	}

	private EducationalObjective associatedTargetEducationalObjective;
	
	public EducationalObjective getAssociatedTargetEducationalObjective() {
		return associatedTargetEducationalObjective;
	}

	public void setAssociatedTargetEducationalObjective(
			EducationalObjective associatedTargetEducationalObjective) {
		this.associatedTargetEducationalObjective = associatedTargetEducationalObjective;
	} 
	
	public XGroup() {
		
	}
	
	public XGroup(String name) {
		this.name = name; 
	}

}
