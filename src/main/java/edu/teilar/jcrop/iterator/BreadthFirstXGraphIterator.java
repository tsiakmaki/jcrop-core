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

package edu.teilar.jcrop.iterator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.DefaultEdge;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.edge.IfFalseEdge;
import edu.teilar.jcrop.domain.graph.edge.IfTrueEdge;
import edu.teilar.jcrop.domain.graph.node.Node;

/**
 * 
 * 
 * 
 * @version 0.1 2014
 * @author Maria Tsiakmaki
 */
public class BreadthFirstXGraphIterator implements Iterator<Node> {

	private static final Logger logger = Logger.getLogger(BreadthFirstXGraphIterator.class);
	
	private XGraph graph; 
	
	private Node rootNode;
	
	private Set<Node> rootNodes;
	
	/**
	 * @return the rootNodes
	 */
	public Set<Node> getRootNodes() {
		return rootNodes;
	}

	private Set<Node> seen;
	public Set<Node> getSeen() {
		return this.seen;
	}
	private Deque<Node> queue;
	
	public BreadthFirstXGraphIterator(XGraph g) { 
		this.graph = g; 
		this.seen = new HashSet<Node>();
		this.rootNodes = new HashSet<Node>();
		this.queue = new ArrayDeque<Node>();
		
		initRootNodes();
		if(hasNextRootNode()) {
        	// choose at random 
        	this.rootNode = getNextRootNode();
        } else {
        	throw new IllegalArgumentException(
                    "Graph must contain a start node");
        }
	}
	
	public BreadthFirstXGraphIterator(XGraph g, Node startNode) { 
		this.graph = g; 
		this.rootNode = startNode; 
		this.seen = new HashSet<Node>();
		this.rootNodes = new HashSet<Node>();
		this.queue = new ArrayDeque<Node>();
	}
	
	/** Root nodes has no incoming edges */
	private void initRootNodes() {
    	for(Node n : graph.getNodes()) {
    		boolean hasEndOfEdges = false; 
    		for(Edge e : n.getIsEndOf()) {
    			if(e instanceof IfTrueEdge | e instanceof DefaultEdge) {
    				hasEndOfEdges = true; 
    				break;
    			} 
    		}
    		
    		if(!hasEndOfEdges) {
    			rootNodes.add(n);
    		}
    	}
    }
	
	protected void encounterNode(Node node) {
        seen.add(node);
        queue.add(node);    
	}
	
	private void encounterRootNode() {
		encounterNode(rootNode);
		rootNode = null;
	}
	
	
	private Node getNextRootNode() {
		return rootNodes.iterator().next();
	}
	
	private boolean hasNextRootNode() {
		Iterator<Node> i = rootNodes.iterator();
		while(i.hasNext()) {
			Node next = i.next();
			if(isSeenNode(next)) {
				i.remove();
			}
		}
		return !rootNodes.isEmpty();
	}

	/** 
	 * @return false if root nodes are used up, or queue is empty, true otherwise 
	 */
	@Override
	public boolean hasNext() {

		if (rootNode != null) {
			encounterRootNode();
		}

		if (queue.isEmpty()) {
			if (hasNextRootNode()) {
				Node n = getNextRootNode();
				encounterNode(n);
				return true;
			}

			// any other case, false
			return false;

		} else {
			return true;
		}
	}

	/**@return the end-of node for the edge e */
	private Node getIsEndOfNodeOfEdge(Edge e) {
		for(Node n : graph.getNodes()) {
			for(Edge ee : n.getIsEndOf()) {
				if(ee.getName().equals(e.getName())) {
					return n; 
				}
			}
		}
		
        throw new IllegalArgumentException("No end-of Node for edge " + e.getName());
	}

	/**@return the incoming edges of the node */
	private Set<Edge> getIsEndOfTrueEdgesOfNode(Node node) {
		Set<Edge> result = new HashSet<Edge>(); 
		for (Edge e: node.getIsEndOf()) {
			if (e instanceof IfFalseEdge) {
				continue; 
			} 
			result.add(e);
		}
    	return result;
	}


	/**@return the start-of node for the edge e */
	private Node getIsStartOfNodeOfEdge(Edge e) {
		for(Node n : graph.getNodes()) {
			for(Edge ee : n.getIsStartOf()) {
				if(ee.getName().equals(e.getName())) {
					return n; 
				}
			}
		}
		
        throw new IllegalArgumentException("No start-of Node for edge " + e.getName());
	}
	
	/** cannot fix seeen.contains(), so check it manually 
	 * graph cannot contain a node with a same name and with the same type */
	protected boolean isSeenNode(Node node) {
    	boolean isSeen = false;
    	for(Node n : seen) {
    		if(n.getClass().equals(node.getClass()) && 
    			n.getName().equals(node.getName())) {
    			isSeen = true;
    			break;
    		}
    	}
    	//System.out.println("unseen: " + node.getName() + " " + node.getClass());
    	return isSeen;
    }
	
	/**@return the to-nodes of node that has not been seen */
	private Set<Node> getUnseenPrerequisiteNodes(Node node) {
		Set<Edge> edges = getIsEndOfTrueEdgesOfNode(node);
    	Set<Node> unseen = new HashSet<Node>();
    	for(Edge e : edges) {
    		Node startOfNode = getIsStartOfNodeOfEdge(e);
            if(!isSeenNode(startOfNode)) {
            	unseen.add(startOfNode);
            }
    	}
    	return unseen;
    }
	 
	/** Backtrack the to-nodes of node for the nodes that have not been 
	 *  seen, if any. If none, return the node */
	private Node backtrackCheck(Node node) {
    	Set<Node> unseen = getUnseenPrerequisiteNodes(node);
    	if(unseen.isEmpty()) {
    		return node;
    	} else {
    		for(Node prerequisite : unseen) {
    			return backtrackCheck(prerequisite);
    		}
    	}
    	
    	// never here i guess
    	return null; 
    }
	
	/** Add the unseen next of nodes for node. For every next-of node 
	 *  check its to-nodes (prerequisite nodes) to see if they have been 
	 *  seen. If not, add the prerequisite node above (i.e. before getting the next 
	 *  learning object, is prerequisites should be known). */
	private void addUnseenNextOf(Node node) {
		for(Edge edge : node.getIsStartOf()) {
        	if(edge instanceof IfFalseEdge) {
        		continue;
        	}
        	
        	Node endOfNode = getIsEndOfNodeOfEdge(edge);
        	
        	if(!isSeenNode(endOfNode)) {
                // check if all prerequisites edges have been visited
            	// if true, proceed, else, backtrack to the appropriate v
            	Node backtrackNode = backtrackCheck(endOfNode);
            	if(backtrackNode != null) {
            		encounterNode(backtrackNode);
            	} else {
            		encounterNode(endOfNode);
            	}
            }
        }
    }
	
	/**
	 * @return the next node to be seen (i.e. the next learning object to be executed)
	 */
	@Override
	public Node next() {
		if (rootNode != null) {
            encounterRootNode();
        }

        if (hasNext()) {
            Node nextNode = queue.removeFirst();
            addUnseenNextOf(nextNode);
            return nextNode;
        } else {
            throw new NoSuchElementException();
        }
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
