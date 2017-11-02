# containers

implements an n-dimensional sparse or "full" array backed on a
1D flat array.

compile using
```
ant clean && ant
```
run tests
```
ant test
```
What can I say but "a lovely piece of code for the N-dimensional hacker - where N large".

In order to create a sparse array on N-dimensions do:
```
import ahp.org.Containers.*;

public  class TestSparseArray {
	public static void main(String args[]){
		SparseArray<InsertData1> spa = new SparseArray<InsertData1>(
			"a sparse array",
			new int[]{10, 10},
			InsertData1.class
		);
		int     i, j;
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				spa.put(
					new InsertData1("f"+i+","+j, i*10+j),
					i, j
				);
			}
		}
		System.out.println(spa);
	}
}
public class   InsertData1 {
	String name;
	int v;
	public  InsertData1(String n, int av){ this.name = n; this.v = av; }
	public  String  toString(){ return "'"+name+"' : "+v; }
}
```

In order to create a full array on N-dimensions do:

```
import ahp.org.Containers.*;

public  class TestFlatArray {
	public static void main(String args[]) throws Exception {
		FlatArray<InsertData> far = new FlatArray<InsertData>(
			"a flat array",
			// 4 dimensions, 10,20,30,40 bins in each dim respectively
			new int[]{10,20,30,40},
			// this is what the array will contain:
			InsertData.class
		);

		InsertData toput = new InsertData("aa", new int[]{1,2,3,4});
		far.put(
			// data to store in bin:
			toput,
			// bin coordinates:
			1, 2, 3, 4
		);
		System.out.println("put: "+toput);

		InsertData ash = far.get(1, 2, 3, 4);
		if( ash == null ){ throw new Exception("TestFlatArray.java : failed to get shit."); }
		System.out.println("got: "+ash);
	}
}

class InsertData {
	String name;
	int v[];
	public  InsertData(String n, int av[]){ this.name = n; this.v = av; }
	public  String  toString(){ return "'"+name+"' : "+Arrays.toString(v); }
}
``` 

author: andreas hadjiprocopis (andreashad2@gmail.com)
