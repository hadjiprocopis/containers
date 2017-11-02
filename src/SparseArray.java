package	ahp.org.Containers;

import	java.util.HashMap;
import	java.util.Arrays;
import	java.util.Iterator;
import	java.util.ArrayList;

import  java.io.Serializable;

import	ahp.org.Cartesians.Cartesian;

// this class IS NOT thread-safe!
public	class	SparseArray<T> extends ahp.org.Containers.Container<T> implements Serializable {
	private static final long serialVersionUID = 1797271487L+4L;

	private	ArrayList<T>	myContent;
	// a SparseArrayIndex,Integer contains the 'coordinates' for an N-dim array
	// however here we store things in a flat (1D) array with a 'linear index'
	// which has nothing to do with the coordinates. It depends on the insertion order
	// first object in the container gets a linear index of 0, independently of its coordinates
	// this map gives us that index given 'coordinates'
	// then this index can be used to read the object from myContent[]
	private	HashMap<SparseArrayIndex,Integer>	mySparseArrayIndex2LinearIndex;
	private	HashMap<Integer, SparseArrayIndex>	myLinearIndex2SparseArrayIndex;

	// we use this as an internal variable in order to avoid
	// creating a SparseArrayIndex object every time we need to
	// lookup a content. This voids thread-safety... but is fast
	private	SparseArrayIndex mySparseArrayIndexTemp;

	public	SparseArray(
		String	aname,
		int	size_over_each_dim[],
		Class<T> shit
	){
		super(
			aname,
			size_over_each_dim,
			true, // is this sparse container?
			shit  // the class type of each content (the T) - java gone mad
		);
		this.alloc(size_over_each_dim);
	}
	protected	void	alloc(int size_over_each_dim[]){
		this.mySparseArrayIndex2LinearIndex = new HashMap<SparseArrayIndex,Integer>();
		this.myLinearIndex2SparseArrayIndex = new HashMap<Integer,SparseArrayIndex>();
		this.myContent = new ArrayList<T>();
		this.myNumDims = size_over_each_dim.length;
		this.mySizePerDim = new int[this.myNumDims];
		int	i;
		for(i=this.myNumDims;i-->0;){ this.mySizePerDim[i] = size_over_each_dim[i]; }
		this.mySparseArrayIndexTemp = new SparseArrayIndex(this.myNumDims);
		this.myCapacity = 1;
		for(i=this.mySizePerDim.length;i-->0;){
			this.myCapacity *= this.mySizePerDim[i];
		}
	}
	public	Iterator<T>	iterator(){ return this.myContent.iterator(); }
	public	int	coordinates2linear_index(int ... acoordinates){
		// given coordinates i,j,k,... we give the index for the 1-dim array
		this.mySparseArrayIndexTemp.set(acoordinates);
		// get a linear index based on coordinates
		Integer anindex = this.mySparseArrayIndex2LinearIndex.get(
			this.mySparseArrayIndexTemp
		);
		if( anindex == null ){ System.err.println("SparseArray.java : coordinates2linear_index() : failed for coordinates: "+Arrays.toString(acoordinates)); System.exit(1); }
		return anindex.intValue();
	}
	// Given a linear index, decompose it into its coordinates:
	// thread-safety problem here because the coordinates (or the de-linearised index)
	// which is an int[] is stored in this.myLinearIndex2CoordinatesReturn[]
	// in order to be accessed by the caller rather than allocating an array of int
	// every time this function is called...
	// I guess this is the fastest of the two versions, an array already been
	// allocated internally of the object is used to write the results
	public	int[]	linear_index2coordinates(int alinearindex){
		SparseArrayIndex aSPI = this.myLinearIndex2SparseArrayIndex.get(alinearindex);
		if( aSPI == null ){
			System.err.println("SparseArray.java : linear_index2coordinates()/1 : failed for index : "+alinearindex+" ... exiting, you must fix this");
			System.out.println("this.myLinearIndex2SparseArrayIndex: "+this.myLinearIndex2SparseArrayIndex);
			System.exit(1);
		}
		return aSPI.coordinates();
	}
	// for flexibility we provide this where an array is passed-by-reference
	// and results are stored in it.
	public	void	linear_index2coordinates(int alinearindex, int ret[]){
		ret = this.linear_index2coordinates(alinearindex);
	}
	public	T get(int ... acoordinates){
		this.mySparseArrayIndexTemp.set(acoordinates);
		// get a linear index based on coordinates
		Integer anindex = this.mySparseArrayIndex2LinearIndex.get(
			this.mySparseArrayIndexTemp
		);
		if( anindex == null ){ return null; }

		return this.myContent.get(anindex);
	}
	public	T get_with_linear_index(int anindex){
		if( anindex < this.myContent.size() ){
			return this.myContent.get(anindex);
		}
		return null;
	}
	// returns the linear index. ie the index in our sparse array
	public	int put(
		T data_to_add,
		int ... acoordinates
	){
		this.myContent.add(data_to_add);
		SparseArrayIndex	aSPI = new SparseArrayIndex(acoordinates);
		int my_linear_index_i_was_assigned = this.myContent.size() - 1;
		Integer	desitnation_in_our_contents_array = new Integer(my_linear_index_i_was_assigned);
		this.mySparseArrayIndex2LinearIndex.put(aSPI, desitnation_in_our_contents_array);
		this.myLinearIndex2SparseArrayIndex.put(desitnation_in_our_contents_array, aSPI);
		this.myNeedRecalculate = true;
		return my_linear_index_i_was_assigned;
	}
	public	int put_with_linear_index(
		T data_to_add,
		int a_linear_index
	){
		System.err.println("SparseArray.java : put_with_linear_index() : you should not be using this.");
		System.exit(1);
		return /*fucking*/ -1;
	}
	public	boolean	contains(int ... acoordinates){
		return this.get(acoordinates) != null;
	}
	public	boolean	contains_with_linear_index(int a_linear_index){
		return this.myContent.size() < a_linear_index ? false:true;
	}
	public	int	size(){ return this.myContent.size(); }
	public	int	capacity(){ return this.myCapacity; }
	public	double	sparseness(){ this.recalculate(false); return this.mySparseness; }
	protected	void	recalculate(boolean force){
		if( (force == false) && (this.myNeedRecalculate == false) ){ return; }
		// size() and capacity are longs, so force them to double
		this.mySparseness = 1.0 - 1.0 * this.size() / this.myCapacity;
		this.myNeedRecalculate = false;
	}
	public	String	toStringInfo(){
		this.recalculate(false);
		return new String(this.myName
			+" : size="+this.size()
			+", capacity="+String.format("%d", this.myCapacity)
			+", sparseness="+this.mySparseness
		);
	}
	public	String	toString(){
		this.recalculate(false);
		StringBuilder sb = new StringBuilder();
		sb.append(this.toStringInfo());
		int	L = this.myContent.size(), i;
		for(i=0;i<L;i++){
			sb.append("\n");
			sb.append(this.myContent.get(i).toString());
		}
		return sb.toString();
	}
}
