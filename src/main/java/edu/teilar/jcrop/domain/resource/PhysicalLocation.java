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

package edu.teilar.jcrop.domain.resource;

public class PhysicalLocation implements AssociatableResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3635261368679172972L;

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

	
	
	/** C:\\dev\\LearningObjects\\cn\\resources\\Division.txt */
	private String physicalLocation; 
	
	public String getPhysicalLocation() {
		return physicalLocation;
	}

	public void setPhysicalLocation(String physicalLocation) {
		this.physicalLocation = physicalLocation;
	}

	private String physicalFormat; 

	public String getPhysicalFormat() {
		return physicalFormat;
	}

	public void setPhysicalFormat(String physicalFormat) {
		this.physicalFormat = physicalFormat;
	}


	public PhysicalLocation() {
		
	}
	
	public PhysicalLocation(String name) {
		this.name = name;
	}
}
