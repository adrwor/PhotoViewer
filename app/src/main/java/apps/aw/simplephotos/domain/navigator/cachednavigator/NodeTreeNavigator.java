package apps.aw.simplephotos.domain.navigator.cachednavigator;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.domain.FileContent;
import apps.aw.simplephotos.domain.FileList;
import apps.aw.simplephotos.domain.ImagePreview;
import apps.aw.simplephotos.domain.navigator.Navigator;
import apps.aw.simplephotos.domain.navigator.cachednavigator.Tree.Node;

/**
 * Class which provides methods to move from directory to directory, as well as list folders and files.
 */
public class NodeTreeNavigator implements Navigator {

    private static final String TAG = "NodeTreeNavigator";

    // Note: non-dir files have children set to null,
    //   directories have children set to a list of nodes, which can be empty
    // Also: we have to call getCurrentSubFiles, getParent Subfiles and getContentOfFocusedChildNode
    //   to sync with the underlying file system. -> Can we make this better?
    //      -> do we even need a tree? Or can we just access the files as we visit them?

    private final Node<NodeData> root;
    private Node<NodeData> currentNode;

    public NodeTreeNavigator() {
        root =  new Node<>(null, new ArrayList<>(), new RootNode("Root"));
        currentNode = root;
    }

    @Override
    public void addSubRoot(File file) {
        Log.i(TAG, "addSubRoot(): " + file.getName());
        root.addChildWithData(new FileNode(file));
    }

    public boolean toParent() {
        Node<NodeData> parentNode = currentNode.getParent();
        if(parentNode != null) {
            currentNode = parentNode;
            return true;
        }
        return false;
    }

    public boolean toChild() {
        assert(currentNode.hasAtLeastOneChild());
        if(getFocusedChildNode().hasAtLeastOneChild()) { // it must be impossible that the current node has no children!
            currentNode = getFocusedChildNode();
            return true;
        }
        return false;
    }

    public boolean setFocus(int position) {
        currentNode.getData().setFocus(position);
        return true; // TODO: return false if position is out of bounds!!!
    }

    public FileList getParentSubFiles() {
        if(currentNode.getParent() == null) {
            // create a fileList with just the currentNode in it and return it
            FileList singleRootElement = new FileList();
            singleRootElement.getFileList().add(currentNode.getData().toString());
            return singleRootElement;
        }
        else {
            return getFileListOfNode(currentNode.getParent());
        }
    }


    public FileList getCurrentSubFiles() {
        return getFileListOfNode(currentNode);
    }


    public FileContent getContentOfFocusedChildNode() {
        Node<NodeData> childNode = getFocusedChildNode();
        if(childNode == null) {
            return null;
        } else {
            assert(childNode.getData() instanceof FileNode);
            FileNode childData = (FileNode)childNode.getData();
            if(childData.isDirectory()) {
                return getFileListOfNode(childNode);
            }
            else {
                return new ImagePreview(childData.getFile());
            }
        }

    }

    public String getPath() {
        if(currentNode.getData() instanceof FileNode) {
            return ((FileNode)currentNode.getData()).getFile().getAbsolutePath();
        }
        return currentNode.getData().toString();
    }

    private Node<NodeData> getFocusedChildNode() {
        if(currentNode.getChildren().isEmpty()) {
            return null;
        } else {
            return currentNode.getChildren().get(currentNode.getData().getFocus());
        }
    }

    private FileList getFileListOfNode(Node<NodeData> node) {
        syncNode(node);
        List<Node<NodeData>> children = node.getChildren();
        FileList subFiles = new FileList();
        for (Node<NodeData> child: children) {
            subFiles.getFileList().add(child.getData().toString());
        }
        subFiles.setFocus(node.getData().getFocus());
        return subFiles;
    }


    /**
     * If the given node denotes a directory, the subFiles are loaded from disk and stored in the
     * tree. If the given node is not a directory, nothing happens.
     * @param node The node
     */
    private void syncNode(Node<NodeData> node) {
        if((node.getData() instanceof RootNode)) {
            return; // don't sync rootnode, only FileNodes represent actual files in the filesystem
        }
        // only load if file is directory and children are not yet loaded
        if(node.getData().isDirectory() && node.getChildren() == null) {
            syncDirectoryNodeWithDisk(node);
        }
    }

    /**
     * Sets the children of the given node, representing the corresponding directory in the filesystem.
     * @param node The node
     */
    private void syncDirectoryNodeWithDisk(Node<NodeData> node) {
        assert(node.getData() instanceof FileNode);
        assert(node.getData().isDirectory());
        FileNode data = (FileNode)node.getData();
        List<FileNode> subfiles;
        subfiles = FileSystemReader.getFileNodeList(data.getFile());
        node.setChildren(new ArrayList<>());
        for (FileNode fileNode : subfiles) {
            node.addChildWithData(fileNode);
        }
    }


}