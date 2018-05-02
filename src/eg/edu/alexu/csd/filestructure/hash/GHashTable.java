package eg.edu.alexu.csd.filestructure.hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GHashTable<T extends Comparable<T>> {
	static int keySize = 32;

	int firstLevelTableSize;
	UniversalHashFamily universalHash;
	ArrayList<Integer>[] collidedKeys;
	ArrayList<HashNode[]> gHashTable = new ArrayList<HashNode[]>();
	HashTable sndHashTable;
	UniversalHashFamily[] hashFunctions;
	int sum = 0;

	public GHashTable(int NumOfKeys) {
		this.firstLevelTableSize = NumOfKeys;
		collidedKeys = (ArrayList<Integer>[]) new ArrayList[firstLevelTableSize];
		hashFunctions = new UniversalHashFamily[firstLevelTableSize];
		for (int i = 0; i < firstLevelTableSize; i++) {
			collidedKeys[i] = new ArrayList<Integer>();
		}

	}

	public void insert(int[] keys) {
		universalHash = new UniversalHashFamily(firstLevelTableSize, keySize);
		for (int i = 0; i < keys.length; i++) {

			int hashedIndex = universalHash.getHashedIndex(keys[i]);
			if (collidedKeys[hashedIndex].size() == 0) {
				collidedKeys[hashedIndex].add(keys[i]);
			} else {
				if (!isRepeated(keys[i])) {
					collidedKeys[hashedIndex].add(keys[i]);
				}
			}
		}
		for (int j = 0; j < firstLevelTableSize; j++) {
			HashNode[] hashTable;
			if (collidedKeys[j].size() != 0) {
				hashTable = rehash(collidedKeys[j]);
				sum = sum + hashTable.length;
				hashFunctions[j] = sndHashTable.universalHash;
				gHashTable.add(hashTable);

			} else {
				gHashTable.add(null);
			}

		}

	}

	private HashNode[] rehash(ArrayList<Integer> list) {
		sndHashTable = new HashTable(list.size());
		int[] arrayOfKeys = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arrayOfKeys[i] = list.get(i);
		}
		sndHashTable.insert(arrayOfKeys);
		return sndHashTable.getHashTable();
	}

	public boolean isRepeated(int key) {
		for (int i = 0; i < firstLevelTableSize; i++) {

			if (collidedKeys[i].contains(key)) {

				return true;
			}

		}
		return false;
	}

	public boolean find(int key) {
		HashNode[] subArr = gHashTable.get(universalHash.getHashedIndex(key));

		UniversalHashFamily hfUNCTION = hashFunctions[universalHash.getHashedIndex(key)];

		if (subArr != null && subArr[hfUNCTION.getHashedIndex(key)] != null) {
			if (subArr[hfUNCTION.getHashedIndex(key)].getKey() == key) {
				return true;
			}

		}

		return false;

	}

	public void insert(File file) {
		universalHash = new UniversalHashFamily(firstLevelTableSize, keySize);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
			scan.useDelimiter(",");
			while (scan.hasNext()) {
				int key = Integer.parseInt(scan.next());
				int hashedIndex = universalHash.getHashedIndex(key);
				if (collidedKeys[hashedIndex].size() == 0) {
					collidedKeys[hashedIndex].add(key);
				} else {
					if (!isRepeated(key)) {
						collidedKeys[hashedIndex].add(key);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.close();

		for (int j = 0; j < firstLevelTableSize; j++) {
			HashNode[] hashTable;
			if (collidedKeys[j].size() != 0) {
				hashTable = rehash(collidedKeys[j]);
				sum = sum + hashTable.length;
				hashFunctions[j] = sndHashTable.universalHash;
				gHashTable.add(hashTable);
			} else {
				gHashTable.add(null);
			}

		}

	}
}