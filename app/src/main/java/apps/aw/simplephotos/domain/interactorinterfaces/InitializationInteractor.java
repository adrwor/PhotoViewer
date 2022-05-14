package apps.aw.simplephotos.domain.interactorinterfaces;

import java.io.File;

import apps.aw.simplephotos.domain.navigator.Navigator;
import apps.aw.simplephotos.executor.Executor;

public class InitializationInteractor implements Initialization {

    private Navigator navigator;
    private Executor executor;

    public InitializationInteractor(Navigator navigator, Executor executor) {
        this.navigator = navigator;
        this.executor = executor;
    }

    @Override
    public void addSubRoot(File file) {
        navigator.addSubRoot(file);
    }
}
