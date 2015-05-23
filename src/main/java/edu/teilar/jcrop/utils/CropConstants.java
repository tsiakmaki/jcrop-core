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

/**
 * @author m.tsiakmaki
 *
 */
public class CropConstants {

	// the name of the edge type when the connection of the two nodes is recommended (not necessary) 
	public static final String RecommendedEdgeType = "recommendedEdge";

	public static final String RecommendedConcepts = "Recommended Concepts";
	
	// the name of the edge type when the connection of the two nodes is prerequisite
	public static final String PrerequisiteEdgeType = "prerequisiteEdge";
	
	public static final String PrerequisiteConcepts = "Prerequisite Concepts";
	
	
	/** crop ontology */
	public static final String KObjectFilename = "KObject.owl";
	
	/** the name of the folder in the project's folder where the populated crop ontology will be placed */
	public static final String cropOntologiesFolderNameInTheProject = "crop";
	
	/** */
	public static final String[] cropOntologiesNames = {
		"KObject.owl", "LearningDomain.owl", "SpeechAct.owl", 
		"Channel.owl", "KOrder.owl", "LearningDomainParticipant.owl", "Message.owl", 
		"XGraph.owl", "CommunicativeAct.owl", "KRC.owl", "LearningDomainRole.owl", 
		"Participant.owl", "XModel.owl", "ConceptGraph.owl", "LearnerInformation.owl",
		"LearningObjectMetaData.owl", "ParticipantProfile.owl", "Expression.owl", 
		"LearnerModel.owl", "LearningObject.owl", "Process.owl", "Graph.owl",
		"LearnerTypeUO.owl", "LearningStyle.owl", "Profile.owl", "KConcept.owl",
		"LearningBehavior.owl", "LIPInformation.owl", "Role.owl"
	};
	
	
	public static final String mxGraphExtention = ".mxg";
	
	public static final String mxGraphDirName = "mxGraphs";
	
	public static final String[] cropGraphNames = {
		"ConceptGraph", "KRC", "XGraph", "XModel", "XManager"
	};
	
	public static final String[] priorities = {
			"TargetConceptPriority",
			"SupportPriority",
			"AssessmentPriority",
			"MultimediaPriority",
			"TextPriority",
			"RandomPriority" };	

}
