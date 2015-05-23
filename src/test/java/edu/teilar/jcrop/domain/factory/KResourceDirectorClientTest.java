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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.builder.resource.KProductBuilder;
import edu.teilar.jcrop.domain.builder.resource.KResourceAssessmentBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.KObjectDirector;
import edu.teilar.jcrop.domain.graph.ConceptGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.edge.IfFalseEdge;
import edu.teilar.jcrop.domain.graph.edge.IfTrueEdge;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.ConceptGraphNode;
import edu.teilar.jcrop.domain.graph.node.ControlNode;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * @author m.tsiakmaki
 *
 */
public class KResourceDirectorClientTest {

	private File cropOntologyFolder = 
			new File("/home/maria/dev/LearningObjects/complex");

	private String ontologyName = 
			"file:/home/maria/dev/LearningObjects/complex/content_ontologies/complex_number_system.owl";
	
	private CROPOntologyController controller = 
			new CROPOntologyController(cropOntologyFolder); 
	
	
	// the class under test should be instantiated 
	// in setUp method (or in a test method).
	// When the JUnit examples create an ArrayList in the setUp method, 
	// they all go on to test the behavior of that ArrayList, 
	// with cases like testIndexOutOfBoundException, 
	// testEmptyCollection, and the like. 
	// The perspective there is of someone writing a class and making sure it works right.
	/**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    	KObjectBuilderCreator builderCreator = 
				new KObjectBuilderCreator(controller);
    	for(KObject kobj : controller.getKObjects().values()) {
			KObjectBuilder builder = builderCreator.createBuilder(kobj);
			builder.buildKObject(kobj.getName());
			builder.buildContentOntology();
			builder.buildTarget();
		}
    }
	
	// @Test(expected=IndexOutOfBoundsException.class)
	//@Test
	public void testKResourceAssessmentBuilder() {
		
		KResourceAssessment r; 
		String kobjName = "Test1";
		
		KResourceAssessmentBuilder builder = new KResourceAssessmentBuilder(controller); 
		
		builder.buildKObject(kobjName);
		r = (KResourceAssessment)builder.getKObject();
		
		// bottom up build
		// build content ontology, 
		builder.buildContentOntology();

		Assert.assertEquals(ontologyName, 
				builder.getKObject().getAssociatedContentOntology().getName());
	
		String expectedTarget = "ComplexNumberSystem";
		builder.buildTarget();
		Assert.assertEquals(expectedTarget, builder.getKObject()
				.getTargetEducationalObjective().getName());
		
		builder.buildPrerequisites();
		Assert.assertEquals(0, r.getPrerequisites().size());
		//String expectedPrerequisite = "RealNumberSystem";
		//Assert.assertEquals(expectedPrerequisite, 
		//		r.getPrerequisites().iterator().next().getName());
		
		builder.buildConceptGraph();
		
		builder.buildKRC();
		
		Assert.assertEquals(1, r.getAssociatedKRCGraph().getNodes().size());
		
		Assert.assertEquals(0, r.getAssociatedKRCGraph().getEdges().size());
		
		builder.buildExecutionModel();
		
		Assert.assertEquals(1, r.getExecutionModel().getxGraph().getNodes().size());
		
		ParXGroup g = (ParXGroup)r.getExecutionModel()
				.getxGraph().getNodes().iterator().next();
		
		Assert.assertEquals(1, r.getExecutionModel().getxGraph().getNodes().size());
		
		Assert.assertEquals(1, g.getNodes().size());
		
		LearningActNode l = (LearningActNode) g.getNodes().iterator().next();
		
		PhysicalLocation pl = (PhysicalLocation)l.getAssociated();
		
		Assert.assertEquals(
			"C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex\\resources\\Test1.obj", 
			pl.getPhysicalLocation());
	}
	
	@Test
	public void testKProductBuilder() {
		
		KProduct p;

		String kobjName = "complex";
		
		KProductBuilder builder = new KProductBuilder(controller); 
		
		builder.buildKObject(kobjName);
		p = (KProduct)builder.getKObject();
		
		// bottom up build
		// build content ontology, 
		builder.buildContentOntology();

		Assert.assertEquals(ontologyName, 
				builder.getKObject().getAssociatedContentOntology().getName());
	
		String expectedTarget = "ComplexNumberSystem";
		builder.buildTarget();
		Assert.assertEquals(expectedTarget, builder.getKObject()
				.getTargetEducationalObjective().getName());
		
		String expectedPrerequisite = "RealNumberSystem";
		builder.buildPrerequisites();
		Assert.assertEquals(1, p.getPrerequisites().size());
		Assert.assertEquals(expectedPrerequisite, 
				p.getPrerequisites().iterator().next().getName());
		
		builder.buildConceptGraph();
		Assert.assertEquals(5, p.getAssociatedConceptGraph().getNodes().size());
		Assert.assertEquals(5, p.getAssociatedConceptGraph().getEdges().size());
		ConceptGraphNode cgn = (ConceptGraphNode)p.getAssociatedConceptGraph().getNodeByName("Division");
		Assert.assertEquals(Concept.class, cgn.getAssociated().getClass());
		Concept divConcept = (Concept)cgn.getAssociated();
		Assert.assertEquals("Division", divConcept.getName()); 
		
		builder.buildKRC();
		Assert.assertEquals(4, p.getAssociatedKRCGraph().getNodes().size());
		Assert.assertEquals(4, p.getAssociatedKRCGraph().getEdges().size());
		
		builder.buildExecutionModel();
		Assert.assertEquals(7, p.getExecutionModel().getxGraph().getNodes().size());
		Assert.assertEquals(8, p.getExecutionModel().getxGraph().getEdges().size());

		Float expectedThreshold = new Float(0.7);
		ControlNode c = (ControlNode)p.getExecutionModel().getxGraph().getNodeByName("Control_2");
		Assert.assertEquals(expectedThreshold, c.getThreshold());
		Assert.assertEquals(2, c.getIsStartOf().size());
		Set<Class> ccc = new HashSet<Class>();
		for(Edge eee: c.getIsStartOf()) {
			ccc.add(eee.getClass());
			//System.out.println(eee.getName() + " type: " + eee.getClass());
		}
		Assert.assertTrue(ccc.contains(IfTrueEdge.class));
		Assert.assertTrue(ccc.contains(IfFalseEdge.class));
		
		ParXGroup g = (ParXGroup)p.getExecutionModel()
				.getxGraph().getNodeByName("ComplexNumberSystem");
		Assert.assertEquals(2, g.getNodes().size());
		Assert.assertEquals(1, g.getEdges().size());
		Assert.assertEquals(g.getAssociatedTargetEducationalObjective().getName(), 
				"ComplexNumberSystem");
		
		LearningActNode a = (LearningActNode)g.getNodeByName("Sys_act");
		
		KResourceSupport s = (KResourceSupport)a.getAssociated();
	
		Assert.assertEquals(s.getClass(), KResourceSupport.class);
		Assert.assertEquals(0, a.getIsEndOf().size());
		Assert.assertEquals(1, a.getIsStartOf().size());
		
		Assert.assertEquals(a.getAssociatedTargetEducationalObjective().getName(), 
				"ComplexNumberSystem");
				
	}
	
}
