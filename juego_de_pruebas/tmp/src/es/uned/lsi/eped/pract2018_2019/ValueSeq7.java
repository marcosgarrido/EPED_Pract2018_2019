package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;

public class ValueSeq7 extends Value {
	
	private StackIF<Integer> value;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq7(String s) {
		
		value = new Stack<Integer>();
		for (int i = 0 ; i < s.length() ; i++) {
			int n = Character.getNumericValue(s.charAt(i));
			if (n != 0 || !value.isEmpty()) value.push(n);
		}
		if (value.isEmpty()) value.push(0);
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		
		return toStringAux(this).toString();
	}

	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		
		this.value = addSubValue(this,(ValueSeq7) n,true,0).value;
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		
		this.value = addSubValue(this,(ValueSeq7) n,false,0).value;
		if (this.value.isEmpty()) this.value.push(0);
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		
		this.value = addSubValue((ValueSeq7) n,this,false,0).value;
		if (value.isEmpty()) value.push(0);
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
		
		//mult(this,(ValueSeq) n);
		
		this.value = multValueAux(this,(ValueSeq7) n,0).value;
		if (value.isEmpty()) value.push(0);
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		
		ValueSeq7 v2 = ((ValueSeq7) n);
		if (this.value.size() > v2.value.size()) return true;
		if (this.value.size() < v2.value.size()) return false;
		return greaterAux(this,v2);
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		
		return this.toString().equals("0");
	}
	
	private StringBuffer toStringAux (ValueSeq7 v) {
		
		if (v.value.isEmpty()) return new StringBuffer();
		int top = v.value.getTop();
		v.value.pop();
		StringBuffer buff = toStringAux(v).append(top);
		v.value.push(top);
		return buff;
	}
	
	private boolean greaterAux (ValueSeq7 v1, ValueSeq7 v2) {
		
		if (v1.value.isEmpty()) return false;
		int v1top = v1.value.getTop();
		int v2top = v2.value.getTop();
		if (v1top > v2top) return true;
		v1.value.pop();
		v2.value.pop();
		boolean greater = greaterAux(v1,v2);
		v1.value.push(v1top);
		v2.value.push(v2top);
		return greater;
	}
		
	private ValueSeq7 addSubValue (ValueSeq7 v1, ValueSeq7 v2, boolean add, int ac) {
		
		ValueSeq7 result = new ValueSeq7("0");
		result.value.pop();
		
		if (v1.value.isEmpty() && v2.value.isEmpty()) {
			if (add && ac > 0) result.value.push(ac);
			return result;
		}
		int o1 = 0;
		int o2 = 0;
		int res = 0;
		boolean pop1 = false;
		boolean pop2 = false;
		if (!v1.value.isEmpty()) {
			o1 = v1.value.getTop();
			v1.value.pop();
			pop1 = true;
		}
		if (!v2.value.isEmpty()) {
			o2 = v2.value.getTop();
			v2.value.pop();
			pop2 = true;
		}
		if (add) {
			res = (o1+o2+ac)%10;
			ac = (o1+o2+ac)/10;
			result = addSubValue(v1,v2,true,ac);
		} else {
			int j = o1;
			if (o1 < (o2+ac)) j += 10;		
			result = addSubValue(v1,v2,false,j/10);
			res = j-(o2+ac);
		}
		if (pop1) v1.value.push(o1);
		if (pop2) v2.value.push(o2);
		if (res != 0 || !result.value.isEmpty()) result.value.push(res);
		
		return result;		
	}
	
	private ValueSeq7 multValueAux (ValueSeq7 v1, ValueSeq7 v2, int n) {
		
		ValueSeq7 result = null;
		ValueSeq7 pResult = null;
		
		if (v2.value.isEmpty()) return new ValueSeq7("0");
		int o2 = v2.value.getTop();
		v2.value.pop();
		pResult = multValueByDigit(v1,o2,0);
		for (int i  = 0 ; i < n ; i++ ) pResult.value.push(0);
		result = multValueAux(v1,v2,n+1);;
		result = addSubValue(result,pResult,true,0);
		v2.value.push(o2);		
		
		return result;
	}
	
	private ValueSeq7 multValueByDigit (ValueSeq7 v1, int o2, int ac) {
		
		ValueSeq7 result = new ValueSeq7("0");
		
		if (v1.value.isEmpty()) {
			if (ac != 0) result.value.push(ac);
			return result;
		}
		int n = 0;
		int o1 = v1.value.getTop();
		v1.value.pop();
		n = ((o1*o2)+ac)%10;
		ac = ((o1*o2)+ac)/10;
		result = multValueByDigit(v1,o2,ac);
		v1.value.push(o1);
		if (n != 0 || !result.value.isEmpty()) result.value.push(n);
		
		return result;
	}
	
	private void mult (ValueSeq7 v1, ValueSeq7 v2) { 
		
		ValueSeq7 result = new ValueSeq7("0");
		ValueSeq7 one = new ValueSeq7("1");
		ValueSeq7 cont = new ValueSeq7(v2.toString());
		
		while (!cont.isZero()) {
			result.addValue(v1);
			cont.subValue(one);
		}
		this.value = result.value;
	}
}