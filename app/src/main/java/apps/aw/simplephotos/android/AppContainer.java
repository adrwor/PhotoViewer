package apps.aw.simplephotos.android;

import android.content.Context;

import java.util.ArrayList;

import apps.aw.simplephotos.android.storage.ImageFileExifReader;
import apps.aw.simplephotos.android.storage.StoragePaths;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.interactors.tree.Modification;
import apps.aw.simplephotos.java.interactors.tree.ModificationInteractor;
import apps.aw.simplephotos.java.interactors.tree.TreeNavigationInteractor;
import apps.aw.simplephotos.java.storagepath.StoragePathProvider;
import apps.aw.simplephotos.java.treenavigator.FileMetaDataReader;
import apps.aw.simplephotos.java.treenavigator.NodeComparator;
import apps.aw.simplephotos.java.treenavigator.NodeData;
import apps.aw.simplephotos.java.treenavigator.RootNode;
import apps.aw.simplephotos.java.treenavigator.TreeNavigator;
import apps.aw.simplephotos.java.treenavigator.TreeNavigatorImpl;
import apps.aw.simplephotos.java.treenavigator.tree.Node;
import apps.aw.simplephotos.java.executor.Executor;
import apps.aw.simplephotos.java.executor.MainThread;
import apps.aw.simplephotos.android.executor.MainThreadImpl;
import apps.aw.simplephotos.android.executor.ThreadExecutor;

public class AppContainer {

    public Navigation navigation;
    public Modification modification;

    public AppContainer(Context context) {
        Node<NodeData> rootNode = new Node<>(
                null,
                new ArrayList<>(),
                new RootNode("Android TV Box"),
                false
        );
        FileMetaDataReader fileMetaDataReader = new ImageFileExifReader();
        NodeComparator nodeComparator = new NodeComparator();
        TreeNavigator treeNavigator = new TreeNavigatorImpl(rootNode, fileMetaDataReader, nodeComparator);
        Executor executor = new ThreadExecutor();
        MainThread mainThread = new MainThreadImpl();
        StoragePathProvider storagePathProvider = new StoragePaths(context);

        navigation = new TreeNavigationInteractor(treeNavigator, executor, mainThread);
        modification = new ModificationInteractor(rootNode, storagePathProvider, executor);
    }

}
