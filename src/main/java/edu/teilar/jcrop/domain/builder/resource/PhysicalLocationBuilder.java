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

package edu.teilar.jcrop.domain.builder.resource;

import java.util.Set;

import org.jaxen.Navigator;
import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;
/**
 * 
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class PhysicalLocationBuilder {

	private PhysicalLocation pl;
	
	private CROPOntologyController controller;
	
	public PhysicalLocationBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
	public void buildPhysicalLocation(String name, KObject kobj) {
		this.pl = new PhysicalLocation(name); 
		OWLNamedIndividual phIndi = controller.getDataFactory()
				.getPhysicalLocationIndi(name, kobj.getName());
		
		Set<OWLLiteral> plSet = phIndi.getDataPropertyValues(
				controller.getDataFactory().getHasPhysicalLocation(),
				controller.getOwlOntology());
		
		Assert.assertTrue("PhysicalLocation is now one for:  " + name + " of kobject: " 
				+ kobj.getName(), plSet.size() == 1);
		this.pl.setPhysicalLocation(plSet.iterator().next().getLiteral());

		Set<OWLLiteral> pfSet = phIndi.getDataPropertyValues(
				controller.getDataFactory().getPhysicalFormat(),
				controller.getOwlOntology());
		
		Assert.assertTrue(pfSet.size() == 1);
		this.pl.setPhysicalFormat(pfSet.iterator().next().getLiteral());
	}
	
	public PhysicalLocation getPhysicalLocation() {
		return this.pl;
	}
}
