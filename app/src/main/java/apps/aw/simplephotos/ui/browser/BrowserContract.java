package apps.aw.simplephotos.ui.browser;

import apps.aw.simplephotos.ui.BasePresenter;
import apps.aw.simplephotos.ui.BaseView;
import apps.aw.simplephotos.domain.FileContent;
import apps.aw.simplephotos.domain.FileList;

public interface BrowserContract {

    /**
     * Implemented by the view.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Sets the list of filenames in column 1
         * @param column the file list.
         */
        void setColumn1(FileList column);

        /**
         * Sets the list of filenames in column 2
         * @param column The file list.
         */
        void setColumn2(FileList column);

        /**
         * Sets the content of column3
         * @param fileContent The content.
         */
        void setColumn3(FileContent fileContent);

        /**
         * Sets the current path.
         */
        void setPath(String path);

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
         * Called when user opens a certain file.
         */
        void openFocusedChild();

    }

}
