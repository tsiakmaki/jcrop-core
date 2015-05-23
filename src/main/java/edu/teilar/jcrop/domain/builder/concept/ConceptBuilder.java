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
package edu.teilar.jcrop.domain.builder.concept;

import java.util.HashSet;

import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.concept.EducationalObjective;

/**
 * @author m.tsiakmaki
 *
 */
public class ConceptBuilder implements EducationalObjectiveBuilder {

	private Concept concept; 
	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.EducationalObjectiveBuilder#buildEducationalObjective(java.lang.String)
	 */
	@Override
	public void buildEducationalObjective(String name) {
		this.concept = new Concept(name); 
		this.concept.setPrerequisites(new HashSet<Concept>());;
		// 
		//concept.setDefinedBy(definedBy);
		//concept.setPrerequisites(prerequisites);
	}

	
	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.EducationalObjectiveBuilder#getEducationalObjective()
	 */
	@Override
	public EducationalObjective getEducationalObjective() {
		return this.concept;
	}


	/* (non-Javadoc)
	 * @see edu.teilar.jcrop.domain.builder.EducationalObjectiveBuilder#buildPrerequisites()
	 */
	@Override
	public void buildPrerequisites() {
		
	}

}
