package apps.aw.simplephotos.java.interactors.shared;

public interface Navigation {

    void execute(NavigationOperation navigationOperation, Callback callback);

    interface Callback {
        void onSuccess(NavigationOperation navigationOperation, Object object);
        void onError();
    }
}
