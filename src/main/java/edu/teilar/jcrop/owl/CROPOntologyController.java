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
package edu.teilar.jcrop.owl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.owl.datafactory.CROPOWLDataFactoryImpl;
import edu.teilar.jcrop.owl.manager.CROPOWLOntologyManager;
import edu.teilar.jcrop.utils.CropConstants;
import edu.teilar.jcrop.utils.OntoUtil;


/**
 * @author m.tsiakmaki
 *
 */
public class CROPOntologyController {
	
	private static final Logger logger = 
			Logger.getLogger(CROPOntologyController.class);
	
	/** kobject ontology manager - manages the set of crop ontologies */
	private CROPOWLOntologyManager manager;

	public CROPOWLOntologyManager getOwlManager() {
		return manager;
	}

	
	private Configuration configuration;

	/** KObject ontology */
	private OWLOntology ontology;

	public OWLOntology getOwlOntology() {
		return ontology;
	}

	/** the reasoner */
	private OWLReasoner reasoner;

	public OWLReasoner getReasoner() {
		return reasoner;
	}

	/** reasoner factory, for loading the hierarchy of classes */
	private OWLReasonerFactory reasonerFactory;

	public OWLReasonerFactory getOwlReasonerFactory() {
		return reasonerFactory;
	}

	// An interface for creating entities, class expressions and axioms
	private CROPOWLDataFactoryImpl dataFactory;

	public CROPOWLDataFactoryImpl getDataFactory() {
		return dataFactory;
	}

	private OWLEntityRenamer renamer;

	public OWLEntityRenamer getRenamer() {
		return renamer;
	}
	
	private Map<String, KObject> kobjects; 
	
	
	public Map<String, KObject> getKObjects() {
		return kobjects;
	}

	public void setKObjects(Map<String, KObject> kobjects) {
		this.kobjects = kobjects;
	}
	
	public KObject getLearningObjectByName(String name) {
		return this.kobjects.get(name);
	}
	
	private File cropProjectPath; 
	
	public File getCropProjectPath() {
		return this.cropProjectPath;
	}
	public void setCropProjectPath(String path) {
		this.cropProjectPath = new File(path);
	}
	
	public CROPOntologyController() {
		
	}
	
