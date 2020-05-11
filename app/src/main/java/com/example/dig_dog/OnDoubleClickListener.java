package com.example.dig_dog;

import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

//import java.util.logging.Handler;

public class OnDoubleClickListener implements View.OnTouchListener{

//    private int count = 0;//点击次数
//    private long firstClick = 0;//第一次点击时间
//    private long secondClick = 0;//第二次点击时间
    private Handler handler;
    private int clickCount=0;

    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 400;
    /**
     * 自定义回调接口
     */
    private DoubleClickCallback mCallback;

    public interface DoubleClickCallback {
        void onDoubleClick();
        void onOnceClick();
    }
    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
        handler=new Handler();
    }

    /**
     * 触摸事件处理
     * @param v
     * @param event
     * @return
     */
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (MotionEvent.ACTION_DOWN == event.getAction()) {//按下
//            count++;
//            if (1 == count) {
//                firstClick = System.currentTimeMillis();//记录第一次点击时间
//            } else if (2 == count) {
//                secondClick = System.currentTimeMillis();//记录第二次点击时间
//                if (secondClick - firstClick < totalTime) {//判断二次点击时间间隔是否在设定的间隔时间之内
//                    if (mCallback != null) {
//                        mCallback.onDoubleClick();
//                    }
//                    count = 0;
//                    firstClick = 0;
//                } else {
//                    firstClick = secondClick;
//                    count = 1;
//                }
//                secondClick = 0;
//            }
//        }
//        return true;
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickCount++;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (clickCount == 1) {
                        mCallback.onOnceClick();
                    }else if(clickCount>=2){
                        mCallback.onDoubleClick();
                    }
                    handler.removeCallbacksAndMessages(null);
                    //清空handler延时，并防内存泄漏
                    clickCount = 0;//计数清零
                }
            },totalTime);//延时timeout后执行run方法中的代码
        }
        return false;//让点击事件继续传播，方便再给View添加其他事件监听
    }

}
