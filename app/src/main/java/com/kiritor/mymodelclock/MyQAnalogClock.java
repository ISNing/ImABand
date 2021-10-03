//package com.kiritor.mymodelclock;
//
//import android.util.AttributeSet;
//import android.content.Context;
//import android.graphics.*;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Handler;
//import android.view.View;
//import java.util.Calendar;
//import java.util.TimeZone;
///**
// * Created by Kiritor on 13-5-30.
// */
//public class MyQAnalogClock extends View {
//    //时钟盘，分针、秒针、时针对象
//
//    Bitmap mBmpDial;
//    Bitmap mBmpHour;
//    Bitmap mBmpMinute;
//    Bitmap mBmpSecond;
//
//    BitmapDrawable bmdHour;
//    BitmapDrawable bmdMinute;
//    BitmapDrawable bmdSecond;
//    BitmapDrawable bmdDial;
//
//    Paint mPaint;
//
//    Handler tickHandler;
//
//    int mWidth;
//    int mHeigh;
//    int mTempWidth = bmdSecond.getIntrinsicWidth();
//    int mTempHeigh;
//    int centerX;
//    int centerY;
//
//    int availableWidth = 100;
//    int availableHeight = 100;
//
//    private String sTimeZoneString;
//
//
//    public MyQAnalogClock(Context context,AttributeSet attr)
//    {
//        this(context,"GMT+8：00");
//
//    }
//    public MyQAnalogClock(Context context, String sTime_Zone) {
//        super(context);
//        sTimeZoneString = sTime_Zone;
//
//        mBmpHour = BitmapFactory.decodeResource(getResources(),
//                R.drawable.shizhen);
//        bmdHour = new BitmapDrawable(mBmpHour);
//
//        mBmpMinute = BitmapFactory.decodeResource(getResources(),
//                R.drawable.fenzhen);
//        bmdMinute = new BitmapDrawable(mBmpMinute);
//
//        mBmpSecond = BitmapFactory.decodeResource(getResources(),
//                R.drawable.miaozhen);
//        bmdSecond = new BitmapDrawable(mBmpSecond);
//
//        mBmpDial = BitmapFactory.decodeResource(getResources(),
//                R.drawable.android_clock_dial);
//        bmdDial = new BitmapDrawable(mBmpDial);
//        mWidth = mBmpDial.getWidth();
//        mHeigh = mBmpDial.getHeight();
//        centerX = availableWidth / 2;
//        centerY = availableHeight / 2;
//
//        mPaint = new Paint();
//        mPaint.setColor(Color.BLUE);
//        run();
//    }
//
//    public void run() {
//        tickHandler = new Handler();
//        tickHandler.post(tickRunnable);
//    }
//
//    private Runnable tickRunnable = new Runnable() {
//        public void run() {
//            postInvalidate();
//            tickHandler.postDelayed(tickRunnable, 1000);
//        }
//    };
//
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Calendar cal = Calendar.getInstance(TimeZone
//                .getTimeZone(sTimeZoneString));
//        int hour = cal.get(Calendar.HOUR);
//        int minute = cal.get(Calendar.MINUTE);
//        int second = cal.get(Calendar.SECOND);
//        float hourRotate = hour * 30.0f + minute / 60.0f * 30.0f;
//        float minuteRotate = minute * 6.0f;
//        float secondRotate = second * 6.0f;
//
//        boolean scaled = false;
//
//        if (availableWidth < mWidth || availableHeight < mHeigh) {
//            scaled = true;
//            float scale = Math.min((float) availableWidth / (float) mWidth,
//                    (float) availableHeight / (float) mHeigh);
//            canvas.save();
//            canvas.scale(scale, scale, centerX, centerY);
//        }
//
//        bmdDial.setBounds(centerX - (mWidth / 2), centerY - (mHeigh / 2),
//                centerX + (mWidth / 2), centerY + (mHeigh / 2));
//        bmdDial.draw(canvas);
//
//        mTempWidth = bmdHour.getIntrinsicWidth();
//        mTempHeigh = bmdHour.getIntrinsicHeight();
//        canvas.save();
//        canvas.rotate(hourRotate, centerX, centerY);
//        bmdHour.setBounds(centerX - (mTempWidth / 2), centerY
//                - (mTempHeigh / 2), centerX + (mTempWidth / 2), centerY
//                + (mTempHeigh / 2));
//        bmdHour.draw(canvas);
//
//        canvas.restore();
//
//        mTempWidth = bmdMinute.getIntrinsicWidth();
//        mTempHeigh = bmdMinute.getIntrinsicHeight();
//        canvas.save();
//        canvas.rotate(minuteRotate, centerX, centerY);
//        bmdMinute.setBounds(centerX - (mTempWidth / 2), centerY
//                - (mTempHeigh / 2), centerX + (mTempWidth / 2), centerY
//                + (mTempHeigh / 2));
//        bmdMinute.draw(canvas);
//
//        canvas.restore();
//
//        mTempWidth = bmdSecond.getIntrinsicWidth();
//        mTempHeigh = bmdSecond.getIntrinsicHeight();
//        canvas.rotate(secondRotate, centerX, centerY);
//        bmdSecond.setBounds(centerX - (mTempWidth / 2), centerY
//                - (mTempHeigh / 2), centerX + (mTempWidth / 2), centerY
//                + (mTempHeigh / 2));
//        bmdSecond.draw(canvas);
//
//        if (scaled) {
//            canvas.restore();
//        }
//    }
//}