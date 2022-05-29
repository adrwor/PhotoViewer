package apps.aw.simplephotos.java.interactors;

import apps.aw.simplephotos.java.ViewData;

public interface Navigation {

    void execute(NavigationOperation navigationOperation, Callback callback);

    interface Callback {
        void onSuccess(ViewData viewData);
        void onError();
    }
}
