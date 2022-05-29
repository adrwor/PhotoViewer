package apps.aw.simplephotos.java;


public class Path {
    public String parentPath;
    public String name;

    public Path() {
        this.parentPath = "";
        this.name = "";
    }

    public Path(String name) {
        this.parentPath = "";
        this.name = name;
    }

    public Path(String parentPath, String name) {
        this.parentPath = parentPath;
        this.name = name;
    }

    public String toString() {
        return parentPath + name;
    }
}
