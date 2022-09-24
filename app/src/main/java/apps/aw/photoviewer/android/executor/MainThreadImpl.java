package apps.aw.photoviewer.android.executor;

import android.os.Handler;
import android.os.Looper;

import apps.aw.photoviewer.java.executor.MainThread;

public class MainThreadImpl implements MainThread {

    private final Handler handler;

    public MainThreadImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        runnable.run();
//        handler.post(runnable);
    }
}
