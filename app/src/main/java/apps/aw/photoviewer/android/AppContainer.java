package apps.aw.photoviewer.android;

import android.content.Context;

import java.util.ArrayList;

import apps.aw.photoviewer.android.storage.ImageFileExifReader;
import apps.aw.photoviewer.android.storage.StoragePaths;
import apps.aw.photoviewer.java.interactors.shared.Navigation;
import apps.aw.photoviewer.java.interactors.tree.Modification;
import apps.aw.photoviewer.java.interactors.tree.ModificationInteractor;
import apps.aw.photoviewer.java.interactors.tree.TreeNavigationInteractor;
import apps.aw.photoviewer.java.storagepath.StoragePathProvider;
import apps.aw.photoviewer.java.treenavigator.FileMetaDataReader;
import apps.aw.photoviewer.java.treenavigator.NodeComparator;
import apps.aw.photoviewer.java.treenavigator.NodeData;
import apps.aw.photoviewer.java.treenavigator.RootNode;
import apps.aw.photoviewer.java.treenavigator.TreeNavigator;
import apps.aw.photoviewer.java.treenavigator.TreeNavigatorImpl;
import apps.aw.photoviewer.java.treenavigator.tree.Node;
import apps.aw.photoviewer.java.executor.Executor;
import apps.aw.photoviewer.java.executor.MainThread;
import apps.aw.photoviewer.android.executor.MainThreadImpl;
import apps.aw.photoviewer.android.executor.ThreadExecutor;

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
