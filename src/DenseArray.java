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
public	class	DenseArray<T> extends ahp.org.Containers.Container<T> implements Serializable {
	private static final long serialVersionUID = 1797271487L+5L;

	private	boolean		debug = false;

	private	T		myContent[];

	// how many items we hold in the array (cells which are not null)
	private	int		myNumNonNullContents;

	// this set of numbers is the product of mySizePerDim[i]
	// it is used to map the N-array of bins into a 1D array (see linear_index())
	// this is not a problem for thread safety - it is allocated at creation time and never changes - only read
	private	int		myLinearIndexFuck[];

	// when we are asked to de-linearise an index (i.e. return coordinates)
	// we use this array to store the results and return them
	// this voids thread-safety but makes this faster.
	// this IS A PROBLEM to THREAD-SAFETY:
	private	int		myLinearIndex2CoordinatesReturn[];

	public	DenseArray(
		String	aname,
		int	size_over_each_dim[],
		Class<T> shit
	){
		super(
			aname,
			size_over_each_dim,
			false, // is this sparse container?
			shit // the class type of each content (the T) - java gone mad
		);
		this.alloc(size_over_each_dim);
	}
	protected	void	alloc(int size_over_each_dim[]){
		int	i, j;

		this.myNumDims = size_over_each_dim.length;

		this.myLinearIndex2CoordinatesReturn = new int[this.myNumDims];

		this.mySizePerDim = new int[this.myNumDims];
		for(i=this.myNumDims;i-->0;){
			this.mySizePerDim[i] = size_over_each_dim[i];
		}

		// this is the max number of items we can hold
		this.myCapacity = 1;
		for(i=this.mySizePerDim.length;i-->0;){
			this.myCapacity *= this.mySizePerDim[i];
		}
		// this is the num of items we have stored in array (i.e. cells which are not null)
		this.myNumNonNullContents = 0;

		this.myContent = this.create_fucking_generics_array(this.myCapacity);

		// linear-index shit:
		int fuck;
		this.myLinearIndexFuck = new int[this.myNumDims];
		for(i=0;i<this.myNumDims;i++){
			fuck = 1;
			for(j=i+1;j<this.myNumDims;j++){ fuck *= this.mySizePerDim[j]; }
			this.myLinearIndexFuck[i] = fuck;
		}
		this.myLinearIndex2CoordinatesReturn = new int[this.myNumDims];
	}
	public	int	coordinates2linear_index(int ... acoordinates){
		// given coordinates i,j,k,... we give the index for the 1-dim array
		int     index = 0, i;
		for(i=this.myNumDims;i-->0;){
			index += acoordinates[i] * this.myLinearIndexFuck[i];
		}
		return index;
	}
	// Given a linear index, decompose it into its coordinates:
	// thread-safety problem here because the coordinates (or the de-linearised index)
	// which is an int[] is stored in this.myLinearIndex2CoordinatesReturn[]
	// in order to be accessed by the caller rather than allocating an array of int
	// every time this function is called...
	// I guess this is the fastest of the two versions, an array already been
	// allocated internally of the object is used to write the results
	public int[]	linear_index2coordinates(int alinearindex){
		// given an index in a 1D array which was composed from an N-dim array
		// return the array of the coordinates of the cell pointed by the specified index
		for(int i=0;i<this.myNumDims;i++){
			this.myLinearIndex2CoordinatesReturn[i] = alinearindex / this.myLinearIndexFuck[i];
			alinearindex %= this.myLinearIndexFuck[i];
		}
		return this.myLinearIndex2CoordinatesReturn;
	}
	// for flexibility we provide this where an array is passed-by-reference
	// and results are stored in it.
	public	void	linear_index2coordinates(int alinearindex, int ret[]){
		// given an index in a 1D array which was composed from an N-dim array
		// the result is passed in the array specified which must have length N
		for(int i=0;i<this.myNumDims;i++){
			ret[i] = alinearindex / this.myLinearIndexFuck[i];
			alinearindex %= this.myLinearIndexFuck[i];
		}
	}
	public	Iterator<T>	iterator(){ return new ahp.org.Containers.ArrayIterator<T>(this.myContent); }
	public	T get(int ... acoordinates){
		int a_linear_index = this.coordinates2linear_index(acoordinates);
		if( debug ){ System.out.println("DenseArray.java: get()/1 : content size: "+this.myContent.length+", index="+a_linear_index+", coord="+Arrays.toString(acoordinates)); }
		if( a_linear_index < this.myContent.length ){
			return this.myContent[a_linear_index];
		}
		return null;
	}
	public	T get_with_linear_index(int a_linear_index){
		if( debug ){ System.out.println("DenseArray.java: get_with_linear_index() : content size: "+this.myContent.length+", index="+a_linear_index); }
		if( a_linear_index < this.myContent.length ){
			return this.myContent[a_linear_index];
		}
		return null;
	}
	// put returns the linear index we were assigned to
	public	int put(
		T data_to_add,
		int ... acoordinates
	){
		int a_linear_index = this.coordinates2linear_index(acoordinates);
		this.myContent[a_linear_index] = data_to_add;
		if( debug ){ System.out.println("DenseArray.java: put() : content size: "+this.myContent.length+", index="+a_linear_index+", coord="+Arrays.toString(acoordinates)); }
		this.myNeedRecalculate = true;
		return a_linear_index;
	}
	public	int put_with_linear_index(
		T data_to_add,
		int a_linear_index
	){
		this.myContent[a_linear_index] = data_to_add;
		if( debug ){ System.out.println("latArray.java: put_with_linear_index() : content size: "+this.myContent.length+", index="+a_linear_index); }
		this.myNeedRecalculate = true;
		return a_linear_index;
	}
	public	boolean	contains(int ... acoordinates){
		int a_linear_index = this.coordinates2linear_index(acoordinates);
		return a_linear_index < this.myContent.length;
	}
	public	boolean	contains_with_linear_index(int a_linear_index){
		return a_linear_index < this.myContent.length;
	}
	public	int	size(){ return this.myContent.length; }
	public	int	capacity(){ return this.myContent.length; }
	public	double	sparseness(){ this.recalculate(false); return this.mySparseness; }
	protected	void	recalculate(boolean force){
		if( (force == false) && (this.myNeedRecalculate == false) ){ return; }
		// size() and capacity are longs, so force them to double

		int	i;
		this.myNumNonNullContents = 0;
		for(i=this.capacity();i-->0;){ if( this.myContent[i] != null ){ this.myNumNonNullContents++; } }

		this.mySparseness = 1.0 - 1.0 * this.size() / this.capacity();
		this.myNeedRecalculate = false;
	}
	@SafeVarargs
	private final <T> T[] create_fucking_generics_array(int length, T... array){
		return Arrays.copyOf(array, length);
	}
	public	String	toStringInfo(){
		this.recalculate(false);
		return new String(this.myName
			+" : size="+this.size()
			+", capacity="+String.format("%E", (double )this.myCapacity)
			+", sparseness="+this.mySparseness
		);
	}
	public	String	toString(){
		this.recalculate(false);
		StringBuilder sb = new StringBuilder();
		sb.append(this.toStringInfo());
		int	L = this.capacity(), i;
		for(i=0;i<L;i++){
			sb.append("\n");
			sb.append(this.myContent[i]==null ? "<null>" : this.myContent[i].toString());
		}
		return sb.toString();
	}
}
