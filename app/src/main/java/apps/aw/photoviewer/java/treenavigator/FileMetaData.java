package apps.aw.photoviewer.java.treenavigator;

public class FileMetaData implements Comparable<FileMetaData> {

    public String originalDateTime = "";
    public boolean isImageFile = false;
    // TODO: add more data fields (maybe use some kind of map?)

    @Override
    public int compareTo(FileMetaData o) {
        // TODO: implement compareTo using a given metric
        return this.originalDateTime.compareTo(o.originalDateTime);
    }
}
