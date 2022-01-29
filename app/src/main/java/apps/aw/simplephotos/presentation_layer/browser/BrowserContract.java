package apps.aw.simplephotos.presentation_layer.browser;

import apps.aw.simplephotos.presentation_layer.BasePresenter;
import apps.aw.simplephotos.presentation_layer.BaseView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileContentView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;

public interface BrowserContract {

    /**
     * Implemented by the view.
     */
    interface View extends BaseView<Presenter> {

        /**
         * Sets the list of filenames in column 1
         * @param column
         */
        void setColumn1(FileList column);

        /**
         * Sets the list of filenames in column 2
         * @param column
         */
        void setColumn2(FileList column);

        /**
         * Sets the content of column3
         * @param fileContentView
         */
        void setColumn3(FileContentView fileContentView);


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
        //void stop();  //is this necessary?

        void focus(int position);

        /**
         * Called when user navigates to the parent directory
         */
        void toParent();

        /**
         * Called when user navigates to a child file/directory
         */
        void toFocusedChild();


        /**
         * Called when user opens a certain file.
         */
        void openFocusedChild();

    }

}
