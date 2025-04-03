import java.io.File;
import java.nio.file.Files;
import java.util.Iterator;

public class DirectoryTree {
    private FileSystemObject root;

    public DirectoryTree(FileSystemObject rt) {
        root = rt;
    }

    public FileSystemObject getRoot() {return root;}

    public int level(FileSystemObject fso) {
        if (fso == root) return 0;
        else return 1 + level(fso.getParent());
    }

    public FileSystemObject lca (FileSystemObject a, FileSystemObject b) {

        FileSystemObject x = null;

        // return root
        if (a == root) return a;
        else if (b == root) return b;

        // return if direct parent
        if (a.getParent() == b) return b;
        else if (b.getParent() == a) return a;


        if (level(a) > level (b)) {
            x = a;
            while (level(x)!=level(b)) {
                x = x.getParent();
            }
        }

        else if (level(b) > level (a)) {
            x = b;
            while (level(a)!=level(x))
                x = x.getParent();
        }

        // step 2

        // ----> 2 base cases
        if (a.getParent() == b.getParent()) return a.getParent();
        if (x!= null) {
            if (a.getParent() == x.getParent() || b.getParent() == x.getParent())
            {return x.getParent();}
        }

        // ----> recursive case
        if (level(a) == level(b)) return lca (a.getParent(), b.getParent());
        else if (level(a) == level(x)) return lca (a.getParent(), x.getParent());
        else if (level(b) == level(x)) return lca (b.getParent(), x.getParent());
        else return null;
    }

    public String buildPath (FileSystemObject a, FileSystemObject b) {

        if (a.getParent() == b.getParent()) return b.getName();

        FileSystemObject topNode = lca(a,b); // lowest common ancestor
        StringBuilder sb = new StringBuilder();

        FileSystemObject temp;

        // if topNode == either a or b, ignore this block
        if (topNode != a || topNode != b) {
            temp = a;

            // traverse up from a to topNode
            while(temp!=topNode) {
                temp = temp.getParent();

                // System.out.println("visiting "  + temp);

                sb.append("../");
            }
        }

        // traverse down from topNode to b
        String[] downPath = new String[10];
        int i = 0;

        temp = b;
        while(temp!=topNode) {
            downPath[i] = (temp.getName());
            temp = temp.getParent();
            i++;

        }

        // add items from downPath reversely to sb
        while(downPath[0]!= null) { // while downPath is not empty

            String str = "";
            for (int j = 9; j >= 0; j--) {
                if (downPath[j]!=null) {str = downPath[j];
                downPath[j] = null;
                break;}
            }
            sb.append(str);
            if (downPath[0]!=null) sb.append("/");

        }
        return sb.toString();

    }

    public String toString(){
        return toStringHelper(root, new StringBuilder());
    }

    private String toStringHelper(FileSystemObject r, StringBuilder sb) {

        if (r == null) return "";

        // step 1: print on that thang

        if (r == root) {
            sb.append(r.getName() + "\n");

            //System.out.println("\n" + r.getName() );

        } else {
            int hyp = level(r) - 1;
            sb.append(("  ").repeat(hyp) + " - " + r.getName() + "\n");

            //System.out.println((" ").repeat(hyp) + "- " + r.getName());
        }

        // step 2: recursively call the children if r is a folder

        if (r.isFile()) return "";

        ArrayIterator<FileSystemObject> iter = (ArrayIterator<FileSystemObject>) r.getChildren().iterator();

        while(iter.hasNext()) {
            FileSystemObject next = iter.next();
            toStringHelper(next, sb);
        }
        return sb.toString();
    }

    public void cutPaste(FileSystemObject f, FileSystemObject dest) {
        if (dest.isFile()) throw new DirectoryTreeException("destination cannot be a file");

        if (f == root) throw new DirectoryTreeException("cannot remove the root of the directory");

        // --- remove the file/folder
        f.getParent().getChildren().remove(f);

        // --- move file/folder into dest
        f.setParent(dest);
        dest.addChild(f);
    }

    public void copyPaste(FileSystemObject f, FileSystemObject dest) {
        if (dest.isFile()) throw new DirectoryTreeException("destination cannot be a file");

        System.out.println("copying " + f + " to " + dest);

        if (f.isFile()) {
            FileSystemObject clone = new ComputerFile(f.getName(), f.getID()+100, f.size());
            dest.addChild(clone);
        }
        else {
           copyPasteHelper(f, dest);
        }

    }

    private void copyPasteHelper(FileSystemObject source, FileSystemObject dest) {

        FileSystemObject newFolder = new FileSystemObject(source.getName(), source.getID()+100);
        dest.addChild(newFolder);

        ArrayIterator<FileSystemObject> iter = (ArrayIterator<FileSystemObject>) source.getChildren().iterator();

        while (iter.hasNext()) {
            FileSystemObject node = iter.next();

            if (node.isFile()) {
                newFolder.addChild(new ComputerFile(node.getName(), node.getID()+100, node.size()));
            } else {
                copyPasteHelper(node, dest);
            }
        }

    }


}
