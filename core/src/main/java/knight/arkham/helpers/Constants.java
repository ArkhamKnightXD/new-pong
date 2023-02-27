package knight.arkham.helpers;

import knight.arkham.Pong;

public class Constants {

    public static final float PIXELS_PER_METER = 32.0f;

    public static final int FULL_SCREEN_HEIGHT = Pong.INSTANCE.getScreenHeight();
    public static final float BOX2D_FULL_SCREEN_HEIGHT = Pong.INSTANCE.getScreenHeight() / PIXELS_PER_METER;
    public static final int FULL_SCREEN_WIDTH = Pong.INSTANCE.getScreenWidth();
    public static final float BOX2D_FULL_SCREEN_WIDTH = Pong.INSTANCE.getScreenWidth() / PIXELS_PER_METER;
    public static final int MID_SCREEN_HEIGHT = Pong.INSTANCE.getScreenHeight() / 2;
    public static final int MID_SCREEN_WIDTH = Pong.INSTANCE.getScreenWidth() / 2;
}
