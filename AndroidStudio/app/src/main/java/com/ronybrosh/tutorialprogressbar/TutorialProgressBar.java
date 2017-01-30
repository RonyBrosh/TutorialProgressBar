package com.ronybrosh.tutorialprogressbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by RonyBrosh on 1/14/2016.
 */
public class TutorialProgressBar extends View
{
    private final String TAG = TutorialProgressBar.class.getSimpleName();

    // Default values
    private final int ANIMATION_DURATION = 1000;
    private final int DEFUALT_STEPS_NUMBER = 3;

    private final String DEFAULT_EMPTY_STEP_COLOR_1 = "#cdcdcd";
    private final String DEFAULT_EMPTY_STEP_COLOR_2 = "#e6e6e6";
    private final String DEFAULT_STEP_COLOR_1 = "#199fe4";
    private final String DEFAULT_STEP_COLOR_2 = "#ed1c24";

    private final boolean IS_PRINT_LOGS = false;

    // Drawing values
    private int[] mEmptyStepColors;
    private int[] mFillStepColors;
    private float[] mStepsFillPercentage;
    private float[] mStepsWidth;
    private RectF[] mRectArray;

    // Drawing objects and parameters
    private int mViewWidth;
    private int mViewHeight;
    private int mStepsNumber;
    private int mCurrentStep;
    private float mCurrentFillStepAnimationFraction;
    private boolean mIsUseMask;

    private ValueAnimator mValueAnimator;
    private Paint mPaint;
    private Bitmap mMaskBitmap;
    private Bitmap mHelperBitmap;
    private Canvas mHelperCanvas;


    public TutorialProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TutorialProgressBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public TutorialProgressBar(Context context)
    {
        super(context);
        initView();
    }

    /**
     * Set the color array for the empty steps.
     * The array length should be equal to the number of steps.
     *
     * @param colors
     */
    public void setEmptyStepColors(int[] colors)
    {
        mEmptyStepColors = colors;
    }

    /**
     * Set the color array for the filled steps.
     * The array length should be equal to the number of steps.
     *
     * @param colors
     */
    public void setFillStepColors(int[] colors)
    {
        mFillStepColors = colors;
    }

    /**
     * Set array of steps fill percentage relative to the whole view.
     * For example:
     * Step 1 - 50%
     * Step 2 - 25%
     * Step 3 - 25%
     * The default is equal percentage for each step.
     * The array length should be equal to the number of steps.
     *
     * @param stepsFillPercentage
     */
    public void setStepsFillPercentage(float[] stepsFillPercentage)
    {
        mStepsFillPercentage = stepsFillPercentage;
    }

    /**
     * Set the number of steps.
     *
     * @param stepsNumber
     */
    public void setStepsNumber(int stepsNumber)
    {
        mStepsNumber = stepsNumber;
    }

    /**
     * Set the current step.
     * Step 0 means no steps filled.
     * Filling a step can be animated if desired.
     *
     * @param currentStep
     * @param isAnimate
     */
    public void setCurrentStep(int currentStep, boolean isAnimate)
    {
        mCurrentStep = currentStep;
        if (mCurrentStep > mStepsNumber)
        {
            mCurrentStep = 0;
        }
        if (mCurrentStep > 0 && isAnimate == true)
        {
            mValueAnimator.cancel();
            mValueAnimator.start();
        }
        invalidate();
    }

    /**
     * Get the current tutorial progress step.
     * Zero mean none steps are filled.
     *
     * @return
     */
    public int getCurrentStep()
    {
        return mCurrentStep;
    }

    /**
     * Set the bitmap that will be used to mask the view.
     * To use the default rounded sides mask use the method
     * useDefaultMask(true);
     *
     * @param maskBitmap
     */
    public void setMask(Bitmap maskBitmap)
    {
        mMaskBitmap = maskBitmap;
        mIsUseMask = mMaskBitmap != null ? true : false;
    }

