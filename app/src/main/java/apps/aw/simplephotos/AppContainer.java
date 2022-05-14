package apps.aw.simplephotos;

import apps.aw.simplephotos.domain.Navigation;
import apps.aw.simplephotos.domain.interactorinterfaces.Initialization;
import apps.aw.simplephotos.domain.interactorinterfaces.InitializationInteractor;
import apps.aw.simplephotos.domain.interactorinterfaces.NavigationInteractor;
import apps.aw.simplephotos.domain.navigator.Navigator;
import apps.aw.simplephotos.domain.navigator.cachednavigator.NodeTreeNavigator;
import apps.aw.simplephotos.executor.Executor;
import apps.aw.simplephotos.executor.MainThread;
import apps.aw.simplephotos.executor.MainThreadImpl;
import apps.aw.simplephotos.executor.ThreadExecutor;

public class AppContainer {

    private final Navigator navigator = new NodeTreeNavigator();
    private final Executor executor = new ThreadExecutor();
    private final MainThread mainThread = new MainThreadImpl();

    public Navigation navigation = new NavigationInteractor(navigator, executor, mainThread);
    public Initialization initialization = new InitializationInteractor(navigator, executor);

}
