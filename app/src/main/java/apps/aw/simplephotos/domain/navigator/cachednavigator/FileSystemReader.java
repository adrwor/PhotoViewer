package apps.aw.simplephotos.domain.navigator.cachednavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystemReader {

    public static List<FileNode> getFileNodeList(File directory) {
        File[] files = directory.listFiles();
        if(files == null) {
            return new ArrayList<>();
        }
        ArrayList<FileNode> fileNodes = new ArrayList<>();
        for (File f: files) {
            fileNodes.add(new FileNode(f));
        }
        return fileNodes;
    }

}
