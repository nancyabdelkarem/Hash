package eg.edu.alexu.csd.filestructure.hash;

import java.io.File;

public class test {

	public static void main(String[] args) {

		GHashTable<String> table = new GHashTable<String>(1000);
		table.insert(new File("D:\\keys10001000.txt"));
		//System.out.println(table.sum);
		//System.out.println(table.find(104));
	}

}
