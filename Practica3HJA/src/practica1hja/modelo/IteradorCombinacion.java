/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//http://bobsfera.blogspot.com.es/2011/03/combinaciones-sin-repeticion-en-java-ii.html
package practica1hja.modelo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IteradorCombinacion implements Iterable<List<Card>> {
	private List<Card> lista;
	private Integer k;

	public IteradorCombinacion(List<Card> s, Integer k) {
		lista = s;
		this.k = k;
	}

	@Override
	public Iterator<List<Card>> iterator() {

		return new IteradorCombn(lista, k);
	}

	private class IteradorCombn implements Iterator<List<Card>> {
		private int actualSize, maxresult;
		private Integer curIndex;
		private Card[] result;
		private int[] indices;
		private Card[] arrayList;
		private List<Card> elem = null;

		public IteradorCombn(List<Card> s, Integer k) {
			actualSize = k;// desde dónde
			curIndex = 0;
			maxresult = k;
			arrayList = new Card[s.size()];
			for (int i = 0; i < arrayList.length; i++) { // la lista s la vuelca en arrayList
				arrayList[i] = s.get(i);
			}
			this.result = new Card[actualSize < s.size() ? actualSize : s.size()]; 
			//el tamaño de result va a ser el valor menor entre actualSize y el tamaño de s
			indices = new int[result.length];

			for (int i = 0; i < result.length; i++) {
				indices[i] = result.length - 2 - i;
			}
		}

		public boolean hasNext() {
			elem = null;
			while ((elem == null && curIndex != -1)) {

				indices[curIndex]++;
				if (indices[curIndex] == (curIndex == 0 ? arrayList.length: indices[curIndex - 1])) {
					
					indices[curIndex] = indices.length - curIndex - 2;
					curIndex--;
				} else {

					result[curIndex] = arrayList[indices[curIndex]];
					
					if (curIndex < indices.length - 1)
						curIndex++;
					else {
						elem = new LinkedList<Card>();
						for (Card s : result) {
							elem.add(s);
						}

					}
				}
			}
			if (elem == null) {
				if (actualSize < maxresult) {
					actualSize++;
					this.result = new Card[actualSize < arrayList.length ? actualSize
							: arrayList.length];
					indices = new int[result.length];

					for (int i = 0; i < result.length; i++) {

						indices[i] = result.length - 2 - i;
					}
					curIndex = 0;

					return this.hasNext();
				} else {
					return false;
				}
			} else {
				return true;
			}
		}

		@Override
		public List<Card> next() {
			return elem;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}
	}
}
