package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class ValueSeq extends Value {

	private StackIF<Integer> value;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq(String s) {

		value = new Stack<>();
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
		
		StackIF<Integer> value2 = ((ValueSeq) n).value;
		value = addSubValue(value,value2,true,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq) n).value;
		value = addSubValue(value,value2,false,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq) n).value;
		value = addSubValue(value2,value,false,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq) n).value;
		value = multValueAux(value,value2,0);
		if (value.isEmpty()) value.push(0);
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		
		StackIF<Integer> value2 = ((ValueSeq) n).value;
		if (value.size() > value2.size()) return true;
		if (value.size() < value2.size()) return false;
		return comparateValue(value,value2) == 1;
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		
		return this.toString().equals("0");
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
	
	private int comparateValue (StackIF<Integer> s1, StackIF<Integer> s2) {
		
		int n = 0;
		if (s1.isEmpty()) return n;
		int s1top = s1.getTop();
		int s2top = s2.getTop();
		s1.pop();
		s2.pop();
		n = comparateValue(s1,s2);
		if (n == 0) {
			if (s1top > s2top) n = 1;
			if (s1top < s2top) n = -1;
		}
		s1.push(s1top);
		s2.push((s2top));
		return n;
	}
		
	private StackIF<Integer> addSubValue (StackIF<Integer> s1, StackIF<Integer> s2, boolean add, int ac) {

		StackIF<Integer> result = new Stack<>();

		if (s1.isEmpty() && s2.isEmpty()) {
			if (ac > 0 && add) result.push(ac);
			return result;
		}
		int o1 = 0;
		int o2 = 0;
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
		int res;
		if (add) {
			res = (o1+o2+ac)%10;
			ac = (o1+o2+ac)/10;
			result = addSubValue(s1,s2,true,ac);
		} else {
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
	
	private StackIF<Integer> multValueAux(StackIF<Integer> s1, StackIF<Integer> s2, int n) {

		if (s2.isEmpty()) return new Stack<>();
		int o2 = s2.getTop();
		s2.pop();
		StackIF<Integer> pResult = multValueByDigit(s1, o2, 0);
		for (int i  = 0 ; i < n ; i++ ) pResult.push(0);
		StackIF<Integer> result = multValueAux(s1, s2,  n + 1);
		result = addSubValue(result,pResult,true,0);
		s2.push(o2);		
		
		return result;
	}
	
	private StackIF<Integer> multValueByDigit (StackIF<Integer> s1, int o2, int ac) {

		StackIF<Integer> result = new Stack<>();

		if (s1.isEmpty()) {
			if (ac != 0) result.push(ac);
			return result;
		}
		int o1 = s1.getTop();
		s1.pop();
		int n = ((o1*o2)+ac)%10;
		ac = ((o1*o2)+ac)/10;
		result = multValueByDigit(s1,o2,ac);
		s1.push(o1);
		if (n != 0 || !result.isEmpty()) result.push(n);
		
		return result;
	}

	/* Método alternativo a multValue propuesto por el ED, el orden de complejidad
	 * es el mismo que el método que se ha usado pero el tamaño del problema en este
	 * caso es muchísimo mayor. En el método anterior el tamaño del problema es el
	 * número de dígitos que tiene el número, y en este método el tamaño del problema
	 * es el valor en sí.
	public void multValue(Value n) {

		ValueSeq result = new ValueSeq("0");
		ValueSeq one = new ValueSeq("1");
		ValueSeq cont = new ValueSeq(n.toString());

		while (!cont.isZero()) {
			result.addValue(this);
			cont.subValue(one);
			System.out.println(cont);
		}
	}	*/
}