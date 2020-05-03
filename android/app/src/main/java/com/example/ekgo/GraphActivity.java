package ekgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.content.ContextCompat;

public class GraphActivity extends View {
    Paint dataPaint;
    Paint gridPaint;
    Paint miniPaint;
    Paint textPaint;
    boolean mShowText;
    int mTextWidth = 10;
    float textHeight = 25;
    double[] fy = {-0.145,-0.145,-0.145,-0.145,-0.145,-0.145,-0.145,-0.145,-0.12,-0.135,-0.145,-0.15,-0.16,-0.155,-0.16,-0.175,-0.18,-0.185,-0.17,-0.155,-0.175,-0.18,-0.19,-0.18,-0.155,-0.135,-0.155,-0.19,-0.205,-0.235,-0.225,-0.245,-0.25,-0.26,-0.275,-0.275,-0.275,-0.265,-0.255,-0.265,-0.275,-0.29,-0.29,-0.29,-0.29,-0.285,-0.295,-0.305,-0.285,-0.275,-0.275,-0.28,-0.285,-0.305,-0.29,-0.3,-0.28,-0.29,-0.3,-0.315,-0.32,-0.335,-0.36,-0.385,-0.385,-0.405,-0.455,-0.485,-0.485,-0.425,-0.33,-0.22,-0.07,0.12,0.375,0.62,0.78,0.84,0.765,0.52,0.17,-0.165,-0.365,-0.435,-0.425,-0.37,-0.33,-0.325,-0.335,-0.345,-0.33,-0.325,-0.315,-0.31,-0.32,-0.335,-0.34,-0.325,-0.345,-0.335,-0.33,-0.335,-0.33,-0.325,-0.33,-0.33,-0.345,-0.355,-0.335,-0.325,-0.305,-0.32,-0.32,-0.33,-0.34,-0.335,-0.34,-0.345,-0.355,-0.355,-0.34,-0.33,-0.33,-0.33,-0.34,-0.35,-0.325,-0.325,-0.33,-0.33,-0.335,-0.335,-0.34,-0.33,-0.34,-0.35,-0.355,-0.35,-0.345,-0.33,-0.32,-0.335,-0.33,-0.345,-0.33,-0.335,-0.335,-0.345,-0.345,-0.355,-0.34,-0.34,-0.335,-0.33,-0.35,-0.35,-0.345,-0.335,-0.335,-0.335,-0.35,-0.355,-0.355,-0.345,-0.345,-0.335,-0.35,-0.36,-0.36,-0.36,-0.365,-0.36,-0.37,-0.385,-0.37,-0.36,-0.355,-0.36,-0.375,-0.375,-0.365,-0.365,-0.36,-0.36,-0.365,-0.37,-0.355,-0.33,-0.325,-0.325,-0.335,-0.34,-0.315,-0.3,-0.3,-0.29,-0.295,-0.29,-0.285,-0.275,-0.255,-0.25,-0.25,-0.265,-0.255,-0.245,-0.23,-0.245,-0.245,-0.255,-0.255,-0.24,-0.25,-0.255,-0.245,-0.255,-0.25,-0.25,-0.265,-0.26,-0.26,-0.265,-0.27,-0.265,-0.26,-0.275,-0.28,-0.29,-0.275,-0.27,-0.26,-0.28,-0.28,-0.285,-0.275,-0.275,-0.265,-0.27,-0.285,-0.29,-0.28,-0.275,-0.285,-0.28,-0.3,-0.3,-0.305,-0.295,-0.3,-0.31,-0.31,-0.305,-0.295,-0.285,-0.285,-0.29,-0.295,-0.31,-0.29,-0.295,-0.3,-0.305,-0.31,-0.325,-0.31,-0.3,-0.29,-0.31,-0.325,-0.33,-0.315,-0.3,-0.305,-0.31,-0.32,-0.33,-0.325,-0.315,-0.31,-0.305,-0.305,-0.31,-0.3,-0.305,-0.29,-0.3,-0.3,-0.305,-0.305,-0.29,-0.28,-0.295,-0.305,-0.315,-0.305,-0.295,-0.29,-0.28,-0.27,-0.275,-0.275,-0.27,-0.25,-0.25,-0.255,-0.225,-0.22,-0.205,-0.2,-0.205,-0.215,-0.23,-0.22,-0.225,-0.225,-0.225,-0.23,-0.235,-0.24,-0.235,-0.22,-0.21,-0.205,-0.245,-0.285,-0.285,-0.3,-0.31,-0.33,-0.33,-0.325,-0.315,-0.32,-0.315,-0.325,-0.34,-0.345,-0.34,-0.34,-0.35,-0.345,-0.355,-0.33,-0.335,-0.33,-0.32,-0.345,-0.355,-0.34,-0.33,-0.325,-0.33,-0.35,-0.365,-0.36,-0.38,-0.425,-0.445,-0.475,-0.51,-0.535,-0.505,-0.415,-0.3,-0.16,-0.015,0.235,0.49,0.72,0.875,0.94,0.905,0.755,0.49,0.165,-0.11,-0.27,-0.39,-0.45,-0.475,-0.455,-0.425,-0.39,-0.39,-0.385,-0.39,-0.38,-0.38,-0.38,-0.395,-0.385,-0.385,-0.385,-0.375,-0.395,-0.41,-0.41,-0.4,-0.395,-0.39,-0.405,-0.395,-0.385,-0.375,-0.39,-0.39,-0.405,-0.41,-0.41,-0.39,-0.39,-0.395,-0.405,-0.415,-0.4,-0.41,-0.405,-0.41,-0.415,-0.41,-0.4,-0.4,-0.395,-0.39,-0.405,-0.41,-0.39,-0.39,-0.385,-0.385,-0.41,-0.405,-0.395,-0.39,-0.375,-0.39,-0.395,-0.41,-0.4,-0.39,-0.39,-0.385,-0.405,-0.415,-0.415,-0.4,-0.395,-0.405,-0.415,-0.42,-0.42,-0.41,-0.415,-0.425,-0.42,-0.435,-0.43,-0.43,-0.42,-0.43,-0.45,-0.455,-0.45,-0.435,-0.445,-0.45,-0.455,-0.47,-0.46,-0.455,-0.45,-0.455,-0.47,-0.475,-0.46,-0.45,-0.445,-0.44,-0.435,-0.44,-0.41,-0.395,-0.37,-0.365,-0.36,-0.365,-0.34,-0.325,-0.315,-0.32,-0.33,-0.33,-0.32,-0.31,-0.3,-0.3,-0.32,-0.32,-0.315,-0.305,-0.305,-0.295,-0.32,-0.33,-0.305,-0.31,-0.3,-0.3,-0.32,-0.325,-0.31,-0.305,-0.315,-0.305,-0.315,-0.315,-0.31,-0.295,-0.29,-0.305,-0.31,-0.32,-0.315,-0.3,-0.315,-0.315,-0.315,-0.33,-0.315,-0.32,-0.315,-0.325,-0.335,-0.34,-0.335,-0.335,-0.33,-0.325,-0.345,-0.35,-0.345,-0.335,-0.33,-0.33,-0.345,-0.345,-0.345,-0.32,-0.33,-0.335,-0.34,-0.355,-0.335,-0.33,-0.33,-0.335,-0.355,-0.36,-0.355,-0.35,-0.34,-0.345,-0.345,-0.345,-0.345,-0.33,-0.33,-0.335,-0.345,-0.35,-0.35,-0.34,-0.33,-0.345,-0.345,-0.355,-0.35,-0.34,-0.33,-0.34,-0.34,-0.34,-0.33,-0.335,-0.33,-0.335,-0.345,-0.345,-0.34,-0.33,-0.315,-0.295,-0.3,-0.295,-0.285,-0.275,-0.265,-0.265,-0.265,-0.255,-0.25,-0.24,-0.225,-0.215,-0.24,-0.245,-0.24,-0.245,-0.235,-0.245,-0.25,-0.275,-0.275,-0.265,-0.25,-0.225,-0.22,-0.23,-0.265,-0.27,-0.28,-0.285,-0.305,-0.32,-0.34,-0.33,-0.335,-0.335,-0.355,-0.37,-0.36,-0.345,-0.35,-0.355,-0.365,-0.375,-0.38,-0.37,-0.365,-0.365,-0.38,-0.385,-0.38,-0.375,-0.355,-0.37,-0.39,-0.405,-0.41,-0.435,-0.465,-0.49,-0.52,-0.555,-0.57,-0.525,-0.405,-0.25,-0.09,0.12,0.41,0.69,0.885,0.96,0.85,0.52,0.05,-0.32,-0.5,-0.505,-0.445,-0.415,-0.395,-0.39,-0.395,-0.39,-0.395,-0.405,-0.395,-0.405,-0.39,-0.395,-0.395,-0.41,-0.405,-0.4,-0.4,-0.415,-0.405,-0.42,-0.415,-0.405,-0.405,-0.42,-0.42,-0.435,-0.42,-0.41,-0.41,-0.405,-0.425,-0.43,-0.42,-0.415,-0.41,-0.41,-0.425,-0.42,-0.4,-0.395,-0.4,-0.4,-0.395,-0.415,-0.405,-0.39,-0.4,-0.4,-0.415,-0.425,-0.425,-0.415,-0.415,-0.41,-0.425,-0.425,-0.42,-0.415,-0.4,-0.4,-0.41,-0.425,-0.41,-0.405,-0.395,-0.4,-0.4,-0.405,-0.385,-0.4,-0.39,-0.39,-0.4,-0.415,-0.405,-0.405,-0.41,-0.415,-0.42,-0.42,-0.425,-0.415,-0.415,-0.41,-0.435,-0.43,-0.435,-0.42,-0.42,-0.42,-0.42,-0.425,-0.425,-0.415,-0.395,-0.395,-0.39,-0.375,-0.36,-0.34,-0.335,-0.33,-0.335,-0.335,-0.325,-0.32,-0.305,-0.305,-0.315,-0.31,-0.305,-0.3,-0.3,-0.3,-0.31,-0.31,-0.31,-0.315,-0.3,-0.305,-0.315,-0.32,-0.325,-0.31,-0.305,-0.305,-0.32,-0.325,-0.305,-0.31,-0.295,-0.31,-0.325,-0.33,-0.32,-0.32,-0.32,-0.325,-0.35,-0.35,-0.34,-0.335,-0.33,-0.335,-0.34,-0.36,-0.35,-0.35,-0.355,-0.36,-0.355,-0.365,-0.355,-0.35,-0.34,-0.355,-0.35,-0.365,-0.365,-0.35,-0.355,-0.35,-0.37,-0.38,-0.37,-0.36,-0.365,-0.36,-0.365,-0.38,-0.37,-0.365,-0.35,-0.35,-0.35,-0.37,-0.365,-0.345,-0.345,-0.34,-0.355,-0.365,-0.36,-0.355,-0.355,-0.355,-0.375,-0.385,-0.355,-0.36,-0.355,-0.36,-0.36,-0.37,-0.36,-0.345,-0.355,-0.345,-0.345,-0.345,-0.34,-0.31,-0.305,-0.305,-0.31,-0.305,-0.29,-0.285,-0.275,-0.28,-0.28,-0.275,-0.265,-0.255,-0.245,-0.26,-0.265,-0.275,-0.28,-0.265,-0.27,-0.275,-0.28,-0.275,-0.28,-0.275,-0.265,-0.255,-0.24,-0.26,-0.28,-0.31,-0.31,-0.345,-0.36,-0.38,-0.385,-0.38,-0.37,-0.375,-0.385,-0.39,-0.395,-0.385,-0.385,-0.385,-0.395,-0.405,-0.4,-0.375,-0.375,-0.39,-0.395,-0.415,-0.405,-0.4,-0.39,-0.405,-0.425,-0.46,-0.475,-0.505,-0.51,-0.525,-0.54,-0.6,-0.645,-0.63,-0.575,-0.495,-0.385,-0.27,-0.115,0.09,0.37,0.635,0.81,0.86,0.77,0.475,0.065,-0.305,-0.5,-0.52,-0.475,-0.41,-0.39,-0.39,-0.4,-0.41,-0.405,-0.385,-0.39,-0.395,-0.405,-0.41,-0.405,-0.4,-0.39,-0.395,-0.41,-0.415,-0.41,-0.395,-0.405,-0.41,-0.405,-0.405,-0.41,-0.39,-0.385,-0.39,-0.395,-0.4,-0.4,-0.4,-0.395,-0.395,-0.405,-0.405,-0.4,-0.395,-0.385,-0.375,-0.385,-0.395,-0.39,-0.385,-0.375,-0.385,-0.395,-0.395,-0.385,-0.375,-0.375,-0.385,-0.395,-0.39,-0.39,-0.365,-0.375,-0.385,-0.395,-0.38,-0.385,-0.375,-0.38,-0.39,-0.38,-0.375,-0.375,-0.375,-0.375,-0.38,-0.39,-0.39,-0.365,-0.37,-0.375,-0.385,-0.39,-0.39,-0.375,-0.365,-0.385,-0.38,-0.39,-0.375,-0.375,-0.365,-0.37,-0.385,-0.395,-0.385,-0.385,-0.37,-0.365,-0.37,-0.375,-0.35,-0.335,-0.32,-0.315,-0.32,-0.325,-0.315,-0.295,-0.285,-0.3,-0.295,-0.285,-0.285,-0.28,-0.27,-0.28,-0.29,-0.295,-0.295,-0.29,-0.28,-0.29,-0.295,-0.31,-0.295,-0.285,-0.28,-0.275,-0.295,-0.305,-0.3,-0.295,-0.295,-0.285,-0.3,-0.305,-0.3,-0.295,-0.3,-0.305,-0.315,-0.31,-0.315,-0.295,-0.3,-0.31,-0.33,-0.34,-0.33,-0.32,-0.32,-0.315,-0.325,-0.335,-0.33,-0.315,-0.32,-0.32,-0.33,-0.345,-0.34,-0.335,-0.33,-0.335,-0.345,-0.35,-0.345,-0.34,-0.335,-0.335,-0.33,-0.335,-0.34,-0.335,-0.32,-0.34,-0.34,-0.35,-0.365,-0.36,-0.345,-0.345,-0.35,-0.35,-0.35,-0.34,-0.34,-0.345,-0.345,-0.38,-0.36,-0.36,-0.345,-0.345,-0.35,-0.355,-0.345,-0.33,-0.33,-0.335,-0.335,-0.34,-0.33,-0.32,-0.29,-0.29,-0.3,-0.305,-0.285,-0.28,-0.275,-0.28,-0.275,-0.27,-0.265,-0.25,-0.23,-0.245,-0.24,-0.255,-0.255,-0.245,-0.235,-0.255,-0.265,-0.26,-0.255,-0.27,-0.265,-0.28,-0.255,-0.25,-0.245,-0.265,-0.28,-0.3,-0.315,-0.325,-0.335,-0.345,-0.33,-0.335,-0.345,-0.36,-0.35,-0.34,-0.335,-0.345,-0.36,-0.365,-0.375,-0.37,-0.365,-0.365,-0.38,-0.375,-0.375,-0.365,-0.37,-0.36,-0.37,-0.38,-0.365,-0.36,-0.35,-0.355,-0.37,-0.385,-0.395,-0.43,-0.445,-0.485,-0.515,-0.565,-0.555,-0.475,-0.355,-0.22,-0.055,0.185,0.475,0.705,0.82,0.78,0.535,0.145,-0.2,-0.42,-0.5,-0.48,-0.45,-0.41,-0.4,-0.39,-0.39,-0.4,-0.405,-0.425,-0.425,-0.405,-0.395,-0.405,-0.405,-0.415,-0.405,-0.385,-0.39,-0.395,-0.395,-0.395,-0.395,-0.385,-0.39,-0.39,-0.4,-0.425,-0.41,-0.4,-0.395,-0.4,-0.405,-0.425,-0.415,-0.405,-0.405,-0.405,-0.41,-0.415,-0.405,-0.42,-0.405,-0.41,-0.41,-0.42,-0.415,-0.405,-0.395,-0.395,-0.395,-0.4,-0.395,-0.395,-0.405,-0.405,-0.42,-0.435,-0.415,-0.405,-0.395,-0.39,-0.415,-0.405,-0.405,-0.4,-0.395,-0.395,-0.405,-0.42,-0.415,-0.4,-0.395,-0.4,-0.405,-0.425,-0.425,-0.425,-0.42,-0.425,-0.45,-0.445,-0.45,-0.435,-0.445,-0.47,-0.455,-0.46,-0.44,-0.435,-0.425,-0.43,-0.445,-0.45,-0.445,-0.43,-0.42,-0.41,-0.425,-0.42,-0.405,-0.38,-0.37,-0.365,-0.365,-0.365,-0.345,-0.33,-0.315,-0.305,-0.305,-0.32,-0.31,-0.31,-0.305,-0.31,-0.315,-0.32,-0.315,-0.305,-0.305,-0.295,-0.31,-0.32,-0.31,-0.315,-0.295,-0.3,-0.305,-0.3,-0.285,-0.28,-0.29,-0.295,-0.305,-0.325,-0.305,-0.3,-0.3,-0.315,-0.325,-0.335,-0.325,-0.315,-0.32,-0.335,-0.33,-0.34,-0.325,-0.325,-0.32,-0.315,-0.335,-0.335,-0.345,-0.335,-0.325,-0.33,-0.315,-0.335,-0.33,-0.33,-0.33,-0.33,-0.335,-0.34,-0.33,-0.32,-0.315,-0.325,-0.335,-0.355,-0.345,-0.34,-0.335,-0.32,-0.325,-0.35,-0.33,-0.315,-0.315,-0.33,-0.33,-0.335,-0.335,-0.33,-0.33,-0.33,-0.34,-0.34,-0.34,-0.325,-0.325,-0.325,-0.345,-0.35,-0.34,-0.33,-0.32,-0.31,-0.305,-0.305,-0.275,-0.27,-0.255,-0.26,-0.265,-0.26,-0.25,-0.235,-0.235,-0.22,-0.235,-0.235,-0.22,-0.23,-0.225,-0.235,-0.245,-0.255,-0.24,-0.23,-0.23,-0.25,-0.255,-0.25,-0.23,-0.205,-0.195,-0.21,-0.23,-0.27,-0.27,-0.285,-0.285,-0.31,-0.33,-0.34,-0.34,-0.345,-0.35,-0.35,-0.34,-0.355,-0.345,-0.34,-0.34,-0.345,-0.355,-0.37,-0.355,-0.37,-0.345,-0.34,-0.36,-0.38,-0.36,-0.345,-0.35,-0.35,-0.355,-0.38,-0.39,-0.395,-0.425,-0.455,-0.475,-0.51,-0.545,-0.54,-0.495,-0.405,-0.295,-0.16,0.04,0.315,0.595,0.8,0.885,0.795,0.52,0.13,-0.215,-0.425,-0.5,-0.49,-0.45,-0.4,-0.39,-0.395,-0.395,-0.4,-0.39,-0.38,-0.385,-0.385,-0.4,-0.41,-0.41,-0.39,-0.39,-0.405,-0.405,-0.415,-0.41,-0.395,-0.395,-0.4,-0.415,-0.415,-0.415,-0.4,-0.39,-0.39,-0.41,-0.415,-0.405,-0.395,-0.395,-0.395,-0.405,-0.41,-0.41,-0.4,-0.39,-0.395,-0.41,-0.425,-0.415,-0.405,-0.41,-0.405,-0.415,-0.425,-0.425,-0.41,-0.395,-0.39,-0.4,-0.41,-0.41,-0.4,-0.39,-0.41,-0.42,-0.42,-0.415,-0.415,-0.405,-0.42,-0.415,-0.425,-0.41,-0.4,-0.405,-0.4,-0.4,-0.41,-0.4,-0.395,-0.395,-0.405,-0.41,-0.425,-0.425,-0.41,-0.41,-0.42,-0.43,-0.445,-0.44,-0.435,-0.43,-0.43,-0.45,-0.445,-0.445,-0.435,-0.435,-0.445,-0.44,-0.45,-0.43,-0.43,-0.415,-0.4,-0.4,-0.385,-0.365,-0.335,-0.33,-0.325,-0.33,-0.33,-0.31,-0.295,-0.285,-0.29,-0.29,-0.31,-0.295,-0.285,-0.28,-0.3,-0.29,-0.295,-0.29,-0.28,-0.275,-0.275,-0.29,-0.29,-0.285,-0.27,-0.27,-0.27,-0.275,-0.29,-0.285,-0.275,-0.275,-0.29,-0.295,-0.305,-0.295,-0.295,-0.285,-0.29,-0.3,-0.305,-0.31,-0.295,-0.3,-0.305,-0.31,-0.325,-0.325,-0.315,-0.31,-0.315,-0.315,-0.325,-0.32,-0.31,-0.32,-0.315,-0.33,-0.33,-0.32,-0.31,-0.31,-0.31,-0.33,-0.335,-0.33,-0.32,-0.325,-0.325,-0.33,-0.335,-0.33,-0.325,-0.325,-0.32,-0.325,-0.33,-0.325,-0.315,-0.305,-0.325,-0.315,-0.335,-0.33,-0.31,-0.32,-0.33,-0.33,-0.335,-0.33,-0.33,-0.32,-0.32,-0.33,-0.33,-0.325,-0.315,-0.315,-0.315,-0.33,-0.33,-0.32,-0.315,-0.305,-0.305,-0.295,-0.315,-0.3,-0.275,-0.255,-0.255,-0.26,-0.265,-0.255,-0.235,-0.23,-0.225,-0.22,-0.22,-0.215,-0.2,-0.205,-0.205,-0.225,-0.235,-0.24,-0.23,-0.215,-0.24,-0.24,-0.25,-0.235,-0.21,-0.2,-0.22,-0.255,-0.275,-0.285,-0.3,-0.305,-0.32,-0.33,-0.325,-0.32,-0.33,-0.33,-0.335,-0.34,-0.345,-0.34,-0.335,-0.32,-0.325,-0.335,-0.345,-0.345,-0.345,-0.335,-0.34,-0.345,-0.355,-0.34,-0.34,-0.34,-0.34,-0.345,-0.355,-0.365,-0.37,-0.405,-0.445,-0.48,-0.495,-0.52,-0.535,-0.505,-0.415,-0.28,-0.135,0.065,0.3,0.58,0.81,0.945,0.92,0.775,0.445,0.02,-0.295,-0.445,-0.47,-0.425,-0.38,-0.36,-0.36,-0.37,-0.38,-0.38,-0.375,-0.37,-0.365,-0.37,-0.385,-0.385,-0.375,-0.37,-0.37,-0.385,-0.395,-0.385,-0.385,-0.39,-0.385,-0.395,-0.41,-0.4,-0.395,-0.38,-0.395,-0.395,-0.4,-0.4,-0.395,-0.39,-0.39,-0.395,-0.405,-0.39,-0.385,-0.375,-0.375,-0.38,-0.39,-0.385,-0.37,-0.37,-0.37,-0.375,-0.385,-0.37,-0.39,-0.375,-0.37,-0.38,-0.39,-0.39,-0.385,-0.375,-0.375,-0.385,-0.395,-0.39,-0.385,-0.375,-0.38,-0.385,-0.39,-0.38,-0.38,-0.37,-0.37,-0.365,-0.39,-0.365,-0.365,-0.36,-0.375,-0.38,-0.4,-0.39,-0.375,-0.375,-0.37,-0.385,-0.39,-0.39,-0.39,-0.39,-0.375,-0.385,-0.395,-0.38,-0.37,-0.375,-0.375,-0.39,-0.405,-0.375,-0.37,-0.36,-0.375,-0.385,-0.38,-0.37,-0.36,-0.345,-0.345,-0.33,-0.33,-0.315,-0.31,-0.295,-0.285,-0.285,-0.295,-0.28,-0.27,-0.28,-0.28,-0.28,-0.29,-0.29,-0.28,-0.275,-0.28,-0.285,-0.29,-0.29,-0.28,-0.28,-0.285,-0.29,-0.3,-0.295,-0.285,-0.28,-0.285,-0.285,-0.295,-0.285,-0.28,-0.275,-0.28,-0.29,-0.31,-0.305,-0.285,-0.27,-0.27,-0.275,-0.285,-0.285,-0.27,-0.265,-0.265,-0.265,-0.28,-0.265,-0.245,-0.235,-0.235};
    float[] y;
    float width;
    float init_width = 0;
    float height;
    float axis_label_width = 50;
    float axis_width = 30;

