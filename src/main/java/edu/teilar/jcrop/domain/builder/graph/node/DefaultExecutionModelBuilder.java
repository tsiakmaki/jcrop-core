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

import edu.teilar.jcrop.domain.builder.graph.XGraphBuilder;
import edu.teilar.jcrop.domain.execution.DefaultExecutionManager;
import edu.teilar.jcrop.domain.execution.DefaultExecutionModel;
import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * @author m.tsiakmaki
 *
 */
public class DefaultExecutionModelBuilder {
	
	private ExecutionModel model; 
	
	private CROPOntologyController controller;
	
	public DefaultExecutionModelBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	public void buildExecutionModel() {
		this.model = new DefaultExecutionModel();
	}
	
	public void buildExecutionManager() {
		this.model.setExecutionManager(new DefaultExecutionManager());
	}
	
	public void buildExecutionGraph(KObject kobj) {
		XGraphBuilder builder = new XGraphBuilder(controller);
		builder.buildExecutionGraph();
		builder.buildEdges(builder.getXGraph(), kobj);
		builder.buildNodes(builder.getXGraph(), kobj);
		
		this.model.setxGraph(builder.getXGraph());
	}
	
	public ExecutionModel getExecutionModel() {
		return this.model;
	}
	
}
