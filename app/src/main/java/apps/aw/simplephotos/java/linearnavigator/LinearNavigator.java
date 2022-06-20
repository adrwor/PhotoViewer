package apps.aw.simplephotos.java.linearnavigator;

import apps.aw.simplephotos.java.Image;

public interface LinearNavigator {

    boolean isImage(int index);

    boolean next();

    boolean previous();

    Image getImage();


}
