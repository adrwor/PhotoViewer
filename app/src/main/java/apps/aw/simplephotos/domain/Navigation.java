package apps.aw.simplephotos.domain;


import apps.aw.simplephotos.domain.ViewData;
import apps.aw.simplephotos.domain.interactorinterfaces.NavigationChange;

public interface Navigation {

    void execute(NavigationChange navigationChange, Callback callback);

    interface Callback {
        void onSuccess(ViewData viewData);
        void onError();
    }
}
