package apps.aw.simplephotos.presentation_layer.browser.utils;

import java.util.List;

public class FileNode {

    enum fileType{
        DIRECTORY,
        IMAGEFILE,
        OTHER
    }

    fileType type;

    Object data;
    List<FileNode> childNodes;

    public FileNode(fileType type) {
        this.type = type;
    }


    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setChildNodes(List<FileNode> childNodes) {
        this.childNodes = childNodes;
    }

    public List<FileNode> getChildNodes() {
        return childNodes;
    }

}
