package com.ys.zy.roulette.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.roulette.bean.LPBean;
import com.ys.zy.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LPView extends View {
    private Context context;
    private int w;
    private int h;
    private Paint paint;
    private RectF rectF;
    private Rect destRect;
    private List<LPBean> list;
    private Bitmap bgBitmap, insideBitmap;
    private List<Bitmap> bitmapList;
    private float insideCircleOffset = 0.6f;
    private Rect insideRect;
    private String currentResult = "";
    private int bgCircleRadius;//外层背景图半径
    private int outCircleRadius;//外部扇形半径
    private int inCircleRadius;//内部背景图半径

    public LPView(Context context) {
        this(context, null);
    }

    public LPView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LPView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initPaint();
        bgBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.lp_img_bg_out)).getBitmap();
        insideBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.lp_img_bg_inside)).getBitmap();
        bitmapList = new ArrayList<>();
    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        //设置画笔默认颜色
        paint.setColor(Color.WHITE);
        //设置画笔模式：填充
        paint.setStyle(Paint.Style.FILL);
        //
        paint.setTextSize(30);
        //初始化区域
        rectF = new RectF();
        destRect = new Rect();
        insideRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measure(widthMeasureSpec);
        measure(heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    private void measure(int measureValue) {
        int defalueWidthSize = 150;//默认宽度
        int defalueHeightSize = 150;//默认高度
        int mode = MeasureSpec.getMode(measureValue);
        int specValue = MeasureSpec.getSize(measureValue);
        Log.e("onMeasure", "mode: " + mode + "specValue: " + specValue);
        switch (mode) {
            //指定一个默认值
            case MeasureSpec.UNSPECIFIED:
                Log.e("onMeasure", "mode: " + mode + "UNSPECIFIED ");
                w = defalueWidthSize;
                h = defalueHeightSize;
                break;
            //取测量值
            case MeasureSpec.EXACTLY:
                Log.e("onMeasure", "mode: " + mode + "EXACTLY ");
                w = specValue;
                h = specValue;
                break;
            //取测量值和默认值中的最小值
            case MeasureSpec.AT_MOST:
                Log.e("onMeasure", "mode: " + mode + "AT_MOST ");
                h = w;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("lh", "onSizeChanged执行w=" + w + "----h=" + h);
        this.w = w;     //获取宽高
        this.h = h;
        bgCircleRadius = Math.min(w, h) / 2;
        outCircleRadius = Math.min(w, h) / 2 - DensityUtil.dp2px(context, 18);
        inCircleRadius = (int) (insideCircleOffset * bgCircleRadius);
        rectF.set(-outCircleRadius, -outCircleRadius, outCircleRadius, outCircleRadius);                    //设置将要用来画扇形的矩形的轮廓
        destRect.set(-bgCircleRadius + DensityUtil.dp2px(context, 3.5f), -bgCircleRadius, bgCircleRadius - DensityUtil.dp2px(context, 3.5f), bgCircleRadius);
        insideRect.set(-inCircleRadius, -inCircleRadius, inCircleRadius, inCircleRadius);
    }

    private int[] offset = new int[]{5, 5, 2, 1, 2, 2, 2, 3, 5, 6, 7, 6};
    private double[] offsetX = new double[]{0.98, 0.99, 0.96, 0.91, 0.92, 0.9, 0.9, 0.87, 0.87, 0.95, 0.93, 0.96};
    private double[] offsetY = new double[]{0.9, 0.95, 0.98, 0.95, 0.95, 0.9, 0.9, 0.91, 0.89, 0.90, 0.94, 0.94};
    private double bitmapOffset = 0.85;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(w / 2, h / 2);             //将画布坐标原点移到中心位置
        float currentStartAngle = 0;                //起始角度
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < list.size(); i++) {
            LPBean lpBean = list.get(i);
            paint.setColor(Color.parseColor(lpBean.color));
            //绘制扇形(通过绘制圆弧)
            canvas.drawArc(rectF, currentStartAngle, lpBean.angle, true, paint);
            //绘制扇形上文字
            float textAngle = currentStartAngle + offset[i];    //计算文字位置角度
            paint.setColor(Color.BLACK);
            paint.setTextSize(DensityUtil.sp2px(context, 16));
            paint.setTextAlign(Paint.Align.RIGHT);
            float x = (float) (outCircleRadius * offsetX[i] * Math.cos(textAngle * Math.PI / 180));    //计算文字位置坐标
            float y = (float) (outCircleRadius * offsetY[i] * Math.sin(textAngle * Math.PI / 180));
            canvas.drawText(lpBean.name, x, y, paint);    //绘制文字


            //绘制bitmap
            paint.setTextAlign(Paint.Align.LEFT);
            float textAngle1 = currentStartAngle + 15;
            float x1 = (float) (outCircleRadius * bitmapOffset * Math.cos(textAngle1 * Math.PI / 180));
            float y1 = (float) (outCircleRadius * bitmapOffset * Math.sin(textAngle1 * Math.PI / 180));
            float left = x1 - bitmapList.get(i).getWidth() / 2;
            float top = y1 - bitmapList.get(i).getHeight() / 2;
            canvas.drawBitmap(bitmapList.get(i), left, top, paint);

            //绘制投注金额
            paint.setColor(Color.parseColor("#f7f7f7"));
            paint.setTextSize(DensityUtil.sp2px(context, 12));
            float x2 = (float) (outCircleRadius * 0.85 * Math.cos(textAngle1 * Math.PI / 180));
            float y2 = (float) (outCircleRadius * 0.85 * Math.sin(textAngle1 * Math.PI / 180) - DensityUtil.dp2px(context, 5));
            float y3 = (float) (outCircleRadius * 0.85 * Math.sin(textAngle1 * Math.PI / 180) + DensityUtil.dp2px(context, 10));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(doubleToStr(lpBean.totalValue), x2, y2, paint);
            paint.setColor(Color.parseColor("#14c5ed"));
            canvas.drawText(doubleToStr(lpBean.myValue), x2, y3, paint);
            currentStartAngle += lpBean.angle;     //改变起始角度
        }
        canvas.drawBitmap(bgBitmap, null, destRect, paint);
        canvas.drawBitmap(insideBitmap, null, insideRect, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DensityUtil.sp2px(context, 56));
        paint.setColor(Color.parseColor("#f7f7f7"));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float dy = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        canvas.drawText(currentResult, 0, dy, paint);
    }

    private void initData() {
        if (null == list || list.size() == 0) {
            return;
        }

        float sumValue = 0;                 //数值和
        for (int i = 0; i < list.size(); i++) {
            LPBean lpBean = list.get(i);
            sumValue += lpBean.value;
        }

        for (LPBean lpBean : list) {
            float percentage = lpBean.value / sumValue;    //计算百分比
            float angle = percentage * 360;           //对应的角度
            lpBean.percentage = percentage;
            lpBean.angle = angle;
            bitmapList.add(((BitmapDrawable) getResources().getDrawable(lpBean.drawableId)).getBitmap());
        }
    }

    //设置数据
    public void setData(List<LPBean> shanXinViewDatas) {
        this.list = shanXinViewDatas;
        currentResult = "";
        initData();     //设置数据的百分度和角度
        invalidate();   //刷新UI
    }

    /**
     * 恢复默认
     */
    public void clearColorAndResult() {
        currentResult = "";
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                list.get(i).color = "#ffa958";
            } else {
                list.get(i).color = "#ff854b";
            }
            list.get(i).myValue = 0;
            list.get(i).totalValue = 0;
        }
        invalidate();
    }

    private RandomThraed randomThraed;

    public void startRandomColor() {
        if (randomThraed != null) {
            randomThraed.setOver(true);
            randomThraed.interrupt();
            randomThraed = null;
        }
        randomThraed = new RandomThraed();
        randomThraed.start();
    }

    /**
     * 线程是否正在进行
     *
     * @return
     */
    public boolean isRamdomRunning() {
        return randomThraed != null && !randomThraed.isOver();
    }

    public void closeRandomColor() {
        if (randomThraed != null) {
            randomThraed.setOver(true);
            randomThraed.interrupt();
            randomThraed = null;
        }
    }

    class RandomThraed extends Thread {
        private volatile boolean isOver = false;

        public boolean isOver() {
            return isOver;
        }

        public void setOver(boolean over) {
            isOver = over;
        }

        @Override
        public void run() {
            while (!isOver) {
                try {
                    Thread.sleep(100);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int i = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                for (int j = 0; j < list.size(); j++) {
                    if (j % 2 == 0) {
                        list.get(j).color = "#ffa958";
                    } else {
                        list.get(j).color = "#ff854b";
                    }
                }
                list.get(i).color = "#f7f7f7";
                currentResult = list.get(i).name;
                i++;
                if (i > 11) {
                    i = 0;
                }
                invalidate();
            } else if (msg.what == 1) {

            }
        }
    };

    public void setResult(final String result) {
        if (currentResult == result) {
            closeRandomColor();
            currentResult = result;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).name.equals(result)) {
                    list.get(i).color = "#f7f7f7";
                } else {
                    if (i % 2 == 0) {
                        list.get(i).color = "#ffa958";
                    } else {
                        list.get(i).color = "#ff854b";
                    }
                }
            }
            invalidate();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setResult(result);
                }
            }, 100);
        }
    }

    public static final String[] sx = new String[]{"兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪", "鼠", "牛", "虎"};

    public void setRandomResult() {
        Random random = new Random();
        String result = sx[random.nextInt(12)];
        setResult(result);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doOnSpecialTypeClick(event);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void doOnSpecialTypeClick(MotionEvent event) {
        if (list == null || list.isEmpty()) return;
        float eventX = event.getX();
        float eventY = event.getY();
        double alfa = 0;
        //点击的位置到圆心距离的平方
        double distance = Math.pow(eventX - bgCircleRadius, 2) + Math.pow(eventY - bgCircleRadius, 2);
        //判断点击的坐标是否在环内
        if (distance < Math.pow(bgCircleRadius, 2) && distance > Math.pow(inCircleRadius, 2)) {
            int which = touchOnWhichPart(event);
            switch (which) {
                case PART_ONE:
                    alfa = Math.atan2(eventY - bgCircleRadius, eventX - bgCircleRadius) * 180 / Math.PI;
                    break;
                case PART_TWO:
                    alfa = Math.atan2(bgCircleRadius - eventX, eventY - bgCircleRadius) * 180 / Math.PI + 90;
                    break;
                case PART_THREE:
                    alfa = Math.atan2(bgCircleRadius - eventY, bgCircleRadius - eventX) * 180 / Math.PI + 180;
                    break;
                case PART_FOUR:
                    alfa = Math.atan2(eventX - bgCircleRadius, bgCircleRadius - eventY) * 180 / Math.PI + 270;
                    break;
            }
//            Toast.makeText(context, "x=" + eventX + "--y=" + eventY + "---bgCircleRadius=" + bgCircleRadius + "---alfa=" + alfa + "--which=" + which, 1).show();
            if (clickListener != null) {
                int position = (int) (alfa / 30);
                clickListener.click(position, list.get(position));
            }
        }
    }

    private static final int PART_ONE = 1;

    private static final int PART_TWO = 2;

    private static final int PART_THREE = 3;

    private static final int PART_FOUR = 4;

    /**
     * 3 |  4
     * -----|-----
     * 2 |  1
     * 圆被分成四等份，判断点击在园的哪一部分
     */
    private int touchOnWhichPart(MotionEvent event) {
        if (event.getX() > bgCircleRadius) {
            if (event.getY() > bgCircleRadius) return PART_ONE;
            else return PART_FOUR;
        } else {
            if (event.getY() > bgCircleRadius) return PART_TWO;
            else return PART_THREE;
        }
    }


    public interface ClickListener {
        void click(int position, LPBean lpBean);
    }

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public String doubleToStr(double d) {
        if (d > 0) {
            return String.valueOf(d);
        } else {
            return "";
        }
    }

    public List<LPBean> getTZList() {
        List<LPBean> tzList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).myValue != 0) {
                tzList.add(list.get(i));
            }
        }
        return tzList;
    }
}
