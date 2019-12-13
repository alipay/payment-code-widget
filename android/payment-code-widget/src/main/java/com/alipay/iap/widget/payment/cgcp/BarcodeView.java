package com.alipay.iap.widget.payment.cgcp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Scanner-friendly Barcode view based on CGCP standard
 *
 * @author sean.zhang Created on 2019-09-03.
 */
public class BarcodeView extends FrameLayout {

    // Maximum width of barcode is 65mm
    private static final int MAX_BARCODE_WIDTH = 65;

    // Minimum height of barcode is 10mm
    private static final int DEFAULT_BARCODE_HEIGHT = 10;

    // Barcode width, including the left and right quiet zones
    private int barcodeWidth;

    // Barcode height
    private int barcodeHeight;

    // Barcode color
    private int barcodeBackgroundColor = Color.WHITE;
    private int barcodeForegroundColor = Color.BLACK;

    // The payment code
    private String code;

    // The number of digits that barcode contains, possible values are 18, 19 and 24
    // Default value is 18
    private int numOfDigits = 18;

    public BarcodeView(Context context) {
        super(context);
    }

    public BarcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarcodeView(Context context, AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {

            int width = getWidth() - getPaddingLeft() - getPaddingRight();
            int height = getHeight() - getPaddingTop() - getPaddingBottom();

            int offsetX = (width - barcodeWidth) / 2;
            int offsetY = (height - barcodeHeight) / 2;

            // draw barcode
            Bitmap bitmap = createBarcodeBitmap();
            canvas.drawBitmap(bitmap, offsetX, offsetY, null);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // Make sure the height is >= 10mm
        int defaultHeight = convertMmToPixel(DEFAULT_BARCODE_HEIGHT);
        if (heightSpecMode == MeasureSpec.UNSPECIFIED) {
            heightSpecSize = defaultHeight;
        } else if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.EXACTLY) {
            heightSpecSize = Math.max(defaultHeight, heightSpecSize);
        }

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        // Calculate the size of the bitmap, make sure the width is <= 65mm
        int maxBarcodeWidth = convertMmToPixel(MAX_BARCODE_WIDTH);

        barcodeWidth = Math.min(widthSpecSize - getPaddingLeft() - getPaddingRight(), maxBarcodeWidth);

        // Calculate the minimum unit width
        int minimumUnitWidth = (int)(barcodeWidth / (5.5f * numOfDigits + 55));

        // Calculate the barcode width without quite zone
        int l1 = (int)((5.5f * numOfDigits + 35) * minimumUnitWidth);

        barcodeHeight = Math.max(defaultHeight, (int) (l1 * 0.15));

        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    /**
     * Set the payment code
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
        this.numOfDigits = this.code.length();
    }

    /**
     * Convert millimeters to screen pixels
     * @param mm value in millimeters
     * @return value in screen pixels
     */
    private int convertMmToPixel(int mm) {
        final DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, dm);
    }

    /**
     * Create a barcode bitmap based on payment code, width and height
     * @return barcode bitmap
     * @throws WriterException
     */
    private Bitmap createBarcodeBitmap() throws WriterException {
        if (!TextUtils.isEmpty(this.code)) {
            BitMatrix matrix = new MultiFormatWriter()
                .encode(this.code, BarcodeFormat.CODE_128, this.barcodeWidth, this.barcodeHeight);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = matrix.get(x, y) ? barcodeForegroundColor : barcodeBackgroundColor;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } else {
            // Return a blank bitmap
            Bitmap bitmap = Bitmap.createBitmap(this.barcodeWidth, this.barcodeHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(this.barcodeBackgroundColor);
            return bitmap;
        }
    }
}
