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

package edu.teilar.jcrop.domain.director;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;

public class KObjectFinder {

	private static final Logger logger = Logger.getLogger(KObjectFinder.class);
			
	private CROPOntologyController controller;

	public KObjectFinder(CROPOntologyController controller) {
		this.controller = controller;
	}

	/**
	 * 
	 * @param target concept name
	 * @return
	 */
	public List<String> findKObjectsWithTarget(String target) {
		List<String> result = new ArrayList<>();
		long start = System.nanoTime();
		// the target concept indi
		OWLNamedIndividual targetIndividual = controller.getDataFactory()
				.getConceptIndi(target);

		NodeSet<OWLNamedIndividual> kobjects = controller.getReasoner()
				.getObjectPropertyValues(targetIndividual,
						controller.getDataFactory().getIsTargetOf());
		
		System.out.println("Target: " + target + ", Num of kobjects with this target : " + 
				kobjects.getFlattened().size() + " [" +
				((System.nanoTime() - start) / 1_000_000) +  "ms]");
		
		for (OWLNamedIndividual i : kobjects.getFlattened()) {
			result.add(stipPostFixFromIndividual(i));
		}
		logger.info("Target: " + target + ", Num of kobjects found: " + result.size() + 
				 " [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		return result;
	}

	private String stipPostFixFromIndividual(OWLNamedIndividual i) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf(OntoUtil.KObjectPostfix);
		return fragment.substring(0, indx);
	}

}
