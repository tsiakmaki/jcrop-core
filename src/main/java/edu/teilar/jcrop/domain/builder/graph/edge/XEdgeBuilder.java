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
package edu.teilar.jcrop.domain.builder.graph.edge;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.edge.IfFalseEdge;
import edu.teilar.jcrop.domain.graph.edge.IfTrueEdge;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.utils.OntoUtil;
import edu.teilar.jcrop.utils.OntoUtil.Postfix;

/**
 * @author m.tsiakmaki
 *
 */
public class XEdgeBuilder {

	private Edge edge;
	
	private CROPOntologyController controller;
	
	public XEdgeBuilder(CROPOntologyController controller) {
		this.controller = controller; 
	}
	
	public void buildEdge(OWLIndividual eIndi, XGraph xgraph, KObject kobj) {
		
		Set<OWLClassExpression> types = eIndi.getTypes(
				controller.getOwlManager().getOntologies());
		
		if(types.contains(controller.getDataFactory().getIfFalseEdge())) {
			edge = new IfFalseEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
					eIndi.asOWLNamedIndividual(), xgraph, kobj));
		} else if(types.contains(controller.getDataFactory().getIfTrueEdge())) {
			edge = new IfTrueEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
					eIndi.asOWLNamedIndividual(), xgraph, kobj));
		} else {
			edge = new DefaultEdge(OntoUtil.stipPostfix(Postfix.XGraphEdge, 
					eIndi.asOWLNamedIndividual(), xgraph, kobj));
		}
	}
	
	public Edge getEdge() {
		return this.edge;
	}
}
