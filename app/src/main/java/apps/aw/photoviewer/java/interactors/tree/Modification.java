package apps.aw.photoviewer.java.interactors.tree;

public interface Modification {

    void initialize();

    void addSubRoot(String path);

    // TODO: maybe add more functions to modify the Tree (adding/removing parts of the tree)
}
