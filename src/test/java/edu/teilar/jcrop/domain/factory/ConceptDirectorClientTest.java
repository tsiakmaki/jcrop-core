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
package edu.teilar.jcrop.domain.factory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import edu.teilar.jcrop.domain.builder.ontology.ContentOntologyBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.ContentOntologyDirector;
import edu.teilar.jcrop.domain.ontology.ContentOntology;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * @author m.tsiakmaki
 *
 */
public class ConceptDirectorClientTest {


	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");

	private String ontologyName = 
			"file:/C:/git/java/jcrop-core/src/test/resources/crop-projects/complex/content_ontologies/complex_number_system.owl";
	
	@Test
	public void test() {
		//
		CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder);
		// 
		ContentOntology o; 
		ContentOntologyDirector director = new ContentOntologyDirector(); 
		ContentOntologyBuilder builder = new ContentOntologyBuilder(controller); 
		
		
		director.createContentOntology(builder, ontologyName);
		o = builder.getContentOntology();
		
		Assert.assertNotNull(o);
		Assert.assertNotNull(o.getDefines());
		Assert.assertEquals(6, o.getDefines().size());
	}

}
