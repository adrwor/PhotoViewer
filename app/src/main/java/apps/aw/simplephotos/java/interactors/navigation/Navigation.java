package apps.aw.simplephotos.java.interactors.navigation;

public interface Navigation {

    void execute(NavigationOperation navigationOperation, Callback callback);

    interface Callback {
        void onSuccess(Object object);
        void onError();
    }
}
