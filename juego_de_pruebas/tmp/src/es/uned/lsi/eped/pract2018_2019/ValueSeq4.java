package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class ValueSeq4 extends Value {
	
	private StackIF<Integer> value;
	private StackIF<Integer> valueC9;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq4(String s) {
		
		value = new Stack<Integer>();
		valueC9 = new Stack<Integer>();
		for (int i = 0 ; i < s.length() ; i++) {
			int n = Character.getNumericValue(s.charAt(i));
			value.push(n);
			if (i == s.length()-1) valueC9.push(10-n);
			if (i < s.length()-1) valueC9.push(9-n);
		}
		removeHeadZeros(value);
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		
		return toStringAux(value).toString();
	}

	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq4) n).getValue();
		value = addValueAux(value,value2,0);
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq4) n).getValueC9();
		value = addValueAux(value,value2,0);
		System.out.println(value);
		removeHeadZeros(value);
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq4) n).getValue();
		if (value.size() > value2.size()) return true;
		if (value.size() < value2.size()) return false;
		return greaterAux(value, value2);
		
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		
		return value.toString().equals("0");
	}
	
	public StackIF<Integer> getValue() {
		
		return value;
	}
	
	public StackIF<Integer> getValueC9() {
		
		return valueC9;
	}
	
	private StringBuffer toStringAux (StackIF<Integer> q) {
		
		if (q.isEmpty()) return new StringBuffer();
		int first = q.getTop();
		q.pop();
		StringBuffer buff = toStringAux(q);
		buff.append(first);
		q.push(first);
		return buff;
	}
	
	private boolean greaterAux (StackIF<Integer> q1, StackIF<Integer> q2) {
		
		if (q1.isEmpty()) return false;
		int firstQ1 = q1.getTop();
		int firstQ2 = q2.getTop();
		if (firstQ1 > firstQ2) return true;
		q1.pop();
		q2.pop();
		boolean greater = greaterAux(q1,q2);
		q1.push(firstQ1);
		q2.push(firstQ2);
		return greater;
	}
	
	private StackIF<Integer> addValueAux (StackIF<Integer> q1, StackIF<Integer> q2, int ac) {
		
		StackIF<Integer> result = new Stack<Integer>();
		
		if (q1.isEmpty() && q2.isEmpty()) {
			if (ac == 1) result.push(ac);
			return result;
		}
		int o1, o2;
		if (!q1.isEmpty() && q2.isEmpty()) {
			o1 = q1.getTop();
			o2 = 0;
			q1.pop();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q1.push(o1);
			result.push((o1+o2+ac)%10);
		}
		if (q1.isEmpty() && !q2.isEmpty()) {
			o1 = 0;
			o2 = q2.getTop();
			q2.pop();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q2.push(o2);
			result.push((o1+o2+ac)%10);
		}
		if (!q1.isEmpty() && !q2.isEmpty()) {
			o1 = q1.getTop();
			o2 = q2.getTop();
			q1.pop();
			q2.pop();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q1.push(o1);
			q2.push(o2);;
			result.push((o1+o2+ac)%10);
		}
		return result;
		
	}
	
	private StackIF<Integer> subValueAux (StackIF<Integer> q1, StackIF<Integer> q2, int ac) {
		   
		StackIF<Integer> result = new Stack<Integer>();
		
		if (q1.isEmpty() && q2.isEmpty()) return result;
		int o1, o2;
		if (!q1.isEmpty() && q2.isEmpty()) {
			o1 = q1.getTop();
			o2 = 0;
			q1.pop();
			int j = o1;
			if (o1 < (o2+ac)) j += 10;
			result = subValueAux(q1,q2,j/10);
			q1.push(o1);
			result.push(j-(o2+ac));
		}
		if (q1.isEmpty() && !q2.isEmpty()) {
			o1 = 0;
			o2 = q2.getTop();
			q2.pop();
			int j = o1;
			if (o1 < (o2+ac)) j += 10;
			result = subValueAux(q1,q2,j/10);
			q2.push(o2);
			result.push(j-(o2+ac));
		}
		if (!q1.isEmpty() && !q2.isEmpty()) {
			o1 = q1.getTop();
			o2 = q2.getTop();
			q1.pop();
			q2.pop();
			int j = o1;
			if (o1 < (o2+ac)) j += 10;
			result = subValueAux(q1,q2,j/10);
			q1.push(o1);
			q2.push(o2);;
			result.push(j-(o2+ac));
		}
		return result;
	}
	
	private void fillToLength (StackIF<Integer> value, int n) {
		
		int numZeros = n - value.size();
		while (numZeros > 0) {
			value.push(0);
			numZeros--;
		}
	}
	
	private void removeHeadZeros (StackIF<Integer> value) {
		removeHeadZerosAux(value);
		if (value.isEmpty()) value.push(0);
	}
	
	private void removeHeadZerosAux (StackIF<Integer> value) {
		
		if (!value.isEmpty()) {
			int first = value.getTop();
			value.pop();
			removeHeadZerosAux(value);
			if (first != 0 || !value.isEmpty()) value.push(first);
			
		}
	}
}
