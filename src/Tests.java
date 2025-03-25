import java.util.Iterator;

public class Tests {
	
	private static FileSystemObject root;

	public static void main(String[] args) {

		test01(); // DT toString
		test02(); // DT lca
		test03(); // DT buildPath
		test04(); // DT cutPaste
		test05(); // DT copyPaste
	}

	private static void test01 () {
		System.out.print("Test 1 - toString()");
		try {

			DirectoryTree t = createTree();
			//System.out.println();
			//System.out.println(t);

			String exp = "C:\n"
					+ " - Users\n"
					+ "   - Alice\n"
					+ "     - Website\n"
					+ "       - about.html\n"
					+ "       - contact.html\n"
					+ "       - index.html\n"
					+ "     - img_1285019.jpg\n"
					+ "   - Bob\n"
					+ "     - Games\n"
					+ "       - Games\n"
					+ "       - pacman.exe\n"
					+ "   - Carl\n"
					+ "     - asst1.pdf\n"
					+ "     - asst2.pdf\n"
					+ "     - asst3.pdf\n"
					+ "     - asst4.pdf\n"
					+ "   - resume.docx";

			if (t.toString().strip().equals(exp.strip())) {
				System.out.println("\t\t PASSED!");
			} else {
				System.out.println("\t\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private static void test02 () {
		System.out.print("Test 2 - lca()");
		try {
			
			DirectoryTree t = createTree();

			FileSystemObject f1 = getNodeByID(10);
			FileSystemObject f2 = getNodeByID(11);
			FileSystemObject f3 = getNodeByID(12);
			FileSystemObject f4 = getNodeByID(15);
			
			FileSystemObject exp1 = getNodeByID(5);
			FileSystemObject exp2 = getNodeByID(9);
			FileSystemObject exp3 = getNodeByID(1);

			boolean b1 = t.lca(f1, f2) == exp1;
			boolean b2 = t.lca(f2, f3) == exp2;
			boolean b3 = t.lca(f3, f4) == exp3;

			if (b1 && b2 && b3) {
				System.out.println("\t\t\t PASSED!");
			} else {
				System.out.println("\t\t\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t\t\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void test03 () {
		System.out.print("Test 3 - buildPath()");
		try {
			
			DirectoryTree t = createTree();

			FileSystemObject f1 = getNodeByID(11);
			FileSystemObject f2 = getNodeByID(10);
			FileSystemObject f3 = getNodeByID(8);
			FileSystemObject f4 = getNodeByID(3);
			
			String s1 = t.buildPath(f1, f2);
			String s2 = t.buildPath(f2, f3);
			String s3 = t.buildPath(f4, f1);
			
			boolean b1 = s1 != null && s1.equals("../../img_1285019.jpg");
			boolean b2 = s2 != null && s2.equals("../../Bob/Games/pacman.exe");
			boolean b3 = s3 != null && s3.equals("../Alice/Website/index.html");
			
			if (b1 && b2 && b3) {
				System.out.println("\t\t PASSED!");
			} else {
				System.out.println("\t\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void test04 () {
		System.out.print("Test 4 - cutPaste");
		try {
			
			DirectoryTree t = createTree();

			// Cut and paste the Website folder into the Bob folder.
			FileSystemObject f = getNodeByID(9);
			FileSystemObject d = getNodeByID(2);
			FileSystemObject x = getNodeByID(5);
			t.cutPaste(f, d);

			// First, check that the Website folder is in the Bob root now.
			boolean b1 = d.getChildren().size() == 2;
			Iterator<FileSystemObject> iter = d.getChildren().iterator();
			b1 = b1 && iter.next().getName().equals("Games");
			b1 = b1 && iter.next().getName().equals("Website");
			
			
			// Now, check that the Website folder is removed from the Alice folder.
			boolean b2 = x.getChildren().size() == 1;
			b2 = b2 && x.getChildren().first().getName().equals("img_1285019.jpg");
			
			if (b1 && b2) {
				System.out.println("\t\t PASSED!");
			} else {
				System.out.println("\t\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void test05 () {
		System.out.print("Test 5 - copyPaste");
		try {
			DirectoryTree t = createTree();

			int origSize = t.getRoot().size();
			
			// Cut and paste the Website folder into the Bob folder.
			FileSystemObject f = getNodeByID(9);
			FileSystemObject d = getNodeByID(2);

			t.copyPaste(f, d);

			// First, check that the Website folder is in the Bob root now.
			boolean b1 = d.getChildren().size() == 2;
			Iterator<FileSystemObject> iter = d.getChildren().iterator();
			b1 = b1 && iter.next().getName().equals("Games");
			b1 = b1 && iter.next().getName().equals("Website");

			// Now, check original and new total file sizes to determine if files were copied.
			int newSize = t.getRoot().size();
			boolean b2 = origSize == 23209 && newSize == 23508;

			if (b1 && b2) {
				System.out.println("\t\t PASSED!");
			} else {
				System.out.println("\t\t FAILED");
			}
		} catch (Exception e) {
			System.out.println("\t\t FAILED (Exception)");
			System.out.println(e + " - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private static DirectoryTree createTree () {
		root = new FileSystemObject("C:", 0);
		
		FileSystemObject f1 = new FileSystemObject("Users", 1);
		
		FileSystemObject f2 = new FileSystemObject("Bob", 2);
		FileSystemObject f3 = new ComputerFile("resume.docx", 3, 742);
		FileSystemObject f4 = new FileSystemObject("Carl", 4);
		FileSystemObject f5 = new FileSystemObject("Alice", 5);
		
		FileSystemObject f6 = new FileSystemObject("Games", 6);
		FileSystemObject f7 = new FileSystemObject("Games", 7);
		FileSystemObject f8 = new ComputerFile("pacman.exe", 8, 13093);
		
		FileSystemObject f9 = new FileSystemObject("Website", 9);
		FileSystemObject f10 = new ComputerFile("img_1285019.jpg", 10, 4827);
		FileSystemObject f11 = new ComputerFile("index.html", 11, 149);
		FileSystemObject f12 = new ComputerFile("contact.html", 12, 57);
		FileSystemObject f13 = new ComputerFile("about.html", 13, 93);
		
		FileSystemObject f14 = new ComputerFile("asst1.pdf", 14, 894);
		FileSystemObject f15 = new ComputerFile("asst2.pdf", 15, 1307);
		FileSystemObject f16 = new ComputerFile("asst3.pdf", 16, 1055);
		FileSystemObject f17 = new ComputerFile("asst4.pdf", 17, 992);
		
		root.addChild(f1); // Users
		f1.addChild(f2); // Bob
		f1.addChild(f3); // resume.docx
		f1.addChild(f4); // Carl
		f1.addChild(f5); // Alice
		f2.addChild(f6); // Games
		f6.addChild(f7); // Games
		f6.addChild(f8); // pacman.exe
		f5.addChild(f9); // Website
		f5.addChild(f10); // img_1285019.jpg
		f9.addChild(f11); // index.html
		f9.addChild(f12); // contact.html
		f9.addChild(f13); // about.html
		f4.addChild(f14); // asst1.pdf
		f4.addChild(f15); // asst2.pdf
		f4.addChild(f16); // asst3.pdf
		f4.addChild(f17); // asst4.pdf

		return new DirectoryTree(root);
	}
	
	private static FileSystemObject getNodeByID(int id) {
		return getNodeByIDHelper(root, id);
	}
	
	private static FileSystemObject getNodeByIDHelper(FileSystemObject node, int id) {
		if (node == null) return null;
		
		if (node.getID() == id) return node;
		
		if (node.isFile()) return null;
		
		Iterator<FileSystemObject> iter = node.getChildren().iterator();
		FileSystemObject child;
		FileSystemObject res;
		
		while (iter.hasNext()) {
			child = iter.next();
			res = getNodeByIDHelper(child, id);
			if (res != null) return res;
		}
		
		return null;
	}
}
