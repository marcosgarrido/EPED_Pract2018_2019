package es.uned.lsi.eped.pract2018_2019;

public class prueba {
	
	public static void main (String[] args) {
		
		Value v1 = new ValueSeq("48");
		Value v2 = new ValueSeq("75");
		//v1.addValue(v2);
		System.out.println(v1.greater(v2));
		System.out.println(v1);
		System.out.println(v2);
	}

}
