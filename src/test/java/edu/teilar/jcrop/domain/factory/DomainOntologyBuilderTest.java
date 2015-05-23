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

package edu.teilar.jcrop.domain.factory;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import edu.teilar.jcrop.domain.builder.ontology.ContentOntologyBuilder;
import edu.teilar.jcrop.domain.builder.ontology.DomainOntologyBuilder;
import edu.teilar.jcrop.domain.director.ContentOntologyDirector;
import edu.teilar.jcrop.domain.director.DomainOntologyDirector;
import edu.teilar.jcrop.domain.ontology.ContentOntology;
import edu.teilar.jcrop.domain.ontology.DomainOntology;
import edu.teilar.jcrop.owl.CROPOntologyController;

public class DomainOntologyBuilderTest {

	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");

	private String ontologyName = 
			"file:/C:/git/java/jcrop-core/src/test/resources/crop-projects/complex/content_ontologies/complex_number_system.owl";
	
	private int expectedConcepts = 6; 
	@Test
	public void testDomainOntologyBuilder() {
		long start = System.nanoTime();
		//
		CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder);
		DomainOntologyDirector director = new DomainOntologyDirector();
		DomainOntologyBuilder builder = new DomainOntologyBuilder(controller);
		DomainOntology donto = director.createDomainOntology(builder);
		
		Assert.assertEquals(ontologyName, donto.getName());
		Assert.assertEquals(expectedConcepts, donto.getDefines().size());
		//
		System.out.println("[" + ( ((System.nanoTime() - start)) / 1_000_000) +  "ms]");
	}
	
	@Test
	public void testContenctOntologyBuilder() {
		long start = System.nanoTime();
		//
		CROPOntologyController controller = 
				new CROPOntologyController(cropOntologyFolder);
		ContentOntologyDirector director = new ContentOntologyDirector();
		ContentOntologyBuilder builder = new ContentOntologyBuilder(controller);
		ContentOntology donto = director.createContentOntology(builder, ontologyName);
		
		Assert.assertEquals(ontologyName, donto.getName());
		Assert.assertEquals(expectedConcepts, donto.getDefines().size());
		//
		System.out.println("[" + ( ((System.nanoTime() - start)) / 1_000_000) +  "ms]");
	}
}
