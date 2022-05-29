package apps.aw.simplephotos.android.executor;

import android.os.Handler;
import android.os.Looper;

import apps.aw.simplephotos.java.executor.MainThread;

public class MainThreadImpl implements MainThread {

    private final Handler handler;

    public MainThreadImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
