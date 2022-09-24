package apps.aw.photoviewer.android.ui.browser.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import apps.aw.photoviewer.android.dpad.Dpad;

public class NavigationItemView extends LinearLayout {

    public NavigationItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private NavigationKeyHandler navigationKeyHandler;

    public void setKeyPressHandler(NavigationKeyHandler navigationKeyHandler) {
        this.navigationKeyHandler = navigationKeyHandler;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("NavigationItemView", "onKeyUp");
        switch (Dpad.getDirectionPressed(event)) {
            case Dpad.LEFT:
                navigationKeyHandler.left();
                Log.i("NavigationItemView", "LEFT");
                return true;
            case Dpad.RIGHT:
                navigationKeyHandler.right();
                Log.i("NavigationItemView", "RIGHT");
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    public interface NavigationKeyHandler {
        void left();
        void right();
    }
}
