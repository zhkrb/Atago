package com.zhkrb.atago.viewUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zhkrb.atago.R;

public class MusicVisualizerView extends View {

    private Paint mBaselinePaint;
    private Paint mForePaint;
    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();
    private int mSpectNum = 48;
    private int mHeight;
    private int mWidth;
    private int radius = 100;


    public MusicVisualizerView(Context context) {
        super(context);
        initView();
    }

    public MusicVisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MusicVisualizerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){

//        this.post(getWH);
        mBaselinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBaselinePaint.setColor(getResources().getColor(R.color.colorPrimary));
        mForePaint.setColor(getResources().getColor(R.color.colorAccent));

        mBaselinePaint.setStrokeWidth(3f);
        mForePaint.setStrokeWidth(8f);

    }

    public void updateVisualizer(byte[] fft){
        byte[] model = new byte[fft.length/2+1];
        model[0] = (byte) Math.abs(fft[0]);

        for (int i=2, j=1;j<mSpectNum;){
            model[j] = (byte) Math.hypot(fft[i],fft[i+1]);
            i+=2;
            j++;
        }

        mBytes = model;

        invalidate();

    }

    Runnable getWH = new Runnable() {
        @Override
        public void run() {
            mHeight = MusicVisualizerView.this.getMeasuredHeight();
            mWidth =  MusicVisualizerView.this.getMeasuredWidth();
            MusicVisualizerView.this.invalidate();
        }
    };


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.parseColor("#000000"));
        canvas.drawLine(0,mHeight-10,mWidth,mHeight-10,mBaselinePaint);

        if (mBytes == null){

            return;
        }
        System.out.println(mBytes[1]);
        if (mPoints == null || mPoints.length < mBytes.length * 4)
        {
            mPoints = new float[mBytes.length * 4];
        }
        mRect.set(0, 0, getWidth(), getHeight());
//
//
//        final  int baseX = mRect.width()/mSpectNum;
//        final  int height = mRect.height();
//        for (int i = 0;i<mSpectNum;i++){
//            if (mBytes[i]<0){
//                mBytes[i] = 127;
//            }
//
//            final int xi = baseX*i+baseX/2;         //x坐标加上间隔
//            mPoints[i*4] = xi;
//            mPoints[i*4+1] = height;
//            mPoints[i * 4 + 2] = xi;
//            mPoints[i * 4 + 3] = height - mBytes[i]*10;
//        }

        final  int centerX = mRect.width()/2;
        final  int centerY = mRect.height()/2;

        for (int i=0;i<mSpectNum;i++){
            if (mBytes[i]<0){
                mBytes[i] = 127;
            }

            final float angle = (float) (i*(360/mSpectNum));

            mPoints[i*4] = (float) (centerX+Math.cos(angle)*radius);
            mPoints[i*4+1] = (float) (centerY-Math.sin(angle)*radius);
            mPoints[i*4+2] = (float) (centerX+Math.cos(angle)*(radius+mBytes[i]*5));
            mPoints[i*4+3] = (float) (centerY-Math.sin(angle)*(radius+mBytes[i]*5));


        }




        canvas.drawLines(mPoints,mForePaint);
    }
}
