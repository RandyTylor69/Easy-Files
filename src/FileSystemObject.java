
public class FileSystemObject implements Comparable <FileSystemObject> {
    private String name;
    private OrderedListADT<FileSystemObject> children;
    private FileSystemObject parent;
    private int id;

    // folder (parent) constructor
    public FileSystemObject(String name, int id) {
        this.name = name;
        this.id = id;
        if (isFile()) {
            this.children = null;
        } else this.children = new ArrayOrderedList<>();
    }

    // getters + setters
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public FileSystemObject getParent() {
        return parent;
    }
    public OrderedListADT<FileSystemObject> getChildren() {
        return children;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setParent(FileSystemObject parent) {
        this.parent = parent;
    }

    public boolean isFile() {
        return this instanceof ComputerFile;
    }

    // private helper method for addChild()
    private void addChildAssist(FileSystemObject node) {

        ArrayIterator<FileSystemObject> iter = (ArrayIterator<FileSystemObject>) children.iterator();
        while(iter.hasNext()) {
            if(iter.next().getName().equals(node.getName())) throw new DirectoryTreeException("file name already exists");
        }
        children.add(node);
    }

    public void addChild (FileSystemObject node) {
        if (isFile()) throw new DirectoryTreeException("we cannot store a file/folder within a file");
        else addChildAssist(node);

    }

    public String toString() {
        return name;
    }

    // private helper method for size()
    private int sizeAssist(FileSystemObject node) {
        int sum = 0;
        if (!node.isFile()) { // if folder
            ArrayIterator<FileSystemObject> iter = (ArrayIterator<FileSystemObject>) node.getChildren().iterator();
            while(iter.hasNext()) {
                sum += sizeAssist(iter.next());
            }
        } else sum+=node.size();

        return sum;
    }


    public int size() {
        if (isFile()) return this.size();
        else return sizeAssist(this);
    }


    public int compareTo(FileSystemObject other) {
        if (!this.isFile() && other.isFile()) return -10; // folder is "smaller" than a file and came first
        else if (this.isFile() && !other.isFile()) return 10; // file is "larger" than a folder and came second
        else return this.getName().compareToIgnoreCase(other.getName());
    }
}
