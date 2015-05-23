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
package edu.teilar.jcrop.domain.builder.graph.node;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.KObject;

/**
 * @author m.tsiakmaki
 *
 */
public interface XGraphNodeBuilder {

	public void buildXNode(String name);
	
	public void buildIsStartOf(XGraph xgraph, KObject kobj);
	
	public void buildIsEndOf(XGraph xgraph, KObject kobj); 
	
	public void buildAssociatedTargetConcept(XGraph xgraph, KObject kobj); 
	
	public XNode getXNode();
	
}
