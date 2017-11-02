package	ahp.org.Containers;

/* from 
	http://stackoverflow.com/questions/3912765/how-does-the-enhanced-for-statment-work-for-arrays-and-how-to-get-an-iterator-f
	user: http://stackoverflow.com/users/609149/ashok-d
*/

import	java.util.Iterator;
import	java.lang.UnsupportedOperationException;
import	java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
  private T array[];
  private int pos = 0;

  public ArrayIterator(T anArray[]) {
    array = anArray;
  }

  public boolean hasNext() {
    return pos < array.length;
  }

  public T next() throws NoSuchElementException {
    if (hasNext())
      return array[pos++];
    else
      throw new NoSuchElementException();
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}
