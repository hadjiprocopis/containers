import ahp.org.Containers.*;

public	class TestFlatArray {
	public static void main(String args[]) throws Exception {
		FlatArray<Shitc2> far = new FlatArray<Shitc2>(
			"a flat array",
			new int[]{10,20,30,40},
			Shitc2.class
		);

		Shitc2 toput = new Shitc2("aa", new int[]{1,2,3,4});
		far.put(
			toput,
			1, 2, 3, 4
		);
		System.out.println("put: "+toput);

		Shitc2 ash = far.get(1, 2, 3, 4);
		if( ash == null ){ throw new Exception("TestFlatArray.java : failed to get shit."); }
		System.out.println("got: "+ash);
	}
}
