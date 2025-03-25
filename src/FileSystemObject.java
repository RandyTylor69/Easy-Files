public class FileSystemObject implements Comparable <FileSystemObject> {
    private String name;
    private OrderedListADT<FileSystemObject> children;
    private FileSystemObject parent;
    private int id;

    // folder (parent) constructor
    public FileSystemObject(String name, int id) {
        this.name = name;
        this.id = id;
        this.children = null;
        this.parent = null;
    }

    // file (child) constructor
    public FileSystemObject(String name, int id, OrderedListADT<FileSystemObject> children) {
        this.name = name;
        this.id = id;
        this.children = children;
        this.parent = null;
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
        return this.children!=null;
    }

    public void addChild (FileSystemObject node) {
        if (isFile()) throw new DirectoryTreeException("we cannot store a file/folder within a file");
        else
            if ()
    }
}
