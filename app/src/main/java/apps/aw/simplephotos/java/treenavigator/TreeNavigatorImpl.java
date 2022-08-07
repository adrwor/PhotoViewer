package apps.aw.simplephotos.java.treenavigator;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.java.Action;
import apps.aw.simplephotos.java.ColumnViewData;
import apps.aw.simplephotos.java.ListItem;
import apps.aw.simplephotos.java.ItemListWithFocus;
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
    private final NodeComparator nodeComparator;
    private Node<NodeData> currentNode; // node that is displayed in column 1

    public TreeNavigatorImpl(
            Node<NodeData> root,
            FileMetaDataReader fileMetaDataReader,
            NodeComparator nodeComparator
    ) {
        this.root =  root; // new Node<>(null, new ArrayList<>(), new RootNode("Root"));
        this.fileMetaDataReader = fileMetaDataReader;
        this.nodeComparator = nodeComparator;
        currentNode = this.root;
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

    public ItemListWithFocus getParentItems() {
        if(currentNode.getParent() == null) {
            // parent is null, i.e. currentNode is the root Node
            // create a fileList with just the currentNode (the rootNode) in it and return it
            ItemListWithFocus singleRootElement = new ItemListWithFocus();
            singleRootElement.getItemList().add(
                    new ListItem(
                            currentNode.getData().getName(),
                            ListItem.Type.ROOT
                    )
            );
            return singleRootElement;
        }
        else {
            return getItemListOfNode(currentNode.getParent());
        }
    }


    public ItemListWithFocus getItemList() {
        return getItemListOfNode(currentNode);
    }


    public ColumnViewData getContentOfFocusedChild() {
        Node<NodeData> childNode = getFocusedChildNode();
        if(childNode == null) {
            return null;
        } else {
            NodeData childNodeData = childNode.getData();
            if (childNodeData instanceof FileNode) {
                FileNode childData = (FileNode)childNode.getData();
                if(childData.isDirectory()) {
                    return getItemListOfNode(childNode);
                }
                else {
                    Log.i(TAG, "getContentOfFocusedChild: date: " + childData.getMetaData().originalDateTime);
                    return new Image(childData.getFile(), childData.getMetaData().originalDateTime);
                }
            } else if (childNodeData instanceof ActionNode) {
                return new Action(((ActionNode)childNodeData).action);
            }
        }

        return ItemListWithFocus.emptyItemList();
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
                return new Path(focusedChildNode.getData().getName());
            }
        }
    }

    @Override
    public List<FileNode> getImageChildren() {
        List<Node<NodeData>> children = currentNode.getChildren();
        List<FileNode> resultList = new ArrayList<>();
        for (Node<NodeData> child: children) {
                NodeData data = child.getData();
                if(ListItem.ofNodeData(data).type == ListItem.Type.IMAGE) {
                    assert data instanceof FileNode; // should be guaranteed by the above condition
                    resultList.add((FileNode) data);
                }
        }
        return resultList;
    }

    @Override
    public NodeData getFocusedNodeData() {
        return getFocusedChildNode().getData();
    }

    @Override
    public void resyncFocusedNode() {
        syncNode(getFocusedChildNode(), true);
    }

    private Node<NodeData> getFocusedChildNode() {
        assert(currentNode.hasAtLeastOneChild());
        return currentNode.getChildren().get(currentNode.getData().getFocus());
    }

    private ItemListWithFocus getItemListOfNode(Node<NodeData> node) {
        syncNode(node, false);
        List<Node<NodeData>> children = node.getChildren();
        ItemListWithFocus itemListWithFocus = new ItemListWithFocus();
        for (Node<NodeData> child: children) {
            itemListWithFocus.getItemList().add(ListItem.ofNodeData(child.getData()));
        }
        itemListWithFocus.setFocus(node.getData().getFocus());
        return itemListWithFocus;
    }


    /**
     * If the given node denotes a directory, the subFiles are loaded from disk and stored in the
     * tree. If the given node is not a directory, nothing happens.
     * @param node The node
     */
    private void syncNode(Node<NodeData> node, boolean force) {
        if(!(node.getData() instanceof FileNode)) {
            return; // only FileNodes represent actual files in the filesystem
        }
        // only load if forced, or if file is directory and children are null
        // TODO: maybe also load if date of last change of this file is newer than date stored in node!
        if(force || (node.getData().isDirectory() && node.getChildren() == null)) {
            syncNodeWithDisk(node);
        }
    }

    /**
     * Sets the children of the given node, representing the corresponding file
     * in the filesystem.
     * @param node The node
     */
    private void syncNodeWithDisk(Node<NodeData> node) {
        assert(node.getData() instanceof FileNode);
        FileNode data = (FileNode)node.getData();
        data.setFocus(0); // reset focus
        // TODO: sync, even if it is a file, and then somehow update view
        if(data.getFile().isDirectory()) {
            data.setIsDirectory(true);
            FileSystemReader fileSystemReader = new FileSystemReader(fileMetaDataReader);
            List<FileNode> subfiles;
            subfiles = fileSystemReader.getFileNodeList(data.getFile());
            node.setChildren(new ArrayList<>());
            for (FileNode fileNode : subfiles) {
                node.addChildWithData(fileNode);
            }
            if(node.sortChildren) {
                node.sortChildren(nodeComparator);
            }
        } else {
            data.setIsDirectory(false);
            node.setChildren(null);
            FileMetaData fileMetaData = fileMetaDataReader.readFileMetaData(data.getFile());
            data.setMetaData(fileMetaData);
        }
    }


}