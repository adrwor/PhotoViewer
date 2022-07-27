package apps.aw.simplephotos.java.treenavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystemReader {

    private final FileMetaDataReader fileMetaDataReader;

    public FileSystemReader(FileMetaDataReader fileMetaDataReader) {
        this.fileMetaDataReader = fileMetaDataReader;
    }


    public List<FileNode> getFileNodeList(File directory) {
        File[] files = directory.listFiles();
        if(files == null) {
            return new ArrayList<>();
        }
        ArrayList<FileNode> fileNodes = new ArrayList<>();
        for (File f: files) {
            FileMetaData metaData = fileMetaDataReader.readFileMetaData(f);
            fileNodes.add(new FileNode(f, f.getName(), metaData));
        }
        return fileNodes;
    }

}
