package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.BTreeIF;
import es.uned.lsi.eped.DataStructures.Stack;

public class StackMachine {
	
	private Stack<Operand> stack;
	
	public StackMachine() {
		
		stack = new Stack<Operand>();
	}
	
	public Operand execute(SynTree syn) {
		
		syn.toString();
		processSynTreeNodes(syn.getSynTree());
		return (stack.getTop());
	}
	
	private void processSynTreeNodes(BTreeIF<Node> syntree) {
		
		if (!syntree.isEmpty()) {
			BTreeIF<Node> leftChild = syntree.getLeftChild();
			BTreeIF<Node> rightChild = syntree.getRightChild();
			Node root = syntree.getRoot();
			if (leftChild != null) processSynTreeNodes(leftChild);
			if (rightChild != null) processSynTreeNodes(rightChild);
			if (root.getNodeType() == Node.NodeType.OPERAND) {
				Operand operand = (Operand) root;
				stack.push(operand);
			} else {
				Operator operator = (Operator) root;
				Operand operand = stack.getTop();
				stack.pop();
				switch (operator.getOperatorType()) {
				case ADD:
					stack.getTop().add(operand);
					break;
				case SUB:
					stack.getTop().sub(operand);
					break;
				case MULT:
					stack.getTop().mult(operand);
				default:
					break;
				}
			}
		}
	}
}