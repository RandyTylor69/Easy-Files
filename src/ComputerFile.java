public class ComputerFile extends FileSystemObject{
    private int size;

    public ComputerFile(String name, int id, int size) {
        super(name, id);
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }
}
