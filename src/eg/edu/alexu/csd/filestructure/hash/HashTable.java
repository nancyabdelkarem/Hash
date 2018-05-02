package eg.edu.alexu.csd.filestructure.hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTable<T extends Comparable<T>> {

	@SuppressWarnings("rawtypes")
	private HashNode[] hashTable;
	int tableSize;
	UniversalHashFamily universalHash;
	ArrayList<Integer> lista;
	int numOfReBuild = 0;

	public HashTable(int nomOfKeys) {
		tableSize = (int) Math.pow(nomOfKeys, 2);
		hashTable = new HashNode[tableSize];
		lista = new ArrayList<>();
	}

	@SuppressWarnings("rawtypes")
	public HashNode[] getHashTable() {
		return hashTable;
	}

	private boolean insert(int key) {
		int j;
		j = universalHash.getHashedIndex(key);

		if (hashTable[j] == null) {
			lista.add(key);
			HashNode<T> node = new HashNode<T>(key);
			hashTable[j] = node;
			return true;
		} else if (lista.contains(key)) {
			System.out.println("the key " + key + "is repeated !!");
			return true;
		} else {
			System.out.println("oops collision ! " + j + " " + hashTable[j].getKey());
			return false;
		}
	}

	public void insert(int[] arrayOfKeys) {
		boolean done = false;
		while (!done) {
			universalHash = new UniversalHashFamily(tableSize, 32);
			lista = new ArrayList<>();
			hashTable = new HashNode[tableSize];
			numOfReBuild++;
			for (int i = 0; i < arrayOfKeys.length; i++) {
				if (this.insert(arrayOfKeys[i])) {
					done = true;
				} else {
					System.out.println("Oops anthor method");
					done = false;
					break;
				}
			}
		}
		System.out.println(numOfReBuild);

	}

	public boolean find(int key) {
		return hashTable[universalHash.getHashedIndex(key)] != null;

	}

	public void insert(File file) {
		boolean done = false;
		while (!done) {
			universalHash = new UniversalHashFamily(tableSize, 32);
			lista = new ArrayList<>();
			hashTable = new HashNode[tableSize];
			numOfReBuild++;
			Scanner scan = null;
			try {
				scan = new Scanner(file);
				scan.useDelimiter(",");
				while (scan.hasNext()) {
					if (this.insert(Integer.parseInt(scan.next()))) {
						done = true;
					} else {
						System.out.println("Oops anthor method");
						done = false;
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			scan.close();

		}

	}

}