package com.yulin.horizontaltimeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YuLin on 2016/12/12 0012.
 */

public class HorizontalTimeLine extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mFirstLineHeight;
    private int mSecondLineHeight;

    private String mText;
    private int mTextColor;
    private int mTextSize;
    private Rect mTextBound;

    private Paint mPaint;

    private static final int LINE_NUM_START = 0;
    private static final int LINE_NUM_FINISH = 24;
    private static final int LINE_TOTAL_COUNT = 8;

    private int mLineTextMargin;
    private int mLineMargin;

    private float mLineWidth;
    private float mLinePerWidth;
    private float mLinePerCountWidth;

    private Map<Float, Float> mSelectedMap;

    public void setSelectedMap(Map<Float, Float> map) {
        this.mSelectedMap = map;
        invalidate();
    }

    public Map<Float, Float> getSelectedMap() {
        return mSelectedMap;
    }

    public void setFirstColor(int mFirstColor) {
        this.mFirstColor = mFirstColor;
        invalidate();
    }

    public void setSecondColor(int mSecondColor) {
        this.mSecondColor = mSecondColor;
        invalidate();
    }

    public void setFirstLineHeight(int mFirstLineHeight) {
        this.mFirstLineHeight = mFirstLineHeight;
        invalidate();
    }

    public void setSecondLineHeight(int mSecondLineHeight) {
        this.mSecondLineHeight = mSecondLineHeight;
        invalidate();
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public static int getLineNumStart() {
        return LINE_NUM_START;
    }

    public static int getLineNumFinish() {
        return LINE_NUM_FINISH;
    }

    public static int getLineTotalCount() {
        return LINE_TOTAL_COUNT;
    }

    public void setLineTextMargin(int mLineTextMargin) {
        this.mLineTextMargin = mLineTextMargin;
        invalidate();
    }

    public HorizontalTimeLine(Context context) {
        this(context,null);
    }

    public HorizontalTimeLine(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalTimeLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalTimeLine);
        int n = a.getIndexCount();
        for(int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.HorizontalTimeLine_firstLineColor:
                    mFirstColor = a.getColor(attr, Color.parseColor("#e0e0e0"));
                    break;
                case R.styleable.HorizontalTimeLine_secondLineColor:
                    mSecondColor = a.getColor(attr, Color.parseColor("#d90b0b"));
                    break;
                case R.styleable.HorizontalTimeLine_firstLineHeight:
                    mFirstLineHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HorizontalTimeLine_secondLineHeight:
                    mSecondLineHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HorizontalTimeLine_textColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.HorizontalTimeLine_textSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mSelectedMap = new HashMap<>();
        mSelectedMap.put(0f,3f);

        mSelectedMap.put(6f,24f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mLineTextMargin = DimensUtils.dp2px(getContext(), 20);
        mLineMargin = DimensUtils.dp2px(getContext(), 7);
        mLineWidth = getWidth() - mLineMargin * 2;
        mLinePerWidth = mLineWidth / LINE_NUM_FINISH;
        mLinePerCountWidth = mLineWidth / LINE_TOTAL_COUNT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**Draw First Line*/
        mPaint.setColor(mFirstColor);
        mPaint.setStrokeWidth(mFirstLineHeight);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(mLineMargin, mFirstLineHeight + (mSecondLineHeight - mFirstLineHeight) / 2.0f, getWidth() - mLineMargin, mFirstLineHeight + (mSecondLineHeight - mFirstLineHeight) / 2.0f, mPaint);

        /**Draw Second Line*/
        if(mSelectedMap != null && mSelectedMap.size() > 0) {
            for (Map.Entry<Float, Float> entry : mSelectedMap.entrySet()) {
                mPaint.setStrokeWidth(mSecondLineHeight);
                mPaint.setColor(mSecondColor);
                float startNum = entry.getKey();
                float finishNum = entry.getValue();
                canvas.drawLine(
                        mLineMargin + mLinePerWidth * startNum,
                        mSecondLineHeight - (mSecondLineHeight - mFirstLineHeight) / 2.0f,
                        mLineWidth + mLineMargin - (LINE_NUM_FINISH - finishNum) * mLinePerWidth,
                        mSecondLineHeight - (mSecondLineHeight - mFirstLineHeight) / 2.0f,
                        mPaint);
            }
        }

        /**Draw Text*/
        for (int i = 0; i <= LINE_TOTAL_COUNT ; i++) {
            mText = (LINE_NUM_FINISH - LINE_NUM_START) / LINE_TOTAL_COUNT * i + "";
            mTextBound = new Rect();
            mPaint.setTextSize(mTextSize);
            mPaint.setColor(mTextColor);
            mPaint.getTextBounds(mText,0,mText.length(), mTextBound);
            canvas.drawText(
                    mText,
                    mLinePerCountWidth * i + mLineMargin - (mTextBound.width() * 1.0f) / 2,
                    mFirstLineHeight + mLineTextMargin, mPaint);
        }
    }
}
