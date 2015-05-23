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
package edu.teilar.jcrop.domain.execution;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.teilar.jcrop.domain.graph.XGraph;

/**
 * @author m.tsiakmaki
 *
 */
public class DefaultExecutionModel implements ExecutionModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7357201065761297738L;
	
	
	private String name; 
	
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

	private ExecutionManager executionManager; 
	
	/**
	 * @return the executionManager
	 */
	public ExecutionManager getExecutionManager() {
		return executionManager;
	}

	/**
	 * @param executionManager the executionManager to set
	 */
	public void setExecutionManager(ExecutionManager executionManager) {
		this.executionManager = executionManager;
	}

	
	private XGraph xGraph;

	/**
	 * @return the xGraph
	 */
	public XGraph getxGraph() {
		return xGraph;
	}

	/**
	 * @param xGraph the xGraph to set
	 */
	public void setxGraph(XGraph xGraph) {
		this.xGraph = xGraph;
	}
	
	public DefaultExecutionModel() {
		this.name = "Default";
	}
	
}
