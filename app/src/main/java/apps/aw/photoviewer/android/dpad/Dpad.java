package apps.aw.photoviewer.android.dpad;

import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Dpad {
    public final static int UP       = 0;
    public final static int LEFT     = 1;
    public final static int RIGHT    = 2;
    public final static int DOWN     = 3;
    public final static int CENTER   = 4;

    static int directionPressed = -1; // initialized to -1

    public static int getDirectionPressed(InputEvent event) {
        Log.i("Dpad", "InputEvent: " + event.toString());

//        if (!isDpadDevice(event)) {
//            return -1;
//        }

        // If the input event is a MotionEvent, check its hat axis values.
        if (event instanceof MotionEvent) {

            // Use the hat axis value to find the D-pad direction
            MotionEvent motionEvent = (MotionEvent) event;
            float xaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
            float yaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);

            // Check if the AXIS_HAT_X value is -1 or 1, and set the D-pad
            // LEFT and RIGHT direction accordingly.
            if (Float.compare(xaxis, -1.0f) == 0) {
                directionPressed =  Dpad.LEFT;
            } else if (Float.compare(xaxis, 1.0f) == 0) {
                directionPressed =  Dpad.RIGHT;
            }
            // Check if the AXIS_HAT_Y value is -1 or 1, and set the D-pad
            // UP and DOWN direction accordingly.
            else if (Float.compare(yaxis, -1.0f) == 0) {
                directionPressed =  Dpad.UP;
            } else if (Float.compare(yaxis, 1.0f) == 0) {
                directionPressed =  Dpad.DOWN;
            }
        }
        else if (event instanceof KeyEvent) {
            // If the input event is a KeyEvent, check its key code.
            // Use the key code to find the D-pad direction.
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                directionPressed = Dpad.LEFT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                directionPressed = Dpad.RIGHT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                directionPressed = Dpad.UP;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                directionPressed = Dpad.DOWN;
            } else if (
                    keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER ||
                            keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER
            ) {
                directionPressed = Dpad.CENTER;
            } else {
                directionPressed = -1;
            }
            Log.i("Dpad", "directionPressed: " + directionPressed);
        }
        return directionPressed;
    }

    private static boolean isDpadDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_DPAD) != InputDevice.SOURCE_DPAD) {
            return true;
        } else {
            return false;
        }
    }
}