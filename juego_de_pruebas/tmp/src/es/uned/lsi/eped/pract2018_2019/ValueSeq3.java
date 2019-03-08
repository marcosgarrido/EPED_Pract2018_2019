package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.Queue;
import es.uned.lsi.eped.DataStructures.QueueIF;

public class ValueSeq3 extends Value {
	
	private QueueIF<Integer> value;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq3(String s) {
		
		value = new Queue<Integer>();
		for (int i = s.length()-1 ; i >= 0 ; i--) {
			int n = Character.getNumericValue(s.charAt(i));
			value.enqueue(n);
		}
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		
		StringBuffer buff = toStringAux(value);
		if (buff.length() == 0) return "0";
		return buff.toString();
	}

	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		
		QueueIF<Integer> value2 = ((ValueSeq3) n).getValue();
		int count = value.size() - value2.size();
		while (count > 0) {
			value2.enqueue(0);
			count--;
		}
		System.out.println(value.size());
		System.out.println(value2.size());
		value = addValueAux(value,value2,0);
		
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		
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
		
		QueueIF<Integer> value2 = ((ValueSeq3) n).getValue();
		if (value.size() > value2.size()) return true;
		if (value.size() < value2.size()) return false;
		return greaterAux(value, value2);
		
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		
		return value.toString().equals("0");
	}
	
	public QueueIF<Integer> getValue() {
		
		return value;
	}
	
	private StringBuffer toStringAux (QueueIF<Integer> q) {
		
		if (q.isEmpty()) return new StringBuffer();
		int first = q.getFirst();
		q.dequeue();
		StringBuffer buff = toStringAux(q);
		if (buff.length() != 0 || first != 0) buff.append(first);
		q.enqueue(first);
		return buff;
	}
	
	private boolean greaterAux (QueueIF<Integer> q1, QueueIF<Integer> q2) {
		
		if (q1.isEmpty()) return false;
		int firstQ1 = q1.getFirst();
		int firstQ2 = q2.getFirst();
		if (firstQ1 > firstQ2) return true;
		q1.dequeue();
		q2.dequeue();
		boolean greater = greaterAux(q1,q2);
		q1.enqueue(firstQ1);
		q2.enqueue(firstQ2);
		return greater;
	}
	
	private QueueIF<Integer> addValueAux (QueueIF<Integer> q1, QueueIF<Integer> q2, int ac) {
		
		QueueIF<Integer> result = null;
		if (q1.isEmpty() && q2.isEmpty()) return new Queue<Integer>();
		int o1, o2;
		if (!q1.isEmpty() && q2.isEmpty()) {
			o1 = q1.getFirst();
			o2 = 0;
			q1.dequeue();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q1.enqueue(o1);
			result.enqueue((o1+o2+ac)%10);
		}
		if (q1.isEmpty() && !q2.isEmpty()) {
			o1 = 0;
			o2 = q2.getFirst();
			q2.dequeue();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q2.enqueue(o2);
			result.enqueue((o1+o2+ac)%10);
		}
		if (!q1.isEmpty() && !q2.isEmpty()) {
			o1 = q1.getFirst();
			o2 = q2.getFirst();
			q1.dequeue();
			q2.dequeue();
			result = addValueAux(q1,q2,(o1+o2+ac)/10);
			q1.enqueue(o1);
			q2.enqueue(o2);;
			result.enqueue((o1+o2+ac)%10);
		}
		return result;
		
	}
	
}
