package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class ValueSeq5 extends Value {
	
	private StackIF<Integer> value;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq5(String s) {
		
		value = new Stack<Integer>();
		for (int i = 0 ; i < s.length() ; i++) {
			int n = Character.getNumericValue(s.charAt(i));
			if (n != 0 || !value.isEmpty()) value.push(n);
		}
		if (value.isEmpty()) value.push(0);
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		
		return toStringAux(value).toString();
	}

	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq5) n).getValue();
		value = addSubValue(value,value2,true,0);
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq5) n).getValue();
		value = addSubValue(value,value2,false,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq5) n).getValue();
		value = addSubValue(value2,value,false,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq5) n).getValue();
		value = multValueAux(value,value2,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq5) n).getValue();
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
	
	private StringBuffer toStringAux (StackIF<Integer> s) {
		
		if (s.isEmpty()) return new StringBuffer();
		int first = s.getTop();
		s.pop();
		StringBuffer buff = toStringAux(s);
		buff.append(first);
		s.push(first);
		return buff;
	}
	
	private boolean greaterAux (StackIF<Integer> s1, StackIF<Integer> s2) {
		
		if (s1.isEmpty()) return false;
		int firstQ1 = s1.getTop();
		int firstQ2 = s2.getTop();
		if (firstQ1 > firstQ2) return true;
		s1.pop();
		s2.pop();
		boolean greater = greaterAux(s1,s2);
		s1.push(firstQ1);
		s2.push(firstQ2);
		return greater;
	}
		
	private StackIF<Integer> addSubValue (StackIF<Integer> s1, StackIF<Integer> s2, boolean add, int ac) {
		
		StackIF<Integer> result = null;
		
		if (s1.isEmpty() && s2.isEmpty()) return new Stack<Integer>();
		int o1 = 0;
		int o2 = 0;
		int res = 0;
		boolean pop1 = false;
		boolean pop2 = false;
		if (!s1.isEmpty()) {
			o1 = s1.getTop();
			s1.pop();
			pop1 = true;
		}
		if (!s2.isEmpty()) {
			o2 = s2.getTop();
			s2.pop();
			pop2 = true;
		}
		if (add) {
			ac = (o1+o2+ac)/10;
			res = (o1+o2+ac)%10;
			result = addSubValue(s1,s2,true,ac);
		}
		if (!add) {
			int j = o1;
			if (o1 < (o2+ac)) j += 10;		
			result = addSubValue(s1,s2,false,j/10);
			res = j-(o2+ac);
		}
		if (pop1) s1.push(o1);
		if (pop2) s2.push(o2);
		if (res != 0 || !result.isEmpty()) result.push(res);
		
		return result;		
	}
	
	private StackIF<Integer> multValueAux (StackIF<Integer> s1, StackIF<Integer> s2, int i) {
		
		StackIF<Integer> result = null;
		StackIF<Integer> pResult = null;
		
		if (s2.isEmpty()) return new Stack<Integer>();
		int o2 = s2.getTop();
		s2.pop();
		pResult = multValueAux2(s1,o2,0);
		pResult.push(i);
		i = i*10;
		System.out.println(result);
		result = addSubValue(multValueAux(s1,s2,i),pResult,true,0);
		
		return result;
	}
	
	public void a(int o2) {
		
		value = multValueAux2(value,o2,0);
	}
	
	private StackIF<Integer> multValueAux2 (StackIF<Integer> s1, int o2, int ac) {
		
		StackIF<Integer> result = new Stack<Integer>();
		
		if (s1.isEmpty()) {
			if (ac != 0) result.push(ac);
			return result;
		}
		int n = 0;
		int o1 = s1.getTop();
		s1.pop();
		n = ((o1*o2)+ac)%10;
		ac = ((o1*o2)+ac)/10;
		result = multValueAux2(s1,o2,ac);
		s1.push(o1);
		if (n != 0 || !result.isEmpty()) result.push(n);
		
		return result;
	}
}
