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

package edu.teilar.jcrop.domain.execution;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.resource.AssociatableResource;

/**
 * Each XModel has an associated XGraph and an XManager (Execution Manager). 
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
public interface ExecutionModel extends Serializable, AssociatableResource {

	public String getName();
	
	public void setName(String name);
	
	public ExecutionManager getExecutionManager();
	
	public void setExecutionManager(ExecutionManager executionManager);
	
	public XGraph getxGraph();
	
	public void setxGraph(XGraph xGraph);
	
}
