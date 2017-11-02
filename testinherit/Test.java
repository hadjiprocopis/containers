import java.util.Vector;

public class Test {
	public static void main(String args[]){
		A ana = new B();
		B anb = new B();

		Vector<A> shit = new Vector<A>();
		
		shit.add(ana);
		shit.add(anb);

		((A )(shit.get(0))).shit();
		shit.get(1).shit();
	}
}
