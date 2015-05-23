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
package edu.teilar.jcrop.domain.director;

import edu.teilar.jcrop.domain.builder.graph.group.ParGroupXGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.ControlXGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.DialogueXGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.LearningActXGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.XGraphNodeBuilder;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.domain.resource.KObject;

/**
 * @author m.tsiakmaki
 *
 */
public class XGraphNodeDirector {

	public XGraphNodeDirector() {
		
	}
	
	public XNode createXGraphNode(XGraphNodeBuilder builder, String name, 
			XGraph xgraph, KObject kobj) {
		builder.buildXNode(name);
		builder.buildIsEndOf(xgraph, kobj);
		builder.buildIsStartOf(xgraph, kobj);
		
		if(builder instanceof ControlXGraphNodeBuilder) {
			((ControlXGraphNodeBuilder)builder).buildThreshold(xgraph, kobj);
		} else if(builder instanceof DialogueXGraphNodeBuilder) {
			((DialogueXGraphNodeBuilder)builder).buildExplanationParagraph(xgraph, kobj);
		} else if(builder instanceof LearningActXGraphNodeBuilder) {
			((LearningActXGraphNodeBuilder)builder).buildAssociated(xgraph, kobj);
		} else if(builder instanceof ParGroupXGraphNodeBuilder) {
			((ParGroupXGraphNodeBuilder)builder).buildAssociated(xgraph, kobj);
			((ParGroupXGraphNodeBuilder)builder).buildEdges(xgraph, kobj);
			((ParGroupXGraphNodeBuilder)builder).buildNodes(xgraph, kobj);
		}
		
		// build associated target 
		builder.buildAssociatedTargetConcept(xgraph, kobj);
		
		return builder.getXNode();
	}
}
