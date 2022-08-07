package apps.aw.simplephotos.java.interactors.tree;

import java.io.File;

import apps.aw.simplephotos.java.actions.Actions;
import apps.aw.simplephotos.java.storagepath.StoragePath;
import apps.aw.simplephotos.java.storagepath.StoragePathProvider;
import apps.aw.simplephotos.java.treenavigator.ActionNode;
import apps.aw.simplephotos.java.treenavigator.FileMetaData;
import apps.aw.simplephotos.java.treenavigator.FileNode;
import apps.aw.simplephotos.java.treenavigator.NodeData;
import apps.aw.simplephotos.java.treenavigator.tree.Node;
import apps.aw.simplephotos.java.executor.Executor;

public class ModificationInteractor implements Modification {

    private final Node<NodeData> rootNode;
    private final StoragePathProvider storagePathProvider;
    private Executor executor;

    private Boolean isInitialized;

    public ModificationInteractor(Node<NodeData> rootNode, StoragePathProvider storagePathProvider, Executor executor) {
        this.rootNode = rootNode;
        this.storagePathProvider = storagePathProvider;
        this.executor = executor;
        this.isInitialized = false;
    }

    @Override
    public void initialize() {
        if (!isInitialized()) {
            for(StoragePath path : storagePathProvider.getPaths()) {
                rootNode.addChildWithData(new FileNode(path.path, path.name, true, new FileMetaData()));
            }
            // TODO: add actions that make sense, e.g. Preferences, Adding Location, ...
            // (File system picker does not exist in Android TV Boxes!)
//            rootNode.addChildWithData(new ActionNode("Add Location", Actions.OPEN_SYSTEM_FILE_PICKER));
            isInitialized = true;
        }
    }

    @Override
    public void addSubRoot(String path) {
        // this should insert the path at the second-last position (so that the "+" is always last)
        rootNode.addChildWithData(
                new FileNode(new File(path), path, true, new FileMetaData()),
                rootNode.getChildren().size() - 1
        );
    }

    Boolean isInitialized() {
        return isInitialized;
    }
}
