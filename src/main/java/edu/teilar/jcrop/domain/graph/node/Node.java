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

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
/**
 * A structured graph node is a usual graph node, 
 * except that it has  entities of some kind or 
 * other associated to it.
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public interface Node extends Serializable, AssociatableResource {
	
	/** get the id of the node */
	public String getName();
	
	/** sets the id of the node */
	public void setName(String name);

	/** the graph that this node belongs to, inverse of Graph -> hasNode */
	//public Graph isNodeOf();
	
	//public void setIsNodeOf(Graph g);
	
	/** the relation of this not to another node of another graph, or physical location,
	 * i.e. 
	 * ConceptGraphNode -> Concept (1)
	 * KRCNode -> ConceptGraphNode, PhysicalLocation | LearnginObject (2)
	 * XNode  
	 * 	Control -> null
	 *  Dialogue -> null 
	 *  LearnigAct ->  PhysicalLocation | LearnginObject (1)
	 *  GroupNode -> KRCNode 
	 */
	public AssociatableResource getAssociated(); 
	
	public void setAssociated(AssociatableResource associatableResource); 
	
	/** the edges that points to this node */
	public Set<Edge> getIsEndOf(); 
	
	/** the edges that start from this node */
	public Set<Edge> getIsStartOf();

	/** the to nodes from this node  */
	public Set<Node> calculateToNodes(); 
	
	/** the default to node, if any */
	public Node calculateDefaultToNode(); 
} 
