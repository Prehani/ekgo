package ekgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class VertText extends View {
    private float x = 0, y = 0;
    private int textColor;
    private Typeface font;
    private int fontSize;
    private int rAngle;
    private float m_nRotationW, m_nRotationH;
    private String textSize;
    private Paint paint = new Paint();

    public VertText(Context context) {
        super(context);
        // set default parameters
        textColor = Color.BLACK;
        fontSize = 14;
        rAngle = 0;
        m_nRotationW = 0;
        m_nRotationH = 0;
        font = Typeface.create("Roboto", Typeface.NORMAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTypeface(font);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);
        //paint.setShadowLayer(1, 0, 1, Color.parseColor("#000000"));
        paint.setTextSize(fontSize);
        canvas.rotate(rAngle,m_nRotationW,m_nRotationH);
        canvas.drawText(textSize, x, y, paint);
        super.onDraw(canvas);
    }

    public void setLoc(float newX, float newY) {
        x = newX;
        y = newY;
        this.invalidate();
    }

    public void changeColor(int newColor) {
        textColor = newColor;
        this.invalidate();
    }

    //style: normal-0,bold-1,italic-2,bold-italic-3,
    public void setFont(String newFontFace, int style) {
        font = Typeface.create(newFontFace, style);
        this.invalidate();
    }
    public void rotate(int newangle, float neww, float newh) {
        rAngle = newangle;
        m_nRotationW = neww;
        m_nRotationH = newh;
        this.invalidate();
    }
    public void setText(String newText, int newSize) {
        textSize = newText;
        fontSize = newSize;
        this.invalidate();
    }
}