	public CROPOntologyController(File cropProjectPath) {
		logger.info("Initializing Crop Ontology Controller"); 
		
		this.cropProjectPath = cropProjectPath; 
		
		long start = System.nanoTime(); 
		
		File cropOntologiesFolderInProject = new File(cropProjectPath,
				CropConstants.cropOntologiesFolderNameInTheProject);
		
		// load crop ontology 
		if(!cropOntologiesFolderInProject.exists()) {
			logger.error("crop ontologies folder (that is set in crop " +
					"project) does not exist.");
		}
		
		loadKObjectOntology(cropOntologiesFolderInProject);
		logger.info("Crop Ontology Controller loaded: " + 
				cropOntologiesFolderInProject.getName() + " [" +
				((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		start = System.nanoTime();
		
		initLearningObjects();
		
		logger.info("Learning Objects loaded: " + 
				cropOntologiesFolderInProject.getName() + " [" +
				((System.nanoTime() - start) / 1_000_000_000) +  "s]");
	}

	public void loadKObjectOntology(File cropOntologyFolder) {

		manager = CROPOWLManager.createCROPOWLOntologyManager();
		OWLOntologyIRIMapper autoIRIMapper = new AutoIRIMapper(cropOntologyFolder, false);
		manager.addIRIMapper(autoIRIMapper);

		try {
			File kObjectOntologyFilename = new File(cropOntologyFolder,
					CropConstants.KObjectFilename);
			// logger.info("Loading KObject Ontology from: " +
			// kObjectOntologyFilename.getAbsolutePath());
			ontology = manager.loadOntologyFromOntologyDocument(kObjectOntologyFilename);
			dataFactory = (CROPOWLDataFactoryImpl)manager.getOWLDataFactory();

			configuration = new Configuration();
			configuration.throwInconsistentOntologyException = false;
			// reasonerFactory = new StructuralReasonerFactory();
			reasonerFactory = new Reasoner.ReasonerFactory() {
	            protected OWLReasoner createHermiTOWLReasoner(
	            		org.semanticweb.HermiT.Configuration configuration, OWLOntology ontology) {
	                // don't throw an exception since otherwise we cannot compte explanations 
	                configuration.throwInconsistentOntologyException=false;
	                return new Reasoner(configuration, ontology);
	            }  
	        };
			
			reasoner = reasonerFactory.createNonBufferingReasoner(ontology,	configuration);
			
			renamer = new OWLEntityRenamer(manager, manager.getOntologies());
			
			// add the reasoner as an ontology change listener
			// manager.addOntologyChangeListener(reasoner);
		} catch (OWLOntologyCreationIOException e) {
			// IOExceptions during loading get wrapped in an
			// OWLOntologyCreationIOException
			IOException ioException = e.getCause();
			if (ioException instanceof FileNotFoundException) {
				logger.error("Could not load ontology. File not found: "
						+ ioException.getMessage());
			} else if (ioException instanceof UnknownHostException) {
				logger.error("Could not load ontology. Unknown host: "
						+ ioException.getMessage());
			} else {
				logger.error("Could not load ontology: "
						+ ioException.getClass().getSimpleName() + " "
						+ ioException.getMessage());
			}
		} catch (UnparsableOntologyException e) {
			// If there was a problem loading an ontology because there are
			// syntax errors in the document (file) that
			// represents the ontology then an UnparsableOntologyException is
			// thrown
			logger.error("Could not parse the ontology: "
					+ e.getMessage());
			// A map of errors can be obtained from the exception
			Map<OWLParser, OWLParserException> exceptions = e.getExceptions();
			// The map describes which parsers were tried and what the errors
			// were
			for (OWLParser parser : exceptions.keySet()) {
				logger.error("Tried to parse the ontology with the "
						+ parser.getClass().getSimpleName() + " parser");
				logger.error("Failed because: "
						+ exceptions.get(parser).getMessage());
			}
		} catch (UnloadableImportException e) {
			// If our ontology contains imports and one or more of the imports
			// could not be loaded then an
			// UnloadableImportException will be thrown (depending on the
			// missing imports handling policy)
			logger.error("Could not load import: "
					+ e.getImportsDeclaration());
			// The reason for this is specified and an
			// OWLOntologyCreationException
			OWLOntologyCreationException cause = e
					.getOntologyCreationException();
			logger.error("Reason: " + cause.getMessage());
		} catch (OWLOntologyCreationException e) {
			logger.error("Could not load ontology: " + e.getMessage());
		}

	}

	public void initLearningObjects() {
		kobjects = new HashMap<String, KObject>();
		// construct the KProducts
		kobjects.putAll(constructKObjectsOfType(
				dataFactory.getKProduct(), KProduct.class));
		// construct the KResourceAssessments
		kobjects.putAll(constructKObjectsOfType(
				dataFactory.getAssessmentResource(), KResourceAssessment.class));
		// construct the KResourceSupports
		kobjects.putAll(constructKObjectsOfType(
				dataFactory.getSupportResource(), KResourceSupport.class));
	}

	/**
	 * 
	 * @param owlClazz the owl class, i.e. #KProduct, #AssessmentResource, #SupportResource
	 * @param clazz the java kobject class, i.e 
	 * @return a map of kobject's name , kobject
	 */
	private Map<String, ? extends KObject> constructKObjectsOfType(
			OWLClass owlClazz, Class<? extends KObject> clazz) {
		Map<String, KObject> result = new HashMap<String, KObject>();
		for(OWLIndividual i : owlClazz.getIndividuals(getOwlOntology())) {
			String kobjName = stipPostFixFromIndividual(i.asOWLNamedIndividual());
			try {
				Constructor<? extends KObject> con = clazz.getConstructor(String.class);
				result.put(kobjName, con.newInstance(kobjName));
			} catch (NoSuchMethodException | SecurityException  
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// 
				e.printStackTrace();
			} 
		}
		return result;
	}


	
	private String stipPostFixFromIndividual(OWLNamedIndividual i) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf(OntoUtil.KObjectPostfix);
		return fragment.substring(0, indx);
	}
	
}
