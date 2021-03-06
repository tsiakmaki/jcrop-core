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

import edu.teilar.jcrop.domain.graph.Graph;
import edu.teilar.jcrop.domain.resource.KObject;

public interface GraphBuilder {

	/** each kobj has one concept graph, krc graph */
	public void buildGraph(KObject kobj); 
	
	public void buildEdges(KObject kobj);
	
	public void buildNodes(KObject kobj); 
	
	public Graph getGraph();
}
