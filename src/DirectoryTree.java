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
        // step 1

        FileSystemObject x = null;


        if (level(a) > level (b)) {
            x = a;
            while (level(x)!=level(b)) {
                x = a.getParent();
            }
        }

        else if (level(b) > level (a)) {
            x = b;
            while (level(a)!=level(x)) x = b.getParent();
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

        // traverse up from a to topNode
        FileSystemObject temp = a;
        while(temp!=topNode) {
            temp = temp.getParent();
            sb.append("../");
        }

        // traverse down from topNode to b
        ArrayOrderedList<String> downPath = new ArrayOrderedList<>();
        temp = b;
        while(temp!=topNode) {
            downPath.add(temp.getName());
            temp = temp.getParent();

        }

        // add items from downPath reversely to sb
        while(!downPath.isEmpty()) {
            String str = downPath.removeLast();
            sb.append(str);
            if (str!=downPath.first()) sb.append("/");

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
            int hyp = level(r);
            sb.append((" ").repeat(hyp) + " - " + r.getName() + "\n");

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
        // fill
    }
    public void copyPaste(FileSystemObject f, FileSystemObject dest) {
        // fill
    }


}
