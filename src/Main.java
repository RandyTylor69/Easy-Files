import java.util.Iterator;

public class Main {

    private static FileSystemObject root;

    public static void main(String[] args) {
        test02();
    }

    private static void test02 () {

        try {
            DirectoryTree t = createTree();

            int origSize = t.getRoot().size();

            FileSystemObject f = getNodeByID(3); // documents
            FileSystemObject d = getNodeByID(6); // lisa

            t.copyPaste(f, d);

            System.out.println(t);

        } catch (Exception e) {
            System.out.println("\t\t FAILED (Exception)");
            System.out.println(e + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static DirectoryTree createTree () {

        root = new FileSystemObject("images", 0);

        FileSystemObject f1 = new FileSystemObject("vacation", 1);
        FileSystemObject f2 = new FileSystemObject("brazil", 2);
        FileSystemObject f3 = new FileSystemObject("rio", 3);
        FileSystemObject f4 = new FileSystemObject("beach", 4);
        FileSystemObject f5 = new FileSystemObject("sunsets", 5);

        FileSystemObject f6 = new FileSystemObject("miami", 6);

        FileSystemObject f7 = new ComputerFile("img001.jpg", 7, 1800);
        FileSystemObject f8 = new ComputerFile("img002.jpg", 8, 1900);
        FileSystemObject f9 = new ComputerFile("img003.jpg", 9, 1500);
        FileSystemObject f10 = new ComputerFile("img004.jpg", 10, 1600);
        FileSystemObject f11 = new ComputerFile("img005.jpg", 11, 1700);
        FileSystemObject f12 = new ComputerFile("img006.jpg", 12, 1100);
        FileSystemObject f13 = new ComputerFile("img007.jpg", 13, 1200);
        FileSystemObject f14 = new ComputerFile("img008.jpg", 14, 1300);
        FileSystemObject f15 = new ComputerFile("img009.jpg", 15, 1400);
        FileSystemObject f16 = new ComputerFile("img010.jpg", 16, 900);
        FileSystemObject f17 = new ComputerFile("img011.jpg", 17, 1000);
        FileSystemObject f18 = new ComputerFile("img012.jpg", 18, 500);
        FileSystemObject f19 = new ComputerFile("img013.jpg", 19, 600);
        FileSystemObject f20 = new ComputerFile("img014.jpg", 20, 700);
        FileSystemObject f21 = new ComputerFile("img015.jpg", 21, 800);

        FileSystemObject f22 = new ComputerFile("clipart.png", 22, 2000);
        FileSystemObject f23 = new ComputerFile("logo.gif", 23, 2100);
        FileSystemObject f24 = new ComputerFile("selfie.jpg", 24, 2200);

        root.addChild(f1);
        root.addChild(f22);
        root.addChild(f23);
        root.addChild(f24);

        f1.addChild(f2);
        f1.addChild(f6);
        f1.addChild(f7);
        f1.addChild(f8);

        f2.addChild(f3);
        f2.addChild(f12);
        f2.addChild(f13);
        f2.addChild(f14);
        f2.addChild(f15);

        f3.addChild(f4);
        f3.addChild(f16);
        f3.addChild(f17);

        f4.addChild(f5);

        f5.addChild(f18);
        f5.addChild(f19);
        f5.addChild(f20);
        f5.addChild(f21);

        f6.addChild(f9);
        f6.addChild(f10);
        f6.addChild(f11);

        return new DirectoryTree(root);
    }

    private static DirectoryTree createTreeSimpsons () {
        root = new FileSystemObject("C:", 0);

        FileSystemObject f1 = new FileSystemObject("Desktop", 1);
        FileSystemObject f2 = new FileSystemObject("Work", 2);
        FileSystemObject f3 = new FileSystemObject("Documents", 3);
        FileSystemObject f4 = new FileSystemObject("Pictures", 4);
        FileSystemObject f5 = new FileSystemObject("pics", 5);

        FileSystemObject f6 = new ComputerFile("food.jpg", 6, 1500);
        FileSystemObject f7 = new ComputerFile("nyc.jpg", 7, 1800);
        FileSystemObject f8 = new FileSystemObject("Work", 8);

        FileSystemObject f9 = new FileSystemObject("Public", 9);
        FileSystemObject f10 = new FileSystemObject("Users", 10);
        FileSystemObject f11 = new FileSystemObject("Bart", 11);
        FileSystemObject f12 = new FileSystemObject("Lisa", 12);
        FileSystemObject f13 = new FileSystemObject("Maggie", 13);

        FileSystemObject f14 = new ComputerFile("bg.jpg", 14, 2000);

        root.addChild(f1);
        root.addChild(f3);
        root.addChild(f9);
        root.addChild(f10);
        root.addChild(f14);

        f1.addChild(f2);

        f3.addChild(f4);
        f3.addChild(f8);

        f4.addChild(f5);
        f4.addChild(f6);
        f4.addChild(f7);

        f10.addChild(f11);
        f10.addChild(f12);
        f10.addChild(f13);

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