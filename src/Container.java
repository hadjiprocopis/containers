package	ahp.org.Containers;

import	java.util.HashMap;
import	java.util.Arrays;
import	java.util.Iterator;
import	java.util.ArrayList;

import	java.io.Serializable;

import	ahp.org.Cartesians.Cartesian;

// this class IS NOT thread-safe!
// it implements an N-dim array into a 1D (flat) array. It is not sparse
// it does this by converting the N-dim coordinate of an array cell into an 1-dim (linear) index
public	abstract class	Container<T> implements Serializable {
	private Class<T>	myTType;

	// are we sparse or "full" container?
	protected	boolean		myIsSparse = false;
	protected	String		myName; // a name for this structure
	protected	int		myNumDims;
	protected	int		mySizePerDim[];

	// Sparseness is the ratio of zero-count bins over total bins (0-1)
	protected	double		mySparseness = 0.0;
	// the max number of items we can store which is N1 * N2 * ...
	// Ni being the size in dimension i
	protected	int		myCapacity;

	// flag indicating that we need to call recalculate()
	// because the map was altered
	protected	boolean		myNeedRecalculate = false;

	public	Container(
		String	aname,
		int	size_over_each_dim[],
		boolean	is_sparse, // is this sparse container?
		Class<T>	shit
	){
		this.myTType = shit;
		this.myName = aname;
		this.myIsSparse = is_sparse;
	}
	public	boolean	sparse(){ return this.myIsSparse; }
	public	Class<T> getType(){ return this.myTType; }
	protected	void	alloc(int size_over_each_dim[]){
		System.err.println("Container.java : alloc() : this needs to be implemented.");
		System.exit(1);
	}
	public	Iterator<T>	iterator(){
		System.err.println("Container.java : get()/1 : this needs to be implemented.");
		System.exit(1);
		return null;
	}
	public	T get(int ... acoordinates){
		System.err.println("Container.java : get()/1 : this needs to be implemented.");
		System.exit(1);
		return null;
	}
	// given a set of coordinates create a Flat index (1D)
	// which is used as an index to storing things in here
	public	int	coordinates2linear_index(int ... acoordinates){
		System.err.println("Container.java : linear_index()/1 : this needs to be implemented.");
		System.exit(1);
		return -1;
	}
	// Given a linear index, decompose it into its coordinates:
	// thread-safety problem here because the coordinates (or the de-linearised index)
	// which is an int[] is stored in this.myDelinearIndexReturn[]
	// in order to be accessed by the caller rather than allocating an array of int
	// every time this function is called...
	// I guess this is the fastest of the two versions, an array already been
	// allocated internally of the object is used to write the results
	public	int[]	linear_index2coordinates(int alinearindex){
		System.err.println("Container.java : get()/2 : this needs to be implemented.");
		System.exit(1);
		return null;
	}
	// for flexibility we provide this where an array is passed-by-reference
	// and results are stored in it.
	public	void	linear_index2coordinates(int alinearindex, int ret[]){
		System.err.println("Container.java : get()/2 : this needs to be implemented.");
		System.exit(1);
	}
	public	T get_with_linear_index(int a_linear_index){
		System.err.println("Container.java : get()/2 : this needs to be implemented.");
		System.exit(1);
		return null;
	}
	// put returns the linear index we were assigned to
	// this can be the index in an array (if the container subclass is array-based)
	// or a hashkey.
	public	int put(
		T data_to_add,
		int ... acoordinates
	){
		System.err.println("Container.java : put()/1 : this needs to be implemented.");
		System.exit(1);
		return -1;
	}
	public	int put_with_linear_index(
		T data_to_add,
		int a_linear_index
	){
		System.err.println("Container.java : put_with_linear_index()/1 : this needs to be implemented.");
		System.exit(1);
		return -1;
	}
	public	boolean	contains(int ... acoordinates){
		System.err.println("Container.java : contains()/1 : this needs to be implemented.");
		System.exit(1);
		return false;
	}
	public	boolean	contains_with_linear_index(int a_linear_index){
		System.err.println("Container.java : contains_with_linear_index()/1 : this needs to be implemented.");
		System.exit(1);
		return false;
	}
	// returns non-null contents
	public	int	size(){
		System.err.println("Container.java : size()/1 : this needs to be implemented.");
		System.exit(1);
		return -1;
	}
	public	int	capacity(){
		System.err.println("Container.java : capacity()/1 : this needs to be implemented.");
		System.exit(1);
		return -1;
	}
	public	double	sparseness(){ this.recalculate(false); return this.mySparseness; }
	protected	void	recalculate(boolean force){
		System.err.println("Container.java : recalculate()/1 : this needs to be implemented.");
		System.exit(1);
	}
	public	String	toStringInfo(){
		this.recalculate(false);
		return new String(this.myName
			+" : size="+this.size()
			+", capacity="+String.format("%E", (double )this.myCapacity)
			+", sparseness="+this.mySparseness
		);
	}
}
