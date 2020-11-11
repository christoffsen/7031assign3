package school.comp7031.assignment3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class FramedImageView extends View {
    private int borderColor = Color.BLUE;
    private int borderWidth = 5;
    private Bitmap image;
    private Bitmap scaledImage;

    private TextPaint mTextPaint;

    public FramedImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public FramedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FramedImageView, 0, 0);
        
        borderColor = Color.parseColor(a.getString(R.styleable.FramedImageView_borderColor));
        borderWidth = Integer.parseInt(a.getString(R.styleable.FramedImageView_borderWidth));

        init(attrs, 0);
    }

    public FramedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        image = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        setScaleAndInvalidate();
    }

    private void setScaleAndInvalidate() {
        int width = (getRight() - getLeft()) - (2 * borderWidth);
        int height = (getBottom() - getTop()) - (2 * borderWidth);
        if(width < 0) {
            width = image.getWidth() - (2 * borderWidth);
        }
        if(height < 0) {
            height = image.getHeight() - (2 * borderWidth);
        }

        scaledImage = Bitmap.createScaledBitmap(image, width, height, false);
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint backgroundPaint = new Paint();

        backgroundPaint.setColor(borderColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        canvas.drawBitmap(scaledImage, borderWidth, borderWidth, null);

    }

    public void setImage(Bitmap newImage){
        image = newImage;
        setScaleAndInvalidate();
    }

    public void setImage(Integer integer) {
        image = BitmapFactory.decodeResource(getResources(), integer);
        setScaleAndInvalidate();
    }

}
