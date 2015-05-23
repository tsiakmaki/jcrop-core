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

import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;

/**
 * @author m.tsiakmaki
 *
 */
public interface NodeBuilder {

	public void buildNode(String name);
	
	public void buildAssociated(KObject kobj);
	
	public void buildIsStartOf(KObject kobj);
	
	public void buildIsEndOf(KObject kobj); 
	
	public Node getNode(); 
}
