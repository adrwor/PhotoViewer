package apps.aw.simplephotos.domain_layer.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.domain_layer.model.Tree.Node;

/**
 * Class which provides methods to move from directory to directory, as well as list folders and files.
 */
public class FileSystemNavigator {

    private FileSystemModel fileSystemModel;
    private Node<FileSystemNode> currentNode;

    /**
     * Constructor
     */
    public FileSystemNavigator(FileModel rootFileModel) {
        setFileSystemModel(new FileSystemModel(new FileSystemNode(rootFileModel)));
        setCurrentNode(getFileSystemModel().getRootNode());
    }

    public FileSystemModel getFileSystemModel() {
        return fileSystemModel;
    }

    public void setFileSystemModel(FileSystemModel fileSystemModel) {
        this.fileSystemModel = fileSystemModel;
    }

    public Node<FileSystemNode> getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node<FileSystemNode> node) {
        currentNode = node;
    }

    public boolean currentNodeIsDirectory() throws SecurityException {
        return getCurrentNode().getData().getFileModel().isDirectory();
    }

    /**
     *
     * @return the parent node, null if current node is root.
     */
    public Node<FileSystemNode> getParentNode() {
        return getCurrentNode().getParent();
    }

    /**
     * Sets current Directory to parent Directory (if there is a parent directory).
     */
    public void goToParentNode() {
        // TODO: maybe do these checks one level above, in the InteractorImpl?
        Node<FileSystemNode> parent = getParentNode();
        if(parent != null) {
            setCurrentNode(parent);
        }
    }

    public void goToFocusedChildNode() {
        // TODO: maybe do these checks one level above, in the InteractorImpl?
        if(currentNodeHasChildren()) {
            setCurrentNode(getFocusedChildNode());
        }
    }

    public Node<FileSystemNode> getFocusedChildNode() {
        assert hasChildren(currentNode);
        int focusedChildPosition = getCurrentNode().getData().getFocusedChildPosition();
        return getChildrenOfCurrentNode().get(focusedChildPosition);    // index out of bounds
    }

    public void focusChildNode(int position) {
        getCurrentNode().getData().setFocusedChildPosition(position);
    }

    public int getChildNodeSelection() {
        return getCurrentNode().getData().getFocusedChildPosition();
    }

    public void focusNextChildNode() {
        int currentPosition = getCurrentNode().getData().getFocusedChildPosition();
        if(currentPosition < getChildrenOfCurrentNode().size() - 1) {
            getCurrentNode().getData().setFocusedChildPosition(currentPosition + 1);
        }
    }

    public void focusPreviousChildNode() {
        int currentPosition = getCurrentNode().getData().getFocusedChildPosition();
        if(currentPosition > 0) {
            getCurrentNode().getData().setFocusedChildPosition(currentPosition - 1);
        }
    }

    public List<Node<FileSystemNode>> getChildrenOfCurrentNode() {
        return getChildrenOfNode(getCurrentNode());
    }

    public boolean currentNodeHasChildren() {
        return hasChildren(currentNode);
    }
    public boolean hasChildren(Node<FileSystemNode> node) {
        List<Node<FileSystemNode>> children = getChildrenOfNode(node);
        return (children != null) && (children.size() > 0);
    }


    public List<Node<FileSystemNode>> getChildrenOfNode(Node<FileSystemNode> node) {
//        Log.i("FileSystemNavigator", "getChildrenOfNode(" + node.getData().getFileModel().getFile().getAbsolutePath() + ")");
        syncChildrenOfNode(node);
        return node.getChildren();
    }

    /**
     * If the given node denotes a directory, the subFiles are loaded from disk and stored in the
     * tree. If the given node is not a directory, nothing happens.
     * @param node
     */
    public void syncChildrenOfNode(Node<FileSystemNode> node) {
//        Log.i("FileSystemNavigator", "syncChildrenOfNode " + node.getData().getFileModel().getFile().getAbsolutePath());
//        Log.i("FileSystemNavigator", "isDirectory: " + node.getData().getFileModel().isDirectory());
//        Log.i("FileSystemNavigator", "children: " + node.getChildren());
        if(node.getData().getFileModel().isDirectory() && (node.getChildren() == null)) {
//            Log.i("FileSystemNavigator", "Now reloadChildrenFromDisk()");
            reloadChildrenFromDisk(node);
        }
    }

    /**
     * Sets the children of the given node if the corresponding file is a directory, or sets
     * children to null if the given file is not a directory.
     * @param node
     */
    public void reloadChildrenFromDisk(@NonNull Node<FileSystemNode> node) {
        if(node.getData().getFileModel().isDirectory()) {
//            Log.i("FileSystemNavigator", "Node " + node.getData().getFileModel().getFile().getName() + " is a directory");
            List<FileModel> subfiles;
//            Log.i("FileSystemNavigator", "Now getSubFileModels()");
            subfiles = FileSystemFacade.getSubFileModels(node.getData().getFileModel().getFile());
//            Log.i("FileSystemNavigator", "Number of FileModels: " + subfiles.size());
            node.setChildren(new ArrayList<>());
            for (FileModel fileModel: subfiles) {
                node.addChild(new FileSystemNode(fileModel));
            }
        }
        else {
//            Log.i("FileSystemNavigator", "Node " + node.getData().getFileModel().getFile().getName() + " is not a directory");
            node.setChildren(null);
        }
    }

}