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
 * Points to Default Edge of a dialogue Node 
 * 
 * @author m.tsiakmaki
 *
 */
public abstract class AbstractEdge implements Edge {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7819159427302054403L;
	
	
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

	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Edge) {
			return this.name.equals(((Edge)obj).getName());
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return this.name + " type: " + getClass();
	}
	
	
	public AbstractEdge(String name) {
		this.name = name;
	}

	public AbstractEdge() {
	}
}
