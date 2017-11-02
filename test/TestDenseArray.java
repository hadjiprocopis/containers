import ahp.org.Containers.*;

public	class TestDenseArray {
	public static void main(String args[]) throws Exception {
		DenseArray<InsertData2> far = new DenseArray<InsertData2>(
			"a flat array",
			new int[]{10,20,30,40},
			InsertData2.class
		);

		InsertData2 toput = new InsertData2("aa", new int[]{1,2,3,4});
		far.put(
			toput,
			1, 2, 3, 4
		);
		System.out.println("put: "+toput);

		InsertData2 ash = far.get(1, 2, 3, 4);
		if( ash == null ){ throw new Exception("TestDenseArray.java : failed to get shit."); }
		System.out.println("got: "+ash);
	}
}
