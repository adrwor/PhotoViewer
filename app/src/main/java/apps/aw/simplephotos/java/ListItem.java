package apps.aw.simplephotos.java;

import apps.aw.simplephotos.java.treenavigator.ActionNode;
import apps.aw.simplephotos.java.treenavigator.FileNode;
import apps.aw.simplephotos.java.treenavigator.NodeData;


/**
 * High-level view of an item in a column. Has a name and a type.
 */
public class ListItem {

    public String name;
    public Type type;


    public ListItem(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public static ListItem ofNodeData(NodeData nodeData) {
        ListItem.Type type = ListItem.Type.ROOT;
        if(nodeData instanceof ActionNode) {
            type = ListItem.Type.ACTION;
        } else if (nodeData instanceof FileNode) {
            FileNode fileNode = (FileNode)nodeData;
            if(fileNode.isDirectory()) {
                type = ListItem.Type.DIRECTORY;
            } else if (fileNode.getMetaData().isImageFile) {
                type = ListItem.Type.IMAGE;
            } else {
                type = ListItem.Type.FILE;
            }
        }
        return new ListItem(
                nodeData.getName(),
                type
        );
    }

    public enum Type {
        ROOT,
        ACTION,
        DIRECTORY,
        IMAGE,
        BROKEN_IMAGE,
        FILE,
    }
}
