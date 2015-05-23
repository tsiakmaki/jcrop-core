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

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.node.Node;

/**
 * The Graph concept classifies directed graphs. 
 * The intent is to classify "structured graphs", 
 * i.e. graphs with associated entities of some 
 * kind or other to their nodes.
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public interface Graph extends Serializable {
	
	public String getName();

	public void setName(String name);
	
	public Set<Edge> getEdges();
	
	public void setEdges(Set<Edge> edges);
	
	public Edge getEdgeByName(String name); 
	
	public Set<Node> getNodes();

	public void setNodes(Set<Node> nodes);
	
	public Set<Node> calculateTopNodes();
	
	public Set<Node> calculateLeafNodes(); 
	
	//public AssociatableResource isAssosiatedOf();
}
