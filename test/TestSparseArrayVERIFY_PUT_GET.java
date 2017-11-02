import ahp.org.Containers.*;

import	java.util.Random;

public	class TestSparseArrayVERIFY_PUT_GET {
	public static void main(String args[]) throws Exception {
		SparseArray<InsertDataVERIFY_PUT_GET> spa = new SparseArray<InsertDataVERIFY_PUT_GET>(
			"a sparse array",
			new int[]{3,3,3,3,3,3}, // size per dim
			InsertDataVERIFY_PUT_GET.class
		);
		int i=0, j=2, k=0, l=1, m=2, n=1;
		int shit[] = new int[]{i, j, k, l, m, n};
		InsertDataVERIFY_PUT_GET theshit = new InsertDataVERIFY_PUT_GET(
			"shit:"+java.util.Arrays.toString(shit),
			shit
		);
		spa.put(
			theshit,
			i, j, k, l, m, n
		);
		InsertDataVERIFY_PUT_GET ashit = spa.get(0, 2, 0, 1, 2, 1);
		if( ashit == null ){
			throw new Exception("Could not get object just put in: "+theshit);
		}
		System.out.println(ashit);
	}
}
class	InsertDataVERIFY_PUT_GET {
	String name;
	int v[];
	public	InsertDataVERIFY_PUT_GET(String n, int av[]){ this.name = n; this.v = av; }
	public	String	toString(){ return "'"+name+"' : "+java.util.Arrays.toString(v); }
}
