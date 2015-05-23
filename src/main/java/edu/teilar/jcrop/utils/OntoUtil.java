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
package edu.teilar.jcrop.utils;

import junit.framework.Assert;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.resource.KObject;

/**
 * @author m.tsiakmaki
 *
 */
public class OntoUtil {

	/** the postfix, placed after each kconcept individual */
	public static final String KObjectPostfix 			= "_KObject";
	public static final String ConceptPostfix 			= "_Concept";
	public static final String ConceptGraphPostfix 		= "_ConceptGraph";
	public static final String ConceptGraphNodePostfix 	= "_ConceptGraphNode";
	public static final String XGraphPostfix 			= "_XGraph";
	public static final String XNodePostfix				= "_XNode";
	public static final String LearningActNodePostfix	= "_LearningActNode";
	public static final String LearningActNodeNamePostfix = "_act";
	
	public static final String DialogueNode 			= "DialogueNode";
	public static final String DialogueNodePostfix 		= "_DialogueNode";
	public static final String ControlNodePostfix 		= "_ControlNode";
	public static final String ControlNode 				= "Control";
	public static final String SeqGroupPostfix 			= "_SeqGroup";
	public static final String ParGroupPostfix 			= "_ParGroup";
	
	public static final String KRCPostfix 				= "_KRC";
	public static final String KRCNodePostfix 			= "_KRCNode";
	public static final String PhysicalLocationPostfix 	= "_PhysicalLocation";

	public static final String KRCEdgePostfix 			= "_KRCEdge";
	public static final String XGraphEdgePostfix 		= "_XGraphEdge";
	public static final String ConceptGraphEdgePostfix 	= "_ConceptGraphEdge";
	public static final String LOMPostfix 				= "_LOM";
	public static final String XModelPostfix			= "_ExecutionModel";
	public static final String XManagerPostfix			= "_ExecutionManager";

	/* lom */
	public static final String EducationPostfix			= "_Education";
	public static final String RelationPostfix			= "_Relation";
	public static final String ResourcePostfix			= "_Resource";
	public static final String ClassificationPostfix	= "_Classification";
	public static final String GeneralPostfix			= "_General";
	public static final String LifecyclePostfix			= "_Lifecycle";
	public static final String TechnicalPostfix			= "_Technical";
	public static final String MetaMetaDataPostfix		= "_MetaMetadata";
	public static final String RightsPostfix			= "_Rights";
	public static final String TaxonPostfix				= "_Taxon";
	public static final String TaxonPathPostfix			= "_TaxonPath";
	public static final String IdentifierPostfix		= "_Identifier";
	
	
	public static final IRI KConceptIri 		= IRI.create("http://www.cs.teilar.gr/ontologies/KConcept.owl");
	public static final IRI GraphIri 			= IRI.create("http://www.cs.teilar.gr/ontologies/Graph.owl");
	public static final IRI ConceptGraphIri 	= IRI.create("http://www.cs.teilar.gr/ontologies/ConceptGraph.owl");

	public static final IRI KRCIri 				= IRI.create("http://www.cs.teilar.gr/ontologies/KRC.owl");
	public static final IRI LearningObjectIri	= IRI.create("http://www.cs.teilar.gr/ontologies/LearningObject.owl");
	public static final IRI LOMIri				= IRI.create("http://www.cs.teilar.gr/ontologies/LearningObjectMetaData.owl");
	public static final IRI KObjectIri			= IRI.create("http://www.cs.teilar.gr/ontologies/KObject.owl");
	public static final IRI XGraphIri			= IRI.create("http://www.cs.teilar.gr/ontologies/XGraph.owl");
	public static final IRI XModelIri			= IRI.create("http://www.cs.teilar.gr/ontologies/XModel.owl");
	
	public static final PrefixManager KConceptPM 		= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/KConcept.owl#");
	public static final PrefixManager GraphPM 			= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/Graph.owl#");
	public static final PrefixManager ConceptGraphPM 	= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/ConceptGraph.owl#");
	public static final PrefixManager KRCPM 			= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/KRC.owl#");
	public static final PrefixManager LearningObjectPM 	= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/LearningObject.owl#");
	public static final PrefixManager KObjectPM 		= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/KObject.owl#");
	public static final PrefixManager LOMPM 			= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/LearningObjectMetaData.owl#");
	public static final PrefixManager XGraphPM 			= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/XGraph.owl#");
	public static final PrefixManager XModelPM 			= new DefaultPrefixManager("http://www.cs.teilar.gr/ontologies/XModel.owl#");
	
