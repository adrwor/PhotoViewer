package apps.aw.simplephotos.java.presenters.browser;

import java.util.ArrayList;

import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ItemList;
import apps.aw.simplephotos.java.Path;
import apps.aw.simplephotos.java.presenters.BasePresenter;
import apps.aw.simplephotos.java.presenters.BaseView;

public interface BrowserContract {

    /**
     * Implemented by the view.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Sets the list of filenames in column 1
         * @param column the file list.
         */
        void setColumn1(ItemList column);

        /**
         * Sets the list of filenames in column 2
         * @param column The file list.
         */
        void setColumn2(ItemList column);

        /**
         * Sets the content of column3
         * @param item The content.
         */
        void setColumn3(Item item);

        /**
         * Sets the current path.
         */
        void setPath(Path path);

        /**
         * Tells view to show the FullImage View
         */
        void showFullImageView(ArrayList<String> list, int current);

        void openSystemFilePicker();

        /**
         * True if view is active.
         * @return true if view is active
         */
        boolean isActive();
    }



    /**
     * Implemented by the presenter.
     */
    interface Presenter extends BasePresenter {

        void focus(int index);

        /**
         * Called when user navigates to the parent directory
         */
        void toParent();

        /**
         * Called when user navigates to a child file/directory
         */
        void toChild();

        /**
         * Called when user pressed enter on this item
         */
        void open(int index);

        void addSubRoot(String path);

    }

}
