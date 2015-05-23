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

import java.util.Set;

import edu.teilar.jcrop.domain.resource.AssociatableResource;


/**
 * 
 * A KRCNode 
 * hasAssociated one PhysicalLocation or hasAssociated some LearningObject 
 * hasAssociated 1 ConceptGraphNode: AssociatableResource associated
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class KRCNode extends AbstractNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495476248502385012L;
	
	
	private Set<AssociatableResource> associatedResources; 
	
	public Set<AssociatableResource> getAssociatedResources() {
		return associatedResources;
	}

	public void setAssociatedResources(Set<AssociatableResource> associatedResources) {
		this.associatedResources = associatedResources;
	}

	
	public KRCNode() {
		
	}
	
	public KRCNode(String name) {
		this.name = name;
	}

}
