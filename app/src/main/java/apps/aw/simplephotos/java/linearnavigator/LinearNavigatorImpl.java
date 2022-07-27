package apps.aw.simplephotos.java.linearnavigator;

import java.io.File;
import java.util.ArrayList;

import apps.aw.simplephotos.java.Image;

public class LinearNavigatorImpl implements LinearNavigator {

    private final ArrayList<String> paths;
    private int index;

    public LinearNavigatorImpl(ArrayList<String> paths, int index) {
        this.paths = paths;
        this.index = index;
    }

    @Override
    public boolean isImage(int index) {
        // TODO: check if file represented by this path is an image
        return true;
    }

    @Override
    public boolean next() {
        if(index < paths.size() - 1) {
            index++;
            return true;
        }
        return false;
    }

    @Override
    public boolean previous() {
        if(index > 0) {
            index--;
            return true;
        }
        return false;
    }

    @Override
    public Image getImage() {
        return new Image(new File(paths.get(index)), "date");  // TODO: read correct date for this image
    }
}
