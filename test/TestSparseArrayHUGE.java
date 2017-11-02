import ahp.org.Containers.*;

import	java.util.Random;

public	class TestSparseArrayHUGE {
	public static void main(String args[]) throws Exception {
		long	time_started = System.nanoTime();
		int DIMS[] = new int[]{500, 500, 500, 500, 500, 500};
		int NUM_ITEMS_TO_INSERT = 100000;
		int mySeed = 1234;
		SparseArray<InsertDataHUGE> spa = new SparseArray<InsertDataHUGE>(
			"a sparse array",
			DIMS,
			InsertDataHUGE.class
		);

		int	num_dims = DIMS.length;
		java.util.Random myRNG = new java.util.Random(mySeed);

		int	acoord[] = new int[DIMS.length];
		int	num_added = 0, i, j;
		for(i=NUM_ITEMS_TO_INSERT;i-->0;){
			for(j=num_dims;j-->0;){ acoord[j] = myRNG.nextInt(DIMS[j]); }
			if( ! spa.contains(acoord) ){ num_added++; }
			InsertDataHUGE ObjToPut = new InsertDataHUGE(
				"shit:"+java.util.Arrays.toString(acoord),
				acoord
			);
			spa.put(ObjToPut, acoord);
		}
		System.out.println("made "+num_added+" insertions onto non-occupied cells, size of array is "+spa.size());
		System.out.println("Sparse matrix is:");
		System.out.println(spa.toStringInfo());
		System.out.println("Done in "+((System.nanoTime()-time_started)/1000)+" milliseconds.");
	}
}
class	InsertDataHUGE {
	String name;
	int v[];
	public	InsertDataHUGE(String n, int av[]){ this.name = n; this.v = av; }
	public	String	toString(){ return "'"+name+"' : "+java.util.Arrays.toString(v); }
}

