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
package edu.teilar.jcrop.domain.graph.edge;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author m.tsiakmaki
 *
 */
public class DefaultEdge extends AbstractEdge {

	/**
	 * @param name
	 */
	public DefaultEdge(String name) {
		super(name);
	}

	public DefaultEdge() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1526445102580205020L;

}
