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

import edu.teilar.jcrop.domain.builder.graph.node.ConceptGraphNodeBuilder;
import edu.teilar.jcrop.domain.builder.graph.node.NodeBuilder;
import edu.teilar.jcrop.domain.builder.resource.KResourceAssessmentBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.owl.CROPOntologyController;

public class ConceptGraphNodeDirectorTest {
	
	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");
	
	private String kobjName = "Test1";
	
	private String nodeName = "ComplexNumberSystem";
	
	
	@Test
	public void test() {
		CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder);
		
		// 
		KObject r; 
		//LearningObjectDirector director = new LearningObjectDirector(); 
		KResourceAssessmentBuilder builder = new KResourceAssessmentBuilder(controller); 
		builder.buildKObject(kobjName);
		// bottom up build
		// build content ontology, 
		builder.buildContentOntology();
		builder.buildTarget();
		builder.buildConceptGraph();
		
		// ... 
		r = (KResourceAssessment)builder.getKObject();
		
		
		NodeBuilder nBuilder = new ConceptGraphNodeBuilder(controller);
		nBuilder.buildNode(nodeName);
		nBuilder.buildAssociated(r);
		nBuilder.buildIsStartOf(r);
		nBuilder.buildIsEndOf(r);
		
		Node n = nBuilder.getNode(); 
		Assert.assertTrue(n.getAssociated() instanceof Concept);
		Assert.assertTrue(((Concept)n.getAssociated()).getName().equals(nodeName));
		
		Assert.assertEquals(0, n.getIsStartOf().size());
		//Assert.assertTrue(n.getIsStartOf().iterator().next().getName().equals("From_ComplexNumberDefinition_To_Operation"));
		
		Assert.assertEquals(0, n.getIsEndOf().size());
		//Assert.assertTrue(n.getIsEndOf().iterator().next().getName().equals("From_RealNumberSystem_To_ComplexNumberDefinition"));
	}


}
