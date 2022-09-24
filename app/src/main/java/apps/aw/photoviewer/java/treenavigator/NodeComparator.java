package apps.aw.photoviewer.java.treenavigator;

import java.util.Comparator;

import apps.aw.photoviewer.java.treenavigator.tree.Node;

public class NodeComparator implements Comparator<Node<NodeData>> {

    @Override
    public int compare(Node<NodeData> o1, Node<NodeData> o2) {
        if(o1.equals(o2)) {
            return 0;
        } else if(o1.getData().isDirectory() && o2.getData().isDirectory()) {
            return o1.getData().getName().compareTo(o2.getData().getName());
        } else if(o1.getData().isDirectory() && !o2.getData().isDirectory()) {
            return -1;
        } else if(!o1.getData().isDirectory() && o2.getData().isDirectory()) {
           return 1;
        } else {
            // i.e. o1 and o2 are both not directories, we look at other properties
            if(o1.getData() instanceof FileNode && o2.getData() instanceof FileNode) {
                FileNode o1FileNode = (FileNode) o1.getData();
                FileNode o2FileNode = (FileNode) o2.getData();
                if (o1FileNode.getMetaData().isImageFile && o2FileNode.getMetaData().isImageFile) {
                    return o1FileNode.getMetaData().originalDateTime.compareTo(o2FileNode.getMetaData().originalDateTime);
                } else if (o1FileNode.getMetaData().isImageFile && !o2FileNode.getMetaData().isImageFile) {
                    return -1;
                } else if (!o1FileNode.getMetaData().isImageFile && o2FileNode.getMetaData().isImageFile) {
                    return 1;
                } else {
                    // i.e. o1FileNode and o2FileNode are both not images
                    return o1FileNode.getName().compareTo(o2FileNode.getName());
                }
            } else if(!(o1.getData() instanceof FileNode) && o2.getData() instanceof FileNode) {
                return -1;
            } else if(o1.getData() instanceof FileNode && !(o2.getData() instanceof FileNode)) {
                return 1;
            } else {
                // i.e. both o1NodeData and o2NodeData are not FileNodes, so we just compare the names
                return o1.getData().getName().compareTo(o2.getData().getName());
            }
        }
    }
}
