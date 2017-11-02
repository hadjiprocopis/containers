import ahp.org.Containers.*;

public	class TestSparseArray {
	public static void main(String args[]){
		SparseArray<Shitc1> spa = new SparseArray<Shitc1>(
			"a sparse array",
			new int[]{10, 10},
			Shitc1.class
		);
		int	i, j;
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				spa.put(
					new Shitc1("f"+i+","+j, i*10+j),
					i, j
				);
			}
		}
		System.out.println(spa);
	}
}
