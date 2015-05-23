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

import org.junit.Assert;
import org.junit.Test;

import edu.teilar.jcrop.domain.builder.graph.XGraphBuilder;
import edu.teilar.jcrop.domain.builder.resource.KResourceAssessmentBuilder;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * @author m.tsiakmaki
 *
 */
public class XGraphDirectorTest {
	
	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");

	private String ontologyName = 
			"file:/C:/git/java/jcrop-core/src/test/resources/crop-projects/complex/content_ontologies/complex_number_system.owl";
	
	private String kobjName = "Test1";
	private int expectedNumOfEdges = 0;
	private int expectedNumOfNodes = 1; 
	
	@Test
	public void testXGraphOfKResourceAssessment() {
	
		CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder);
		
		// 
		KObject r; 
		KResourceAssessmentBuilder builder = new KResourceAssessmentBuilder(controller); 
		builder.buildKObject(kobjName);
		r = (KObject)builder.getKObject();
		// bottom up build
		// build content ontology, 
		builder.buildContentOntology();
		builder.buildTarget();
		builder.buildPrerequisites();
		builder.buildConceptGraph();
		builder.buildKRC();
		
		// ... 
		
		XGraphBuilder xgBuilder = new XGraphBuilder(controller);
		xgBuilder.buildExecutionGraph();
		xgBuilder.buildEdges(xgBuilder.getXGraph(), r);
		XGraph xg = xgBuilder.getXGraph();
		
		Assert.assertEquals(expectedNumOfEdges, xg.getEdges().size());
		
		xgBuilder.buildNodes(xgBuilder.getXGraph(), r);
		
		Assert.assertEquals(expectedNumOfNodes, xg.getNodes().size());
		
		Assert.assertEquals(kobjName, r.getAssociatedConceptGraph().getName());
		Assert.assertEquals(0, 
				r.getAssociatedConceptGraph().getEdges().size());
		
		Assert.assertEquals("ComplexNumberSystem", 
				r.getAssociatedConceptGraph().getNodes().iterator().next().getName());
	}
}
