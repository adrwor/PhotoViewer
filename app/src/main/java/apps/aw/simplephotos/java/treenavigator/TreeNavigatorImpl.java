package apps.aw.simplephotos.java.treenavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.java.Action;
import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ItemList;
import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.Path;
import apps.aw.simplephotos.java.treenavigator.tree.Node;

/**
 * Class which provides methods to move from directory to directory, as well as list folders and files.
 */
public class TreeNavigatorImpl implements TreeNavigator {

    private static final String TAG = "TreeNavigatorImpl";

    // Note: non-dir files have children set to null,
    //   directories have children set to a list of nodes, which can be empty
    // Also: we have to call getCurrentSubFiles, getParent Subfiles and getContentOfFocusedChildNode
    //   to sync with the underlying file system. -> Can we make this better?
    //      -> do we even need a tree? Or can we just access the files as we visit them?

    private final Node<NodeData> root;
    private final FileMetaDataReader fileMetaDataReader;
    private Node<NodeData> currentNode;

    public TreeNavigatorImpl(Node<NodeData> root, FileMetaDataReader fileMetaDataReader) {
        this.root =  root; // new Node<>(null, new ArrayList<>(), new RootNode("Root"));
        this.fileMetaDataReader = fileMetaDataReader;
        currentNode = root;
    }

    public boolean toParent() {
        Node<NodeData> parentNode = currentNode.getParent();
        if(parentNode != null) {
            currentNode = parentNode;
            return true;
        }
        return false;
    }

    public boolean toFocusedChild() {
        if(getFocusedChildNode().hasAtLeastOneChild()) {
            currentNode = getFocusedChildNode();
            return true;
        }
        return false;
    }

    public boolean setFocus(int position) {
        currentNode.getData().setFocus(position);
        return true; // TODO: return false if position is out of bounds!!!
    }

    public ItemList getParentItems() {
        if(currentNode.getParent() == null) {
            // create a fileList with just the currentNode in it and return it
            ItemList singleRootElement = new ItemList();
            singleRootElement.getFileList().add(currentNode.getData().toString());
            return singleRootElement;
        }
        else {
            return getFileListOfNode(currentNode.getParent());
        }
    }


    public ItemList getItemList() {
        return getFileListOfNode(currentNode);
    }


    public Item getContentOfFocusedChild() {
        Node<NodeData> childNode = getFocusedChildNode();
        if(childNode == null) {
            return null;
        } else {
            NodeData childNodeData = childNode.getData();
            if (childNodeData instanceof FileNode) {
                FileNode childData = (FileNode)childNode.getData();
                if(childData.isDirectory()) {
                    return getFileListOfNode(childNode);
                }
                else {
                    return new Image(childData.getFile(), "date"); // TODO: read correct date for this image
                }
            } else if (childNodeData instanceof ActionNode) {
                return new Action(((ActionNode)childNodeData).action);
            }
        }

        return ItemList.emptyItemList();
    }

    public Path getPathOfFocusedChild() {
        Node<NodeData> focusedChildNode = getFocusedChildNode();
        if(focusedChildNode == null) {
            return new Path();
        } else {
            if(focusedChildNode.getData() instanceof FileNode) {
                File file = ((FileNode)focusedChildNode.getData()).getFile();
                return new Path("", file.getAbsolutePath());
            } else {
                return new Path(focusedChildNode.getData().toString());
            }
        }
    }

    @Override
    public List<Item> getContentOfImageChildren() {
        List<Node<NodeData>> children = currentNode.getChildren();
        List<Item> resultList = new ArrayList<>();
        for (Node<NodeData> child: children) {
            if(child != null) {
                assert(child.getData() instanceof FileNode); // just make sure its not the rootNode
                FileNode data = (FileNode)child.getData();
                if(!data.isDirectory()) {
                    resultList.add(new Image(data.getFile(), "date"));
                }
            }
        }
        return resultList;
    }

    private Node<NodeData> getFocusedChildNode() {
        assert(currentNode.hasAtLeastOneChild());
        return currentNode.getChildren().get(currentNode.getData().getFocus());
    }

    private ItemList getFileListOfNode(Node<NodeData> node) {
        syncNode(node);
        List<Node<NodeData>> children = node.getChildren();
        ItemList subFiles = new ItemList();
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
        // TODO: also load if date of last change of this file is newer than date stored in node!
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
        FileSystemReader fileSystemReader = new FileSystemReader(fileMetaDataReader);
        subfiles = fileSystemReader.getFileNodeList(data.getFile());
        node.setChildren(new ArrayList<>());
        for (FileNode fileNode : subfiles) {
            node.addChildWithData(fileNode);
        }
    }


}