    // Do not set min zoom or scale factor to 1f!!
    // The panning will break if you do
    private static float MIN_ZOOM = 1.1f;
    private static float MAX_ZOOM = 20f;
    private float scaleFactor = 1.1f;
    private ScaleGestureDetector detector;
    private static int NONE = 0;
    private static int PAN = 1;
    private static int ZOOM = 2;

    private int mode;
    private boolean dragged = false;

    // Track X and Y of fingers
    private float startX = 0f;
    private float startY = 0f;

    // How much the canvas moves
    private float moveX = 0f;
    private float moveY = 0f;

    // How much the canvas moved before
    private float lastMoveX = 0f;
    private float lastMoveY = 0f;

    VertText vertText;

    public GraphActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("Info",fy.length + "");

        // For testing purposes
        y = new float[fy.length];
        for (int i = 0; i < fy.length; i++) {
            y[i] = (float) -fy[i];
        }

        // Draws the data
        dataPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dataPaint.setColor(Color.BLACK);
        dataPaint.setStrokeWidth(2);
        // Draws the main grid lines
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.RED);
        gridPaint.setStrokeWidth(1);
        // Draws the five small lines in each cell
        miniPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        miniPaint.setColor(ContextCompat.getColor(context, R.color.translucentRed));
        miniPaint.setStrokeWidth(1);
        // Draws axis labels
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(1);
        vertText = new VertText(getContext());
        if (textHeight == 0) {
            textHeight = textPaint.getTextSize();
        } else {
            textPaint.setTextSize(textHeight);
        }
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public void setGraphData(float[] data) {
        y = data;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = PAN;
                // I don't know why these are subtracted but it works
                startX = event.getX() - lastMoveX;
                startY = event.getY() - lastMoveY;
                break;

            case MotionEvent.ACTION_MOVE:
                moveX = event.getX() - startX;
                moveY = event.getY() - startY;
                // Somehow get the finger position
                double distance = Math.sqrt(Math.pow(event.getX() - (startX + lastMoveX), 2) +
                        Math.pow(event.getY() - (startY + lastMoveY), 2)
                );
                if(distance > 0) {
                    dragged = true;
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;

            case MotionEvent.ACTION_UP:
                mode = NONE;
                dragged = false;
                lastMoveX = moveX;
                lastMoveY = moveY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mode = PAN;
                lastMoveX = moveX;
                lastMoveY = moveY;
                break;
        }

        detector.onTouchEvent(event);
        // Redraw if zoom or drag
        // The scale factor is not equal to 1 (meaning zoom) and dragged is
        // set to true (meaning the finger has actually moved)
        if ((mode == PAN && scaleFactor != 1f && dragged) || mode == ZOOM) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        // Account for the label
        if (mShowText) xpad += mTextWidth;

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on min
        int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minW, widthMeasureSpec, 1);

        // Somehow get a good graph height
        // Idk what this variable is for
        //int minh = MeasureSpec.getSize(w) - (int) mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - mTextWidth, heightMeasureSpec - 60, 0);
        width = w - 10;
        if (init_width == 0) {
            init_width = width;
        }
        height = h - 10;
        setMeasuredDimension(w, h);
    }

    // The graph is drawn in mysterious ways
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = init_width * scaleFactor;
//        width = 10000;

        // Left side
        if((moveX * -1) < 0) {
            moveX = 0;
        }

        // If moveX is greater than that value then it went beyond bounds
        // Set the value of moveX to (1 - scaleFactor) times the display width because idk
        // Same as doing -1 * (scaleFactor - 1) * displayWidth
        else if((moveX * -1) > (scaleFactor - 1) * width) {
            moveX = (1 - scaleFactor) * width;
        }

        if(moveY * -1 < 0) {
            moveY = 0;
        }

        // Same thing for the bottom bound, except use display height
        else if((moveY * -1) > (scaleFactor - 1) * height) {
            moveY = (1 - scaleFactor) * height;
        }

        // Divide by the scale factor here, or else excessive panning happens based on zoom level
        // Translation amount also gets scaled according to how much it zooms in on the canvas
        // Still calculates y pan even though it's off
        // If you divide the scale factor it pans faster?? Or maybe not???
        // ????????
        canvas.translate(moveX * ((float)Math.pow(1/scaleFactor, 1.0/4.0)), 0);
        float max = -10000;
        float min = 10000;
        for (int i = 1; i < y.length; i++) {
            if (y[i] > max) {
                max = y[i];
            }
            if (y[i] < min) {
                min = y[i];
            }
        }
        float range = Math.abs(max- min) + (float).2;
        float rect_size = 5;
        float scale_fact = (height-2*axis_width - axis_label_width) / range;
        float swidth = width-2*axis_width - axis_label_width;
        float sheight = height-2*axis_width - axis_label_width;


        // Draw grid lines
        // Vertical is amplitude 0 - 20
        // X is time from 0 to 1000
        int density = 10;
        int xDensity = 40;

        for (int i = 0; i < xDensity; i++) {
            // Draws vertical lines
            canvas.drawLine((float) i/xDensity * swidth + axis_width + axis_label_width,
                    axis_width,
                    (float) i/xDensity * swidth + axis_width + axis_label_width,
                    sheight + axis_width, gridPaint);
        }
        for (int i = 0; i <= density; i++) {
            // Horizontal lines
            canvas.drawLine(axis_width + axis_label_width,
                    (float) i/density * sheight + axis_width,
                    swidth + axis_width + axis_label_width,
                    (float) i/density * sheight + axis_width, gridPaint);
        }
        // Draw mini lines
        for (int i = 0; i < 5*xDensity; i++) {
            // Draws vertical lines
            canvas.drawLine((float) i/(5*xDensity) * swidth + axis_width + axis_label_width,
                    axis_width,
                    (float) i/(5*xDensity) * swidth + axis_width + axis_label_width,
                    sheight + axis_width, miniPaint);
        }
        for (int i = 0; i <= 5*density; i++) {
            // Horizontal lines
            canvas.drawLine(axis_width + axis_label_width,
                    (float) i/(5*density) * sheight + axis_width,
                    swidth + axis_width + axis_label_width,
                    (float) i/(5*density) * sheight + axis_width, miniPaint);
        }

        // Draw data lines and points
        for (int i = 1; i < y.length; i++) {
            // startX, startY, stopX, stopY, Paint
            canvas.drawLine((float)(i-1)/(y.length-1) * swidth + axis_width + axis_label_width,
                    (y[i-1]+Math.abs(min)+(float).1)*scale_fact + axis_width,
                    (float)i/(y.length-1) * swidth + axis_width + axis_label_width,
                    (y[i]+Math.abs(min)+(float).1)*scale_fact+axis_width,
                    dataPaint);
            /*
            // left, top, right, bottom, Paint
            canvas.drawRect((float)i/(y.length-1) * swidth - rect_size + axis_width + axis_label_width,
                    (y[i]+Math.abs(min)+(float).1)*scale_fact - rect_size + axis_width,
                    (float)i/(y.length-1) * swidth + rect_size + axis_width + axis_label_width,
                    (y[i]+Math.abs(min)+(float).1)*scale_fact + rect_size + axis_width,
                    dataPaint);

             */
        }

