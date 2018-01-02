package com.example.administrator.demo1;

import android.content.Context;
import android.drm.DrmStore.RightsStatus;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class MyCropView extends View {

    // Private Constants ///////////////////////////////////////////////////////

    private static final float BMP_LEFT = 0f;
    private static final float BMP_TOP = 20f;
    private static final float DEFAULT_BORDER_RECT_WIDTH = 200f;
    private static final float DEFAULT_BORDER_RECT_HEIGHT = 200f;

    private static final int POS_TOP_LEFT = 0;

    private static final int POS_TOP_RIGHT = 1;

    private static final int POS_BOTTOM_LEFT = 2;

    private static final int POS_BOTTOM_RIGHT = 3;

    private static final int POS_TOP = 4;

    private static final int POS_BOTTOM = 5;

    private static final int POS_LEFT = 6;

    private static final int POS_RIGHT = 7;

    private static final int POS_CENTER = 8;

    // this constant would be best to use event number

    private static final float BORDER_LINE_WIDTH = 6f;

    private static final float BORDER_CORNER_LENGTH = 30f;

    private static final float TOUCH_FIELD = 30f;

    // Member Variables ////////////////////////////////////////////////////////

    private int imageWidth = 0;

    private int imageHeight = 0;

    private String mBmpPath;

    private Bitmap mBmpToCrop;

    private RectF mBmpBound;

    private Paint mBmpPaint;


    private Paint mBorderPaint;// 裁剪区边框

    private Paint mGuidelinePaint;

    private Paint mCornerPaint;

    private Paint mBgPaint;


    private RectF mDefaultBorderBound;

    private RectF mBorderBound;


    private PointF mLastPoint = new PointF();


    private float mBorderWidth;

    private float mBorderHeight;


    private int touchPos;

    private Paint criclePaint;
    private Paint criclePaintNew;
//    private boolean isPathTrue = false;

    private Paint linePaint;

    private Path newPath ;

    private float x1 = 0  ,y1 = 0  ;
    private float x2 = 0  ,y2 = 0  ;
    private float x3 = 0  ,y3 = 0  ;
    private float x4 = 0  ,y4 = 0  ;

    // Constructors ////////////////////////////////////////////////////////////
    public MyCropView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public MyCropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }// View Methods ////////////////////////////////////////////////////////////@Override

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        // super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // super.onDraw(canvas);

            if (mBmpPath != null) {
                canvas.drawBitmap(mBmpToCrop, null, mBmpBound, mBmpPaint);
//                canvas.drawRect(mBorderBound.left, mBorderBound.top, mBorderBound.right, mBorderBound.bottom, mBorderPaint);
                int left = (int) mBorderBound.left;
                int right = (int) mBorderBound.right;
                int width = (int) (mBorderBound.right - mBorderBound.left);
                int top = (int) mBorderBound.top;
                int height = (int) (mBorderBound.bottom - mBorderBound.top);
                int bottom = (int) mBorderBound.bottom;
                int circleWidth = 16;
                int circleWidth1= 14;
//                canvas.drawCircle(left, top, circleWidth, criclePaint);
//                canvas.drawCircle(left + width / 2, top, circleWidth, criclePaint);
//                canvas.drawCircle(right, top, circleWidth, criclePaint);
//                canvas.drawCircle(left, top + height / 2, circleWidth, criclePaint);
//                canvas.drawCircle(left, bottom, circleWidth, criclePaint);
//                canvas.drawCircle(left + width / 2, bottom, circleWidth, criclePaint);
//                canvas.drawCircle(right, top + height / 2, circleWidth, criclePaint);
//                canvas.drawCircle(right, bottom, circleWidth, criclePaint);

                newPath = new Path() ;
                newPath.moveTo(x1, y1);
                newPath.lineTo(x2, y2);
                newPath.lineTo(x3, y3);
                newPath.lineTo(x4, y4);
                newPath.lineTo(x1, y1);

                canvas.drawPath(newPath, linePaint);

                Path p1 = new Path() ;
                p1.moveTo(mBmpBound.left, mBmpBound.top);
                p1.lineTo(x1, y1);
                p1.lineTo(x4, y4);
                p1.lineTo(mBmpBound.left, mBmpBound.bottom);
                p1.lineTo(mBmpBound.left, mBmpBound.top);
                canvas.drawPath(p1, mBgPaint);
                Path p2 = new Path() ;
                p2.moveTo(mBmpBound.left, mBmpBound.top);
                p2.lineTo(x1, y1);
                p2.lineTo(x2, y2);
                p2.lineTo(mBmpBound.right, mBmpBound.top);
                p2.lineTo(mBmpBound.left, mBmpBound.top);
                canvas.drawPath(p2, mBgPaint);
                Path p3 = new Path() ;
                p3.moveTo(mBmpBound.right, mBmpBound.top);
                p3.lineTo(x2, y2);
                p3.lineTo(x3, y3);
                p3.lineTo(mBmpBound.right, mBmpBound.bottom);
                p3.lineTo(mBmpBound.right, mBmpBound.top);
                canvas.drawPath(p3, mBgPaint);
                Path p4 = new Path() ;
                p4.moveTo(mBmpBound.right, mBmpBound.bottom);
                p4.lineTo(x3, y3);
                p4.lineTo(x4, y4);
                p4.lineTo(mBmpBound.left, mBmpBound.bottom);
                p4.lineTo(mBmpBound.right, mBmpBound.bottom);
                canvas.drawPath(p4, mBgPaint);
//                drawGuidlines(canvas);
//                drawBackground(canvas);
                canvas.drawCircle(x1, y1, circleWidth, criclePaint);
                canvas.drawCircle(x2, y2, circleWidth, criclePaint);
                canvas.drawCircle(x3, y3, circleWidth, criclePaint);
                canvas.drawCircle(x4, y4, circleWidth, criclePaint);

                canvas.drawCircle(x1+(x2-x1)/2,(y1>y2)?(y2+(y1-y2)/2):(y1+(y2-y1)/2), circleWidth, criclePaint);
                canvas.drawCircle(x1+(x2-x1)/2,(y1>y2)?(y2+(y1-y2)/2):(y1+(y2-y1)/2), circleWidth1, criclePaintNew);

                canvas.drawCircle(x1+(x4-x1)/2,y1+(y4-y1)/2, circleWidth, criclePaint);
                canvas.drawCircle(x1+(x4-x1)/2,y1+(y4-y1)/2, circleWidth1, criclePaintNew);

                canvas.drawCircle(x4+(x3-x4)/2,(y4>y3)?(y3+(y4-y3)/2):(y4+(y3-y4)/2), circleWidth, criclePaint);
                canvas.drawCircle(x4+(x3-x4)/2,(y4>y3)?(y3+(y4-y3)/2):(y4+(y3-y4)/2), circleWidth1, criclePaintNew);

                canvas.drawCircle(x2+(x3-x2)/2,y2+(y3-y2)/2, circleWidth, criclePaint);
                canvas.drawCircle(x2+(x3-x2)/2,y2+(y3-y2)/2, circleWidth1, criclePaintNew);


                canvas.drawCircle(x1, y1, circleWidth1, criclePaintNew);
                canvas.drawCircle(x2, y2, circleWidth1, criclePaintNew);
                canvas.drawCircle(x3, y3, circleWidth1, criclePaintNew);
                canvas.drawCircle(x4, y4, circleWidth1, criclePaintNew);

            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setLastPosition(event);
                getParent().requestDisallowInterceptTouchEvent(true);
                // onActionDown(event.getX(), event.getY());
                touchPos = detectTouchPositionNew(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX(), event.getY());
                setLastPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return true;
    }

    // Public Methods //////////////////////////////////////////////////////////
    public String getBmpPath() {
        return mBmpPath;
    }

    public void setBmpPath(String picPath, int width, int height) {
        this.mBmpPath = picPath;
        this.imageWidth = width;
        this.imageHeight = height;
        setBmp();
    }

    public Bitmap getCroppedImage() {
        // 先不考虑图片被压缩的情况 就当作现在的图片就是1：1的

        try {
//            Bitmap bm = Bitmap.createBitmap(mBmpToCrop, (int) mBorderBound.left, (int) mBorderBound.top, (int) mBorderWidth,
//                    (int) mBorderHeight);

            Matrix matrix = new Matrix();
            float[] src = new float[] { 0, 0, // 左上
                    mBmpToCrop.getWidth(), 0,// 右上
                    mBmpToCrop.getWidth(), mBmpToCrop.getHeight(),// 右下
                    0, mBmpToCrop.getHeight() };// 左下
            float[] dst = new float[] { 0, 0,
                    mBmpToCrop.getWidth(), 30,
                    mBmpToCrop.getWidth(), mBmpToCrop.getHeight() - 30,
                    0,mBmpToCrop.getHeight() };
            matrix.setPolyToPoly(src, 0, dst, 0, src.length/2);
            Bitmap bm = Bitmap.createBitmap(mBmpToCrop, (int) x1, (int) y1, (int) (x3-x1),
                    (int) (y3-y1) ,matrix ,false );

            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Private Methods /////////////////////////////////////////////////////////
    private void init(Context context) {

        mBmpPaint = new Paint();
        // 以下是抗锯齿
        mBmpPaint.setAntiAlias(true);// 防止边缘的锯齿
        mBmpPaint.setFilterBitmap(true);// 对位图进行滤波处理

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Style.STROKE);
        mBorderPaint.setColor(Color.parseColor("#AAFFFFFF"));
        mBorderPaint.setStrokeWidth(BORDER_LINE_WIDTH);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setColor(Color.parseColor("#AAFFFFFF"));
        mGuidelinePaint.setStrokeWidth(1f);

        mCornerPaint = new Paint();

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);// 防止边缘的锯齿
        mBgPaint.setColor(Color.parseColor("#B0000000"));
        mBgPaint.setAlpha(150);

        criclePaint = new Paint();
        criclePaint.setColor(Color.parseColor("#24d4ae"));
        criclePaint.setAntiAlias(true);// 防止边缘的锯齿
        criclePaint.setStyle(Paint.Style.STROKE);
        criclePaint.setStrokeWidth(5);

        criclePaintNew = new Paint();
        criclePaintNew.setColor(Color.parseColor("#aaffffff"));
        criclePaintNew.setAntiAlias(true);// 防止边缘的锯齿
        mBgPaint.setAlpha(150);
        criclePaintNew.setStrokeWidth(5);

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#24d4ae"));
        linePaint.setAntiAlias(true);// 防止边缘的锯齿
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4);


    }

    private void setBmp() {
        mBmpToCrop = getBitmapFromPath(mBmpPath);
        if (mBmpToCrop == null) {
            return;
        }

        mBmpBound = new RectF();
        mBmpBound.left = BMP_LEFT;
        mBmpBound.top = BMP_TOP;
//      mBmpBound.right = mBmpBound.left + mBmpToCrop.getWidth();
//      mBmpBound.bottom = mBmpBound.top + mBmpToCrop.getHeight();
        mBmpBound.bottom = mBmpBound.top + imageHeight;

        int width = mBmpToCrop.getWidth() / ((mBmpToCrop.getHeight() / imageHeight));
        mBmpBound.right = mBmpBound.left + width;

        mBmpToCrop = setImgSize(mBmpToCrop, width, (int) (mBmpBound.top + imageHeight));

        // 使裁剪框一开始出现在图片的中心位置
        mDefaultBorderBound = new RectF();
        mDefaultBorderBound.left = (mBmpBound.left + mBmpBound.right - DEFAULT_BORDER_RECT_WIDTH) / 2;
        mDefaultBorderBound.top = (mBmpBound.top + mBmpBound.bottom - DEFAULT_BORDER_RECT_HEIGHT) / 2;
        mDefaultBorderBound.right = mDefaultBorderBound.left + DEFAULT_BORDER_RECT_WIDTH;
        mDefaultBorderBound.bottom = mDefaultBorderBound.top + DEFAULT_BORDER_RECT_HEIGHT;

        mBorderBound = new RectF();
        mBorderBound.left = mDefaultBorderBound.left;
        mBorderBound.top = mDefaultBorderBound.top;
        mBorderBound.right = mDefaultBorderBound.right;
        mBorderBound.bottom = mDefaultBorderBound.bottom;

        x1 = mBorderBound.left ;
        y1 = mBorderBound.top ;

        x2 = mBorderBound.right ;
        y2 = mBorderBound.top ;

        x3 = mBorderBound.right ;
        y3 = mBorderBound.bottom ;

        x4 = mBorderBound.left ;
        y4 = mBorderBound.bottom ;

        getBorderEdgeLength();
        invalidate();

    }




    //修改bitmap的 宽高
    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);

        return newbm;
    }

    public Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
    }


    private void drawBackground(Canvas canvas) {

                 /*-
           -------------------------------------
           |                top                |
           -------------------------------------
           |      |                    |       |<——————————mBmpBound
           |      |                    |       |
           | left |                    | right |
           |      |                    |       |
           |      |                  <─┼───────┼────mBorderBound
           -------------------------------------
           |              bottom               |
           -------------------------------------
          */

        // Draw "top", "bottom", "left", then "right" quadrants.
        // because the border line width is larger than 1f, in order to draw a complete border rect ,
        // i have to change zhe rect coordinate to draw
        float delta = BORDER_LINE_WIDTH / 2;
        float left = mBorderBound.left - delta;
        float top = mBorderBound.top - delta;
        float right = mBorderBound.right + delta;
        float bottom = mBorderBound.bottom + delta;

        // -------------------------------------------------------------------------------移动到上下两端会多出来阴影
        canvas.drawRect(mBmpBound.left, mBmpBound.top, mBmpBound.right, top, mBgPaint);
        canvas.drawRect(mBmpBound.left, bottom, mBmpBound.right, mBmpBound.bottom, mBgPaint);
        canvas.drawRect(mBmpBound.left, top, left, bottom, mBgPaint);
        canvas.drawRect(right, top, mBmpBound.right, bottom, mBgPaint);
    }

    // 画裁剪区域中间的参考线
    private void drawGuidlines(Canvas canvas) {
        // Draw vertical guidelines.
        final float oneThirdCropWidth = mBorderBound.width() / 3;

        final float x1 = mBorderBound.left + oneThirdCropWidth;
        canvas.drawLine(x1, mBorderBound.top, x1, mBorderBound.bottom, mGuidelinePaint);
        final float x2 = mBorderBound.right - oneThirdCropWidth;
        canvas.drawLine(x2, mBorderBound.top, x2, mBorderBound.bottom, mGuidelinePaint);

        // Draw horizontal guidelines.
        final float oneThirdCropHeight = mBorderBound.height() / 3;

        final float y1 = mBorderBound.top + oneThirdCropHeight;
        canvas.drawLine(mBorderBound.left, y1, mBorderBound.right, y1, mGuidelinePaint);
        final float y2 = mBorderBound.bottom - oneThirdCropHeight;
        canvas.drawLine(mBorderBound.left, y2, mBorderBound.right, y2, mGuidelinePaint);
    }

    private void onActionDown(float x, float y) {

    }

    private void onActionMove(float x, float y) {
        float deltaX = x - mLastPoint.x;
        float deltaY = y - mLastPoint.y;
        // 这里先不考虑裁剪框放最大的情况
        switch (touchPos) {
            case POS_CENTER:
                mBorderBound.left += deltaX;
                // fix border position
                if (mBorderBound.left < mBmpBound.left)
                    mBorderBound.left = mBmpBound.left;
                if (mBorderBound.left > mBmpBound.right - mBorderWidth)
                    mBorderBound.left = mBmpBound.right - mBorderWidth;

                mBorderBound.top += deltaY;
                if (mBorderBound.top < mBmpBound.top)
                    mBorderBound.top = mBmpBound.top;

                if (mBorderBound.top > mBmpBound.bottom - mBorderHeight)
                    mBorderBound.top = mBmpBound.bottom - mBorderHeight;

                mBorderBound.right = mBorderBound.left + mBorderWidth;
                mBorderBound.bottom = mBorderBound.top + mBorderHeight;

                break;

            case POS_TOP:
                resetTop(deltaY);
                break;
            case POS_BOTTOM:
                resetBottom(deltaY);
                break;
            case POS_LEFT:
                resetLeft(deltaX);
                break;
            case POS_RIGHT:
                resetRight(deltaX);
                break;
            case POS_TOP_LEFT:
//                resetTop(deltaY);
//                resetLeft(deltaX);
                x1 = x ;
                y1 = y ;
                break;
            case POS_TOP_RIGHT:
//                resetTop(deltaY);
//                resetRight(deltaX);
                x2 = x ;
                y2 = y ;
                break;
            case POS_BOTTOM_LEFT:
//                resetBottom(deltaY);
//                resetLeft(deltaX);
                x4 = x ;
                y4 = y ;
                break;
            case POS_BOTTOM_RIGHT:
//                resetBottom(deltaY);
//                resetRight(deltaX);
                x3 = x ;
                y3 = y ;
                break;
            default:

                break;
        }
        invalidate();
    }

    private void onActionUp(float x, float y) {

    }


    private int detectTouchPositionNew(float x, float y) {

        if (x>x1-TOUCH_FIELD&&x<x1+TOUCH_FIELD&&y>y1-TOUCH_FIELD&&y<y1+TOUCH_FIELD){
            return POS_TOP_LEFT;
        }else if (x>x2-TOUCH_FIELD&&x<x2+TOUCH_FIELD&&y>y2-TOUCH_FIELD&&y<y2+TOUCH_FIELD){
            return POS_TOP_RIGHT;
        }else if (x>x3-TOUCH_FIELD&&x<x3+TOUCH_FIELD&&y>y3-TOUCH_FIELD&&y<y3+TOUCH_FIELD){
            return POS_BOTTOM_RIGHT;
        }else if (x>x4-TOUCH_FIELD&&x<x4+TOUCH_FIELD&&y>y4-TOUCH_FIELD&&y<y4+TOUCH_FIELD){
            return POS_BOTTOM_LEFT;
        }
        return -1 ;
    }
    private int detectTouchPosition(float x, float y) {
        //点击中间
        if (x > mBorderBound.left + TOUCH_FIELD && x < mBorderBound.right - TOUCH_FIELD
                && y > mBorderBound.top + TOUCH_FIELD && y < mBorderBound.bottom - TOUCH_FIELD)
            return POS_CENTER;
        if (x > mBorderBound.left + BORDER_CORNER_LENGTH && x < mBorderBound.right - BORDER_CORNER_LENGTH) {
            if (y > mBorderBound.top - TOUCH_FIELD && y < mBorderBound.top + TOUCH_FIELD)
                return POS_TOP;
            if (y > mBorderBound.bottom - TOUCH_FIELD && y < mBorderBound.bottom + TOUCH_FIELD)
                return POS_BOTTOM;
        }
        if (y > mBorderBound.top + BORDER_CORNER_LENGTH && y < mBorderBound.bottom - BORDER_CORNER_LENGTH) {
            if (x > mBorderBound.left - TOUCH_FIELD && x < mBorderBound.left + TOUCH_FIELD)
                return POS_LEFT;
            if (x > mBorderBound.right - TOUCH_FIELD && x < mBorderBound.right + TOUCH_FIELD)
                return POS_RIGHT;
        }
        // 前面的逻辑已经排除掉了几种情况 所以后面的 ┏ ┓ ┗ ┛ 边角就按照所占区域的方形来判断就可以了
        if (x > mBorderBound.left - TOUCH_FIELD && x < mBorderBound.left + BORDER_CORNER_LENGTH) {
            if (y > mBorderBound.top - TOUCH_FIELD && y < mBorderBound.top + BORDER_CORNER_LENGTH)
                return POS_TOP_LEFT;
            if (y > mBorderBound.bottom - BORDER_CORNER_LENGTH && y < mBorderBound.bottom + TOUCH_FIELD)
                return POS_BOTTOM_LEFT;
        }
        if (x > mBorderBound.right - BORDER_CORNER_LENGTH && x < mBorderBound.right + TOUCH_FIELD) {
            if (y > mBorderBound.top - TOUCH_FIELD && y < mBorderBound.top + BORDER_CORNER_LENGTH)
                return POS_TOP_RIGHT;
            if (y > mBorderBound.bottom - BORDER_CORNER_LENGTH && y < mBorderBound.bottom + TOUCH_FIELD)
                return POS_BOTTOM_RIGHT;
        }
        return -1;
    }

    private void setLastPosition(MotionEvent event) {
        mLastPoint.x = event.getX();
        mLastPoint.y = event.getY();
    }

    private void getBorderEdgeLength() {
        mBorderWidth = mBorderBound.width();
        mBorderHeight = mBorderBound.height();
    }

    private void getBorderEdgeWidth() {
        mBorderWidth = mBorderBound.width();
    }

    private void getBorderEdgeHeight() {
        mBorderHeight = mBorderBound.height();
    }

    private void resetLeft(float delta) {
        mBorderBound.left += delta;

        getBorderEdgeWidth();
        fixBorderLeft();
    }

    private void resetTop(float delta) {
        mBorderBound.top += delta;
        getBorderEdgeHeight();
        fixBorderTop();
    }

    private void resetRight(float delta) {
        mBorderBound.right += delta;

        getBorderEdgeWidth();
        fixBorderRight();

    }

    private void resetBottom(float delta) {
        mBorderBound.bottom += delta;

        getBorderEdgeHeight();
        fixBorderBottom();
    }

    private void fixBorderLeft() {
        // fix left
        if (mBorderBound.left < mBmpBound.left)
            mBorderBound.left = mBmpBound.left;
        if (mBorderWidth < 2 * BORDER_CORNER_LENGTH)
            mBorderBound.left = mBorderBound.right - 2 * BORDER_CORNER_LENGTH;
    }

    private void fixBorderTop() {
        // fix top
        if (mBorderBound.top < mBmpBound.top)
            mBorderBound.top = mBmpBound.top;
        if (mBorderHeight < 2 * BORDER_CORNER_LENGTH)
            mBorderBound.top = mBorderBound.bottom - 2 * BORDER_CORNER_LENGTH;
    }

    private void fixBorderRight() {
        // fix right
        if (mBorderBound.right > mBmpBound.right)
            mBorderBound.right = mBmpBound.right;
        if (mBorderWidth < 2 * BORDER_CORNER_LENGTH)
            mBorderBound.right = mBorderBound.left + 2 * BORDER_CORNER_LENGTH;
    }

    private void fixBorderBottom() {
        // fix bottom
        if (mBorderBound.bottom > mBmpBound.bottom)
            mBorderBound.bottom = mBmpBound.bottom;
        if (mBorderHeight < 2 * BORDER_CORNER_LENGTH)
            mBorderBound.bottom = mBorderBound.top + 2 * BORDER_CORNER_LENGTH;
    }
}