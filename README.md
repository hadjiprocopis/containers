# containers

implements an n-dimensional sparse or "full" array backed on a
1D flat array.

In order to create a full array on N-dimensions do:

import ahp.org.Containers.*;

public  class TestFlatArray {
        public static void main(String args[]) throws Exception {
                FlatArray<Shitc2> far = new FlatArray<Shitc2>(
                        "a flat array",
			// 4 dimensions, 10,20,30,40 bins in each dim respectively
                        new int[]{10,20,30,40},
			// this is what the array will contain:
                        Shitc2.class
                );

                Shitc2 toput = new Shitc2("aa", new int[]{1,2,3,4});
                far.put(
			// data to store in bin:
                        toput,
			// bin coordinates:
                        1, 2, 3, 4
                );
                System.out.println("put: "+toput);

                Shitc2 ash = far.get(1, 2, 3, 4);
                if( ash == null ){ throw new Exception("TestFlatArray.java : failed to get shit."); }
                System.out.println("got: "+ash);
        }
}

class Shitc2 {
        String name;
        int v[];
        public  Shitc2(String n, int av[]){ this.name = n; this.v = av; }
        public  String  toString(){ return "'"+name+"' : "+Arrays.toString(v); }
}

