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

package edu.teilar.jcrop.domain.builder.ontology;

import java.util.Set;

import junit.framework.Assert;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import edu.teilar.jcrop.domain.builder.concept.ConceptBuilder;
import edu.teilar.jcrop.domain.builder.concept.EducationalObjectiveBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.EducationalObjectiveDirector;
import edu.teilar.jcrop.domain.ontology.DomainOntology;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

public class DomainOntologyBuilder {

	private CROPOntologyController controller; 
	
	private DomainOntology domainOntology;
	
	public void buildDomainOntology() {
		Set<OWLIndividual> doSet = controller.getDataFactory().
				getDomainOntology().getIndividuals(
						controller.getOwlManager().getConceptGraphOntology());
		Assert.assertEquals(1, doSet.size());
		
		OWLNamedIndividual doIndi = doSet.iterator().next().asOWLNamedIndividual();
		// the associated Content Ontology indi
		int beginIndx = doIndi.getIRI().toString().indexOf("#") + 1;
		String ontologyName = doIndi.getIRI().toString().substring(beginIndx); 
		
		Assert.assertNotNull("DomainOntology should have name", ontologyName);
		this.domainOntology = new DomainOntology(ontologyName);
	}

	public void buildDefines() {
		OWLNamedIndividual doIndi = controller.getDataFactory()
				.getDomainOntologyIndi(this.domainOntology.getName());
		Set<OWLIndividual> definesSet = doIndi.getObjectPropertyValues(
				controller.getDataFactory().getDefines(),
				controller.getOwlManager().getConceptGraphOntology());
		
		EducationalObjectiveDirector conceptDirector = new EducationalObjectiveDirector(); 
		EducationalObjectiveBuilder conceptBuilder = new ConceptBuilder();
		for (OWLIndividual d : definesSet) {
			// get the name of the concept 
			String cName = OntoUtil.stipPostfix(Postfix.Concept, d.asOWLNamedIndividual());
			Concept c = (Concept)conceptDirector.createEducationalObjective(
					conceptBuilder, cName);
			this.domainOntology.addDefines(c);
		}
	}

	public DomainOntology getDomainOntology() {
		return this.domainOntology;
	}
	
	public DomainOntologyBuilder(CROPOntologyController controller) {
		this.controller = controller;
	}
	
}
