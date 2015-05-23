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

import org.junit.Assert;
import org.junit.Test;

import edu.teilar.jcrop.domain.builder.resource.KResourceSupportBuilder;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;

public class KResourceSupportDirectorTest {

	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");

	private String ontologyName = 
			"file:/C:/git/java/jcrop-core/src/test/resources/crop-projects/complex/content_ontologies/complex_number_system.owl";
	
	@Test
	public void test() {
		long start = System.nanoTime();
		
		CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder);
		
		System.out.println("Controller loaded " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		KResourceSupport r; 
		String kobjName = "Sys";
		
		KResourceSupportBuilder builder = new KResourceSupportBuilder(controller); 
		
		builder.buildKObject(kobjName);
		System.out.println("buildKObject " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		r = (KResourceSupport)builder.getKObject();
		
		// bottom up build
		// build content ontology, 
		builder.buildContentOntology();
		System.out.println("buildContentOntology " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		Assert.assertEquals(ontologyName, 
				builder.getKObject().getAssociatedContentOntology().getName());
	
		String expectedTarget = "ComplexNumberSystem";
		builder.buildTarget();
		System.out.println("buildTarget " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		Assert.assertEquals(expectedTarget, builder.getKObject()
				.getTargetEducationalObjective().getName());
		
		builder.buildPrerequisites();
		System.out.println("buildPrerequisites " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		Assert.assertEquals(0, r.getPrerequisites().size());
		//String expectedPrerequisite = "RealNumberSystem";
		//Assert.assertEquals(expectedPrerequisite, 
		//		r.getPrerequisites().iterator().next().getName());
		
		builder.buildConceptGraph();
		System.out.println("buildConceptGraph " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		
		builder.buildKRC();
		System.out.println("buildKRC " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		Assert.assertEquals(1, r.getAssociatedKRCGraph().getNodes().size());
		Assert.assertEquals(0, r.getAssociatedKRCGraph().getEdges().size());
		
		builder.buildExecutionModel();
		System.out.println("buildExecutionModel " + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		Assert.assertEquals(1, r.getExecutionModel().getxGraph().getNodes().size());
		
		ParXGroup g = (ParXGroup)r.getExecutionModel()
				.getxGraph().getNodes().iterator().next();
		
		Assert.assertEquals(1, r.getExecutionModel().getxGraph().getNodes().size());
		
		Assert.assertEquals(1, g.getNodes().size());
		
		LearningActNode l = (LearningActNode) g.getNodes().iterator().next();
		
		PhysicalLocation pl = (PhysicalLocation)l.getAssociated();
		
		Assert.assertEquals(
			"C:\\dev\\LearningObjects\\complex1\\resources\\ComplexNumberSystem.txt", 
			pl.getPhysicalLocation());
	}
	
}