	public static enum XEdgeType {
		Default(1), IfTrue(2), IfFalse(3);
		
		private int value;
		
		public int getValue() {
			return this.value;
		}
		private XEdgeType(int value) {
			this.value = value;
		}
	}
	
	public static enum XNodeType {
		Dialogue(2), Control(3), ParGroup(4), LearningAct(1);
		
		private int value;
		
		public int getValue() {
			return this.value;
		}
		
		private Postfix getPostfix() {
			switch (this.value) {
			case 2:
				return Postfix.DialogueNode;
			case 3:
				return Postfix.ControlNode;
			case 4:
				return Postfix.ParGroup;
			case 1:
				return Postfix.LearningActNode;
		
			default:
				throw new NullPointerException("Postfix not found for value " + this.value);
			}
		}
		
		private XNodeType(int value) {
			this.value = value;
		}
	}
	
	public static enum Postfix {
		XGraphEdge(OntoUtil.XGraphEdgePostfix),
		DialogueNode(OntoUtil.DialogueNodePostfix),
		ControlNode(OntoUtil.ControlNodePostfix),
		ParGroup(OntoUtil.ParGroupPostfix),
		LearningActNode(OntoUtil.LearningActNodePostfix),
		Concept(OntoUtil.ConceptPostfix),
		ConceptGraphEdge(OntoUtil.ConceptGraphEdgePostfix), 
		ConceptGraphNode(OntoUtil.ConceptGraphNodePostfix),
		KRCEdge(OntoUtil.KRCEdgePostfix), 
		KRCNode(OntoUtil.KRCNodePostfix),
		PhysicalLocation(OntoUtil.PhysicalLocationPostfix),
		KObject(OntoUtil.KObjectPostfix);
		
		private String value;
		 
		private Postfix(String value) {
			this.value = value;
		}
	}
	
	public static String stipPostfix(Postfix p, OWLNamedIndividual i, 
			KObject kobj) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf("_" + kobj.getName() + p.value);
		if(indx != -1) {
			return fragment.substring(0, indx);
		} else {
			System.out.println("\n" + "_" + kobj.getName() + p.value + "\n");
			throw new NullPointerException(
					"1.Could not strip name for: " + 
							"postfix: " + p +
							", indi: " + i + 
							", kobj: " + kobj.getName()
					);
		}
	}
	
	public static String stipPostfix(Postfix p, OWLNamedIndividual i) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf(p.value);
		
		if(indx != -1) {
			return fragment.substring(0, indx);
		} else {
			throw new NullPointerException(
					"2.Could not strip name for: " + 
							"postfix: " + p +
							", indi: " + i 
					);
		}
	}
	
	public static String stipPostfix(Postfix p, OWLNamedIndividual i, 
			XGraph xgraph, KObject kobj) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf("_" + xgraph.getName() + "_" + kobj.getName() + p.value);
		
		
		
		if(indx != -1) {
			return fragment.substring(0, indx);
		} else {
			System.out.println("\n" + "_" + xgraph.getName() + "_" + kobj.getName() + p.value + "\n");
			throw new NullPointerException(
					"3.Could not strip name for: " + 
							"postfix: " + p +
							", indi: " + i + 
							", kobj: " + kobj.getName() +
							", xgraph: " + xgraph.getName()
					);
		}
	}
	
	public static String stipPostfix(XNodeType t, OWLNamedIndividual i, 
			XGraph xgraph, KObject kobj) {
		String fragment = i.getIRI().getFragment();
		int indx = fragment.lastIndexOf("_" + xgraph.getName() + "_" + kobj.getName() + "_" + t.getPostfix());
		
		
		
		if(indx != -1) {
			return fragment.substring(0, indx);
		} else {
			System.out.println("\n" + "_" + xgraph.getName() + "_" + kobj.getName() + "_" + t.getPostfix() +"\n");
			throw new NullPointerException(
					"4.Could not strip name for: " + 
							"XNodeType: " + t +
							", indi: " + i + 
							", kobj: " + kobj.getName() +
							", xgraph: " + xgraph.getName()
					);
		}
	}
	
	
	
}
