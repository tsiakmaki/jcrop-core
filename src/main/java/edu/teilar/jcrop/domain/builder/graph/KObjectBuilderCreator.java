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

package edu.teilar.jcrop.domain.builder.graph;

import org.junit.Assert;

import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.builder.resource.KProductBuilder;
import edu.teilar.jcrop.domain.builder.resource.KResourceAssessmentBuilder;
import edu.teilar.jcrop.domain.builder.resource.KResourceSupportBuilder;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.owl.CROPOntologyController;

public class KObjectBuilderCreator {

	private CROPOntologyController controller;
	
	public KObjectBuilderCreator(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	public KObjectBuilder createBuilder(KObject kobj) {
		
		if(kobj instanceof KProduct) {
			return new KProductBuilder(controller);
		} else if(kobj instanceof KResourceSupport) {
			return new KResourceSupportBuilder(controller);
		} else if(kobj instanceof KResourceAssessment) {
			return new KResourceAssessmentBuilder(controller);
		} 
		
		Assert.assertFalse("Builder not found for kobj  " + kobj.getName() 
				+ " " + kobj.getClass(), true);
		
		return null;
	}
	
	
	public KObjectBuilder createBuilder(String kobjName) {
		
		KObject kobj = controller.getLearningObjectByName(kobjName);
		return createBuilder(kobj);
	}
}
