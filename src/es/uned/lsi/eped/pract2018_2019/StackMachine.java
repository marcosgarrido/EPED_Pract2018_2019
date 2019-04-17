package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.BTreeIF;
import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class StackMachine {

	// Estructura que va almacenando los operandos del árbol sintáctico
	private StackIF<Operand> stack;
	
	public StackMachine() {
		
		stack = new Stack<>();
	}
	
	public Operand execute(SynTree syn) {

		processSynTreeNodes(syn.getSynTree());
		return stack.getTop();
	}

	/**
	 * Método que procesa los nodos del árbol binario asociado al árbol sintáctico
	 * de la expresión a calcular. El método recorre recursivamente el árbol binario,
	 * cuando se encuentra con un nodo de tipo Operand lo inserta en la estructura de
	 * pila de la clase, si se encuentra con un nodo de tipo Operator, opera en
	 * función del tipo del operador, los dos últimos operandos almacenados en la pila
	 * y deja el resultado en la cima de la pila. Cuando el método acaba de procesar
	 * todos los nodos, la pila almacenará en su pila el resultado de la expresión
	 * representada por el árbol sintáctico.
	 * @param syntree El árbol binario asociado al árbol sintáctico
	 */
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