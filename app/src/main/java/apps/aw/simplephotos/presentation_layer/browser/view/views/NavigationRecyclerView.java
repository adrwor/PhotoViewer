package apps.aw.simplephotos.presentation_layer.browser.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.framework_utils.Dpad;

//public class NavigationRecyclerView extends RecyclerView {
//
//    private NavigationKeyHandler navigationKeyHandler;
//
//    public NavigationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public void setKeyPressHandler(NavigationKeyHandler navigationKeyHandler) {
//        this.navigationKeyHandler = navigationKeyHandler;
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Log.i("NavigationRecyclerView", "onKeyUp");
//        switch (Dpad.getDirectionPressed(event)) {
//            case Dpad.UP:
//                navigationKeyHandler.up();
//                return false;   // return true
//            case Dpad.DOWN:
//                navigationKeyHandler.down();
//                return false;   // return true
//            case Dpad.LEFT:
//                navigationKeyHandler.left();
//                return true;
//            case Dpad.RIGHT:
//                navigationKeyHandler.right();
//                return true;
//            case Dpad.CENTER:
//                navigationKeyHandler.enter();
//                return true;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }
//
//    public interface NavigationKeyHandler {
//        void down();
//        void up();
//        void left();
//        void right();
//        void enter();
//    }
//
//}
