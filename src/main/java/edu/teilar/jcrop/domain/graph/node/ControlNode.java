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

package edu.teilar.jcrop.domain.graph.node;

import edu.teilar.jcrop.domain.resource.AssociatableResource;


public class ControlNode extends AbstractXNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 73103754843714380L;

	private Float threshold;

	public Float getThreshold() {
		return threshold;
	}

	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}
	
	public ControlNode() {
		super();
	}
	
	public ControlNode(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.graph.node.AbstractXNode#setAssociated(edu.teilar.jcrop.domain.resource.AssociatableResource)
	 */
	@Override
	public void setAssociated(AssociatableResource associatable) {
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.graph.node.AbstractXNode#getAssociated()
	 */
	@Override
	public AssociatableResource getAssociated() {
		return null;
	}

}