    /**
     * Set is used default rounded sides mask.
     *
     * @param isUseMask
     */
    public void useDefaultMask(boolean isUseMask)
    {
        mIsUseMask = isUseMask;
    }

    /**
     * View padding is not considered in calculations and shouldn't be used.
     *
     * @param start
     * @param top
     * @param end
     * @param bottom
     */
    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom)
    {
        if (IS_PRINT_LOGS == true)
        {
            Log.w(TAG, "View padding is not considered in calculations and shouldn't be used.");
        }
    }

    /**
     * View padding is not considered in calculations and shouldn't be used.
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom)
    {
        if (IS_PRINT_LOGS == true)
        {
            Log.w(TAG, "View padding is not considered in calculations and shouldn't be used.");
        }
    }

    /**
     * View padding is not considered in calculations and shouldn't be used.
     *
     * @param width
     * @param height
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh)
    {
        super.onSizeChanged(width, height, oldw, oldh);
        mViewWidth = width;
        mViewHeight = height;
        if (isInEditMode() == true && mViewWidth == 0 || mViewHeight == 0)
        {
            mViewWidth = 1;
            mViewHeight = 1;
        }

        prepareView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (mMaskBitmap != null)
        {
            setMeasuredDimension(mMaskBitmap.getWidth(), mMaskBitmap.getHeight());
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawEmptySteps(mHelperCanvas);
        drawFilledSteps(mHelperCanvas);

        if (mIsUseMask == true)
        {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mHelperCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
        }
        canvas.drawBitmap(mHelperBitmap, 0, 0, null);
    }

    private void initView()
    {
        initPaint();
        initValueAnimator();
    }

    private void initPaint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initValueAnimator()
    {
        mValueAnimator = ObjectAnimator.ofFloat(0, 1);
        mValueAnimator.setInterpolator(new DecelerateInterpolator(0.2f));
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                if (mCurrentStep - 1 > -1)
                {
                    mCurrentFillStepAnimationFraction = mValueAnimator.getAnimatedFraction() * mStepsWidth[mCurrentStep - 1];
                    invalidate();
                }
            }
        });
        mValueAnimator.setDuration(ANIMATION_DURATION);
        mValueAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator arg0)
            {
            }

            @Override
            public void onAnimationRepeat(Animator arg0)
            {
            }

            @Override
            public void onAnimationEnd(Animator arg0)
            {
            }

            @Override
            public void onAnimationCancel(Animator arg0)
            {
            }
        });
    }

    private void prepareView()
    {
        // 1. Validate steps number.
        if (mStepsNumber <= 0)
        {
            // Set to default.
            mStepsNumber = DEFUALT_STEPS_NUMBER;
        }

        // 2. Validate empty colors.
        if (mEmptyStepColors == null || mEmptyStepColors.length != mStepsNumber)
        {
            // Set to default.
            mEmptyStepColors = new int[mStepsNumber];
            for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
            {
                if (stepIndex % 2 != 0)
                {
                    mEmptyStepColors[stepIndex] = Color.parseColor(DEFAULT_EMPTY_STEP_COLOR_1);
                }
                else
                {
                    mEmptyStepColors[stepIndex] = Color.parseColor(DEFAULT_EMPTY_STEP_COLOR_2);
                }
            }
        }

        // 3. Validate fill colors.
        if (mFillStepColors == null || mFillStepColors.length != mStepsNumber)
        {
            // Set to default.
            mFillStepColors = new int[mStepsNumber];
            for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
            {
                if (stepIndex % 2 != 0)
                {
                    mFillStepColors[stepIndex] = Color.parseColor(DEFAULT_STEP_COLOR_1);
                }
                else
                {
                    mFillStepColors[stepIndex] = Color.parseColor(DEFAULT_STEP_COLOR_2);
                }
            }
        }

        // 4. Validate steps percentage.
        if (mStepsFillPercentage == null || mStepsFillPercentage.length != mStepsNumber)
        {
            // Set to default.
            int percentageSum = 0;
            mStepsFillPercentage = new float[mStepsNumber];
            for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
            {
                percentageSum += 100f / mStepsNumber;
                mStepsFillPercentage[stepIndex] = 100f / mStepsNumber;
            }

            // Add the extra percentage to the step in the middle.
            mStepsFillPercentage[mStepsNumber / 2] += (100 - percentageSum);
        }
        else
        {
            int percentageSum = 0;
            for (float nextPercentage : mStepsFillPercentage)
            {
                percentageSum += nextPercentage;
            }
            if (percentageSum != 100)
            {
                // Set to default.
                percentageSum = 0;
                mStepsFillPercentage = new float[mStepsNumber];
                for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
                {
                    percentageSum += 100f / mStepsNumber;
                    mStepsFillPercentage[stepIndex] = 100f / mStepsNumber;
                }
                // Add the extra percentage to the step in the middle.
                mStepsFillPercentage[mStepsNumber / 2] += (100 - percentageSum);
            }
        }

        // 5. Init steps width and rectangles.
        mRectArray = new RectF[mStepsNumber];
        mStepsWidth = new float[mStepsNumber];
        int widthSum = 0;
        for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
        {
            mStepsWidth[stepIndex] = (int) (mStepsFillPercentage[stepIndex] / 100f * mViewWidth);
            widthSum += mStepsWidth[stepIndex];
        }
        mStepsWidth[mStepsNumber / 2] += (mViewWidth - widthSum);
        for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
        {
            if (stepIndex == 0)
            {
                mRectArray[stepIndex] = new RectF(0, 0, mStepsWidth[stepIndex], mViewHeight);
            }
            else
            {
                mRectArray[stepIndex] = new RectF(mRectArray[stepIndex - 1].right, 0, mRectArray[stepIndex - 1].right + mStepsWidth[stepIndex], mViewHeight);
            }
        }

        // 6 Init helper bitmap and canvas.
        mHelperBitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        mHelperCanvas = new Canvas(mHelperBitmap);

        // 7. Validate mask bitmap.
        if (mIsUseMask == true && mMaskBitmap == null)
        {
            // 7.1 Create default rounded sides mask.
            prepareDefaultMask();
        }
    }

    private void prepareDefaultMask()
    {
        mMaskBitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        int radius = mViewHeight / 2;
        canvas.drawCircle(radius, radius, radius, paint);
        canvas.drawCircle(mViewWidth - radius, radius, radius, paint);
        canvas.drawRect(radius, 0, mViewWidth - radius, mViewHeight, paint);
    }

    private void drawEmptySteps(Canvas canvas)
    {
        mPaint.setXfermode(null);
        for (int stepIndex = 0; stepIndex < mStepsNumber; stepIndex++)
        {
            mPaint.setColor(mEmptyStepColors[stepIndex]);
            canvas.drawRect(mRectArray[stepIndex].left, mRectArray[stepIndex].top, mRectArray[stepIndex].right, mRectArray[stepIndex].bottom, mPaint);
        }
    }

    private void drawFilledSteps(Canvas canvas)
    {
        mPaint.setXfermode(null);
        for (int stepIndex = 0; stepIndex < mCurrentStep; stepIndex++)
        {
            mPaint.setColor(mFillStepColors[stepIndex]);
            if (stepIndex == mCurrentStep - 1)
            {
                canvas.drawRect(mRectArray[stepIndex].left, mRectArray[stepIndex].top, mRectArray[stepIndex].left + mCurrentFillStepAnimationFraction, mRectArray[stepIndex].bottom, mPaint);
            }
            else
            {
                canvas.drawRect(mRectArray[stepIndex].left, mRectArray[stepIndex].top, mRectArray[stepIndex].right, mRectArray[stepIndex].bottom, mPaint);
            }
        }
    }
}
