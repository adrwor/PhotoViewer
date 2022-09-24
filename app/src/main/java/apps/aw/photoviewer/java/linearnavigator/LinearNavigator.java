package apps.aw.photoviewer.java.linearnavigator;

import apps.aw.photoviewer.java.Image;

public interface LinearNavigator {

    boolean isImage(int index);

    boolean next();

    boolean previous();

    Image getImage();


}
