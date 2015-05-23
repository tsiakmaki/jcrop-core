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

/**
 * The XGraph is built on top of the KRC. It does not 
 * contain the KRC as a subgraph and there is no 1-1 
 * correspondance between the KRC Nodes and Edges and 
 * the XGraph nodes and edges.
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class XGraph extends AbstractGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8290855261629552208L;

	public XGraph() {
		
	}
	
	public XGraph(String name) {
		super(name);
	}
}
