package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.List;
import es.uned.lsi.eped.DataStructures.ListIF;

public class ValueSeq2 extends Value {
	
	private ListIF<Integer> value;

	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq2(String s) {
		
		value = new List<Integer>();
		for (char c : s.toCharArray()) {
			int n = Character.getNumericValue(c);
			value.insert(1,n);
		}
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
	
		StringBuffer buff = new StringBuffer();
		for (int i = value.size() ; i > 0 ; i--) {
			if (buff.length() != 0 || value.get(i) != 0)
				buff.append(value.get(i));
		}
		if (buff.length() == 0) buff.append(0);
		return buff.toString();
	}

	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		
		ListIF<Integer> result = new List<Integer>();
		ListIF<Integer> value2 = ((ValueSeq2) n).getValue();
		int ac = 0;
		for (int pos = 1 ; pos <= Math.max(value.size(), value2.size()); pos++) {
			int o1 = 0;
			int o2 = 0;
			if (pos <= value.size()) o1 = value.get(pos);
			if (pos <= value2.size()) o2 = value2.get(pos);
			ac = ac + o1 + o2;
			result.insert(result.size()+1, ac%10);
			ac = ac/10;
		}
		if (ac == 1) result.insert(result.size()+1, ac);
		value = result;
		
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		ListIF<Integer> result = new List<Integer>();
		ListIF<Integer> value2 = ((ValueSeq2) n).getValue();
		for (int pos = 1 ; pos <= Math.max(value.size(), value2.size()); pos++) {
			int o1 = 0;
			int o2 = 0;
			if (pos <= value.size()) o1 = value.get(pos);
			if (pos <= value2.size()) o2 = value2.get(pos);
			if (pos == 1)
				result.insert(result.size()+1, (o1 + (10 - o2)) % 10);
			result.insert(result.size()+1, o1 + (9 - o2));
		}
		result.remove(result.size());
		value = result;
		
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		
		ListIF<Integer> result = new List<Integer>();
		ListIF<Integer> value2 = ((ValueSeq2) n).getValue();
		for (int pos = 1 ; pos <= Math.max(value.size(), value2.size()); pos++) {
			int o1 = 0;
			int o2 = 0;
			if (pos <= value.size()) o1 = value.get(pos);
			if (pos <= value2.size()) o2 = value2.get(pos);
			if (pos == 1)
				result.insert(result.size()+1, (o2 + (10 - o1)) % 10);
			result.insert(result.size()+1, o2 + (9 - o1));
		}
		result.remove(result.size());
		value = result;
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		
		ListIF<Integer> value2 = ((ValueSeq2) n).getValue();
		int s1 = value.size();
		int s2 = value2.size();
		if (s1 > s2) return true;
		if (s1 < s2) return false;
		while (s1 > 0) {
			if (value.get(s1) > value2.get(s1))
				return true;
			s1--;
		}
		return false;
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		
		return value.get(value.size()) == 0;
	}
	
	public ListIF<Integer> getValue() {
		return value;
	}
}
