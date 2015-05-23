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
package edu.teilar.jcrop.domain.builder.ontology;

import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.concept.ConceptBuilder;
import edu.teilar.jcrop.domain.builder.concept.EducationalObjectiveBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.EducationalObjectiveDirector;
import edu.teilar.jcrop.domain.ontology.ContentOntology;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

/**
 * @author m.tsiakmaki
 *
 */
public class ContentOntologyBuilder {

	private CROPOntologyController controller; 
	
	private ContentOntology contentOntology;
		
	public void buildContentOntology(String name) {
		Assert.assertNotNull("ContentOntology should have name", name);
		this.contentOntology = new ContentOntology(name);
	}

	public void buildDefines() {
		OWLNamedIndividual coIndi = controller.getDataFactory()
				.getContentOntologyIndi(this.contentOntology.getName());
		Set<OWLIndividual> definesSet = coIndi.getObjectPropertyValues(
				controller.getDataFactory().getDefines(), 
				controller.getOwlManager().getConceptGraphOntology());
		
		EducationalObjectiveDirector conceptDirector = new EducationalObjectiveDirector(); 
		EducationalObjectiveBuilder conceptBuilder = new ConceptBuilder();
		for (OWLIndividual d : definesSet) {
			// get the name of the concept 
			String cName = OntoUtil.stipPostfix(Postfix.Concept, d.asOWLNamedIndividual());
			Concept c = (Concept)conceptDirector.createEducationalObjective(
					conceptBuilder, cName);
			this.contentOntology.addDefines(c);
		}
	}

	
	public ContentOntology getContentOntology() {
		return this.contentOntology;
	}
	
	public ContentOntologyBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
}
