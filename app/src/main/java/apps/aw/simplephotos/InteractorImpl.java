package apps.aw.simplephotos;


import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.domain_layer.model.FileModel;
import apps.aw.simplephotos.domain_layer.model.FileSystemNavigator;
import apps.aw.simplephotos.domain_layer.model.FileSystemNode;
import apps.aw.simplephotos.domain_layer.model.Tree.Node;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileContentView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;
import apps.aw.simplephotos.presentation_layer.browser.utils.ImagePreview;

public class InteractorImpl implements Interactor {

    private final FileSystemNavigator fileSystemNavigator;

    /**
     * Constructor
     */
    public InteractorImpl() {
        // TODO: maybe have multiple flieSystemNavigators? (with different roots, and we can switch between them)
        File root = new File("/storage/emulated/0/");
        this.fileSystemNavigator = new FileSystemNavigator(new FileModel(root));
    }

    @Override
    public String getCurrentPath() {
        return fileSystemNavigator.getCurrentNode().getData().getFileModel().getFile().getAbsolutePath();
    }


    @Override
    public void toParent() {
        fileSystemNavigator.goToParentNode();
    }

    @Override
    public void focusChild(int position) {
        fileSystemNavigator.focusChildNode(position);
    }

    public int getFocusedChildIndex() {
        return fileSystemNavigator.getCurrentNode().getData().getFocusedChildPosition();
    }

    @Override
    public void toFocusedChild() {
        fileSystemNavigator.goToFocusedChildNode();
    }

    @Override
    public void focusNextChild() {
        fileSystemNavigator.focusNextChildNode();
    }

    @Override
    public void focusPreviousChild() {
        fileSystemNavigator.focusPreviousChildNode();
    }

    @Override
    public FileList getParentSubFiles() {
        Node<FileSystemNode> parent = fileSystemNavigator.getParentNode();
        if(parent != null) {
            return getSubFilesOfNode(parent);
        }
        else {
            List<String> names = new ArrayList<>();
            names.add(fileSystemNavigator.getCurrentNode().getData().getFileModel().getFile().getName());
            return new FileList(names, 0);
        }
    }

    @Override
    public FileList getSubFiles() {
        return getSubFilesOfNode(fileSystemNavigator.getCurrentNode());
    }

    @Override
    public FileContentView getContentOfFocusedChild() {
        if(!fileSystemNavigator.currentNodeHasChildren()) {
            return null;    // display e.g. "empty"
        }
        Node<FileSystemNode> child = fileSystemNavigator.getFocusedChildNode();
        if(child.getData().getFileModel().isDirectory()) {
            FileList fileList = getSubFilesOfNode(child);
            return new FileContentView(fileList);
        }
        else {
            File file = fileSystemNavigator.getFocusedChildNode().getData().getFileModel().getFile();
            ImagePreview imagePreview = new ImagePreview(file);
            return new FileContentView(imagePreview);
        }
        // TODO: what should we return if the file is not an imagefile?
    }

    private FileList getSubFilesOfNode(Node<FileSystemNode> node) {
        List<Node<FileSystemNode>> children = fileSystemNavigator.getChildrenOfNode(node);
        if(children == null) {
            return new FileList();
        }
        List<String> names = new ArrayList<>();
        for(Node<FileSystemNode> subNode : children) {
            names.add(subNode.getData().getFileModel().getFile().getName());
        }
        int focus = node.getData().getFocusedChildPosition();
        return new FileList(names, focus);
    }
}
