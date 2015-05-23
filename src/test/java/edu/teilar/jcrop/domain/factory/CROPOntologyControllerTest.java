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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.CropConstants;
import edu.teilar.jcrop.utils.OntoUtil;

public class CROPOntologyControllerTest {

	private String cropOntologyFolder = 
			new String("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\jcropeditor");

	
	@Test
	public void testWithoutReasoner() {
		
		CROPOntologyController controller = new CROPOntologyController();
		controller.setCropProjectPath(cropOntologyFolder);
		
		System.out.println("\nInitializing Crop Ontology Controller"); 
		
		long start = System.nanoTime(); 
		
		File cropOntologiesFolderInProject = new File(controller.getCropProjectPath(),
				CropConstants.cropOntologiesFolderNameInTheProject);
		
		Assert.assertTrue(cropOntologiesFolderInProject.exists());
		controller.loadKObjectOntology(cropOntologiesFolderInProject);
		
		System.out.println("Crop Ontology Controller loaded: " + 
				cropOntologiesFolderInProject.getName() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		start = System.nanoTime();
		
		controller.setKObjects(initLearningObjects(controller));
		
		System.out.println("Num of kObjects loaded: " + 
				controller.getKObjects().size() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		
		//print(controller.getKObjects().values());
		
		
		OWLNamedIndividual targetIndividual = controller.getDataFactory()
				.getConceptIndi("CROPEditor");
		NodeSet<OWLNamedIndividual> kobjects = controller.getReasoner()
				.getObjectPropertyValues(targetIndividual,
						controller.getDataFactory().getIsTargetOf());
		
		System.out.println("Num of kObjects with this target : " + 
				kobjects.getFlattened().size() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		
		for (OWLNamedIndividual i : kobjects.getFlattened()) {
			System.out.println(stipPostFixFromIndividual(i));
			
			/**
			 * 
Initializing Crop Ontology Controller
Crop Ontology Controller loaded: crop [1107ms]
Num of kObjects loaded: 12 [1ms]
CROPEditorTest1 class edu.teilar.jcrop.domain.resource.KResourceAssessment
BuildingSteps class edu.teilar.jcrop.domain.resource.KProduct
ContentOntologyBuildingSteps class edu.teilar.jcrop.domain.resource.KResourceSupport
ConceptGraphBuildingSteps class edu.teilar.jcrop.domain.resource.KResourceSupport
ExecutionGraphBuildingSteps class edu.teilar.jcrop.domain.resource.KResourceSupport
KRCGraphBuildingSteps class edu.teilar.jcrop.domain.resource.KResourceSupport
CropObjectBuildingStepsTest1 class edu.teilar.jcrop.domain.resource.KResourceAssessment
CROPObjectsDesignPrinciples class edu.teilar.jcrop.domain.resource.KResourceSupport
CropRefArch class edu.teilar.jcrop.domain.resource.KResourceSupport
ExecutionSample class edu.teilar.jcrop.domain.resource.KResourceSupport
XManagerBuildingSteps class edu.teilar.jcrop.domain.resource.KResourceSupport
jcropeditor class edu.teilar.jcrop.domain.resource.KProduct
BuildingSteps
CropObjectBuildingStepsTest1

			 */
		}
		
	}
	
	//@Test
	public void testWithReasoner() {
		
		CROPOntologyController controller = new CROPOntologyController();
		controller.setCropProjectPath(cropOntologyFolder);
		
		System.out.println("\nInitializing Crop Ontology Controller with reasoner"); 
		
		long start = System.nanoTime(); 
		
		File cropOntologiesFolderInProject = new File(controller.getCropProjectPath(),
				CropConstants.cropOntologiesFolderNameInTheProject);
		
		Assert.assertTrue(cropOntologiesFolderInProject.exists());
		controller.loadKObjectOntology(cropOntologiesFolderInProject);
		
		System.out.println("Crop Ontology Controller loaded: " + 
				cropOntologiesFolderInProject.getName() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		start = System.nanoTime();
		
		initLearningObjectUsingReasoner(controller);
		System.out.println("Num of kObjects loaded: " + 
				controller.getKObjects().size() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		
		print(controller.getKObjects().values());
		
	}
	
	private void initLearningObjectUsingReasoner(CROPOntologyController controller) {
		controller.setKObjects(new HashMap<String, KObject>());
		NodeSet<OWLNamedIndividual> loNodeSet = controller.getReasoner().getInstances(
				controller.getDataFactory().getLearningObject(), false);
		for(OWLNamedIndividual i : loNodeSet.getFlattened()) { 
			KObject kobj = getLearningObject(controller, i); 
			if(kobj != null) {
				String kobjName = stipPostFixFromIndividual(i);
				kobj.setName(kobjName);
				controller.getKObjects().put(kobjName, kobj);
			} 
		}
	}
	
	private KObject getLearningObject(CROPOntologyController controller, 
			OWLNamedIndividual kobjIndi) {
		Set<OWLClass> types = controller.getReasoner().getTypes(
				kobjIndi, false).getFlattened(); 
		
		if(types.contains(controller.getDataFactory().getKProduct())) {
			return new KProduct();
		} else if(types.contains(controller.getDataFactory().getAssessmentResource())) {
			return new KResourceAssessment();
		} else if(types.contains(controller.getDataFactory().getSupportResource())) {
			return new KResourceSupport();
		}
		return null; 
	}
	
	private Map<String, KObject> initLearningObjects(CROPOntologyController controller) {
		Map<String, KObject> kobjects = new HashMap<String, KObject>();
		kobjects.putAll(getKObj(controller, 
				controller.getDataFactory().getKProduct(), KProduct.class));
		kobjects.putAll(getKObj(controller, 
				controller.getDataFactory().getAssessmentResource(), KResourceAssessment.class));
		kobjects.putAll(getKObj(controller, controller.getDataFactory().getSupportResource(), KResourceSupport.class));
		return kobjects;
	}
	
	private Map<String, ? extends KObject> getKObj(
			CROPOntologyController controller, 
			OWLClass c, Class<? extends KObject> clazz) {
		Map<String, KObject> kobjects = new HashMap<String, KObject>();
		
		for(OWLIndividual i : c.getIndividuals(controller.getOwlOntology())) {
			String kobjName = stipPostFixFromIndividual(i.asOWLNamedIndividual());
			try {
				Constructor<? extends KObject> con = clazz.getConstructor(String.class);
				kobjects.put(kobjName, con.newInstance(kobjName));
			} catch (NoSuchMethodException | SecurityException  
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// 
				e.printStackTrace();
			} 
			
			
		}
		return kobjects;
	}
	
	
	private String stipPostFixFromIndividual(OWLNamedIndividual i) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf(OntoUtil.KObjectPostfix);
		return fragment.substring(0, indx);
	}
	
	private void print(Collection<KObject> k) {
		for (KObject kk : k) {
			System.out.println(kk.getName() + " " + kk.getClass());
		}
	}
}
