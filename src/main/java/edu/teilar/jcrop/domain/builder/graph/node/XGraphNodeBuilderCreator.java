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

import org.junit.Assert;

import edu.teilar.jcrop.domain.builder.graph.group.ParGroupXGraphNodeBuilder;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil.XNodeType;

/**
 * @author m.tsiakmaki
 *
 */
public class XGraphNodeBuilderCreator {

		
	public XGraphNodeBuilder createBuilder(CROPOntologyController controller, 
			XNodeType type) {
		
		if(type == XNodeType.LearningAct) {
			return new LearningActXGraphNodeBuilder(controller);
		} else if(type == XNodeType.Dialogue) {
			return new DialogueXGraphNodeBuilder(controller);
		} else if(type == XNodeType.Control) {
			return new ControlXGraphNodeBuilder(controller);
		} else if(type == XNodeType.ParGroup) {
			return new ParGroupXGraphNodeBuilder(controller);
		}
		
		Assert.assertFalse("Builder not found for type " + type, true);
		
		return null;
	}
}
