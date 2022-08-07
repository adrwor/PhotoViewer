package apps.aw.simplephotos.java.presenters.browser;

import java.util.ArrayList;

import apps.aw.simplephotos.java.ColumnViewData;
import apps.aw.simplephotos.java.ItemListWithFocus;
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
        void setColumn1(ItemListWithFocus column);

        /**
         * Sets the list of filenames in column 2
         * @param column The file list.
         */
        void setColumn2(ItemListWithFocus column);

        /**
         * Sets the content of column3
         * @param columnViewData The content.
         */
        void setColumn3(ColumnViewData columnViewData);

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
        void enter();

        void addSubRoot(String path);

    }

}
