package school.comp7031.assignment3;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class FramedImageView extends View {
    private int borderColor = Color.RED;
    private int borderWidth = 5;
    private Drawable mExampleDrawable;
    private Bitmap image;
    private Bitmap scaledImage;

    private TextPaint mTextPaint;

    public FramedImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public FramedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FramedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FramedImageView, defStyle, 0);
        this.setMinimumHeight(android.R.attr.height);
        this.setMinimumWidth(android.R.attr.width);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        a.recycle();
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        scaledImage = Bitmap.createScaledBitmap(image, this.getWidth() - (2 * borderWidth), this.getHeight() - (2 * borderWidth), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        Paint backgroundPaint = new Paint();

        backgroundPaint.setColor(borderColor);
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), backgroundPaint);

        canvas.drawBitmap(scaledImage, getLeft() + borderWidth, getTop() - borderWidth, new Paint());

    }

    public void setImage(Bitmap newImage){
        image = newImage;
        invalidateTextPaintAndMeasurements();
    }

    public void setImage(Integer integer) {
        image = BitmapFactory.decodeResource(getResources(), integer);
        invalidateTextPaintAndMeasurements();
    }

    public void setBorderColor(int newColor){
        this.borderColor = newColor;
    }

    public int getBorderColor(){
        return this.borderColor;
    }

    public void setBorderWidth(int newWidth){
        this.borderWidth = newWidth;
    }

    public int getBorderWidth(){
        return this.borderWidth;
    }

    @Override
    public void onMeasure(int width, int height){
        this.setMeasuredDimension(scaledImage.getWidth() + borderWidth * 2, scaledImage.getHeight() + borderWidth * 2);
    }
}
