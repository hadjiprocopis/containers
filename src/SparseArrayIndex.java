package	ahp.org.Containers;

import	java.util.Arrays;
import	java.io.Serializable;

public	class	SparseArrayIndex implements Serializable {
	private static final long serialVersionUID = 1797271487L+7L;

	private	int	myNumDims;
	private	int	myCoordinates[];
	private	int	myHashCode;

	public	SparseArrayIndex(int numdims){
		this.myNumDims = numdims;
		this.init();
		// note: java always creates zero-filled int arrays so this is not needed
		this.zero_coordinates();
	}
	public	SparseArrayIndex(int acoordinates[]){
		this.myNumDims = acoordinates.length;
		this.init();
		this.set(acoordinates);
	}
	private	void	init(){
		this.myCoordinates = new int[this.myNumDims];
	}
	public	int	num_dimensions(){ return this.myNumDims; }
	public	int[]	coordinates(){ return this.myCoordinates; }
	public	void	zero_coordinates(){
		for(int i=this.myNumDims;i-->0;){
			this.myCoordinates[i] = 0;
		}
		this.recalculate();
	}
	public	void	set(int acoordinates[]){
		for(int i=this.myNumDims;i-->0;){
			this.myCoordinates[i] = acoordinates[i];
		}
		this.recalculate();
	}
	private	void	recalculate(){
		StringBuilder sb = new StringBuilder();
		int L = this.myNumDims - 1, i;
		for(i=0;i<L;i++){
			sb.append(this.myCoordinates[i]);
			sb.append('|');
		}
		sb.append(this.myCoordinates[i]);
		this.myHashCode = sb.toString().hashCode();
	}
	/* implementing own hashcode must also implement own equals */
	@Override
	public	int	hashCode(){ return this.myHashCode; }
	@Override
	public	boolean	equals(Object obj){
		return this.myHashCode == obj.hashCode();
	}
	public	SparseArrayIndex	clone(){
		return new SparseArrayIndex(this.myCoordinates);
	}
	public	SparseArrayIndex	clone_only_structure(int numdims){
		return new SparseArrayIndex(numdims);
	}
	public	String	toString(){
		return new String("("+this.myNumDims+") : "+java.util.Arrays.toString(this.myCoordinates)+", hash: "+this.myHashCode);
	}
}