//        // Draws the data points
//        canvas.drawRect((float)0/(y.length-1) * swidth - rect_size + axis_width + axis_label_width,
//                y[0]*scale_fact - rect_size + axis_width,
//                (float)0/(y.length-1) * swidth + rect_size + axis_width + axis_label_width,
//                y[0]*scale_fact + rect_size + axis_width, dataPaint);



        // Create axes
        for (int i = 0; i < xDensity; i++) {
            // Draw x axis ticks
            canvas.drawLine((float) i/xDensity * swidth + axis_width + axis_label_width,
                    axis_width + sheight + axis_width,
                    (float) i/xDensity * swidth + axis_width + axis_label_width,
                    axis_width + sheight, gridPaint);
        }

        for (int i = 0; i <= density; i++) {
            // Draw y axis ticks
            canvas.drawLine(axis_width + axis_label_width,
                    (float) i/density * sheight + axis_width,
                    axis_label_width,
                    (float) i/density * sheight + axis_width,
                    gridPaint);
            /*
            // Draw X axis numbers
            canvas.drawText(i+"", (float) i/density * swidth + axis_width + axis_label_width,
                    height - axis_label_width, textPaint);
            // Draw Y axis numbers
            canvas.drawText((((float)(density-i)/density)*range + min - (float).1)+"",
                    axis_label_width,
                    (float) i/density * sheight + axis_width - textHeight/3,
                    textPaint);

             */
        }
        // Draw axis labels
        //canvas.drawText("Time (ms)", (width/2)/scaleFactor, height - textHeight, textPaint);
        vertText.rotate(270, axis_width + 10, height/2);
        vertText.setText("Voltage (mV)", 15);
        vertText.setLoc(0, height/2);
        vertText.draw(canvas);
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            return true;
        }
    }
}