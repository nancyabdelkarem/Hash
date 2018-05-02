package eg.edu.alexu.csd.filestructure.hash;

public class HashNode<T extends Comparable<T>> {

	private int key;

	public HashNode(int key) {

		this.setKey(key);
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
