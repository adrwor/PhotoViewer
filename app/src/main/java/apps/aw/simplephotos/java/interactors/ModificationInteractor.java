package apps.aw.simplephotos.java.interactors;

import java.io.File;

import apps.aw.simplephotos.java.storagepaths.StoragePath;
import apps.aw.simplephotos.java.storagepaths.StoragePathProvider;
import apps.aw.simplephotos.java.treenavigator.FileNode;
import apps.aw.simplephotos.java.treenavigator.NodeData;
import apps.aw.simplephotos.java.treenavigator.tree.Node;
import apps.aw.simplephotos.java.executor.Executor;
import apps.aw.simplephotos.android.storage.StoragePaths;

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
                rootNode.addChildWithData(
                        new FileNode(path.path, path.name)
                );
            }
            isInitialized = true;
        }
    }

    Boolean isInitialized() {
        return isInitialized;
    }
}
