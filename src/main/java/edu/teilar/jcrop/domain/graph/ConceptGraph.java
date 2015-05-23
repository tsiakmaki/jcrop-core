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
 * A Concept Graph (of some Learning Object or Learning
 * Domain) is a structured graph where the associated 
 * entities to each node of the graph are KConcept instances.
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class ConceptGraph extends AbstractGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7686989461353802716L;

	public ConceptGraph() {
		super();
	}
	
	public ConceptGraph(String name) {
		super(name);
	}
}
