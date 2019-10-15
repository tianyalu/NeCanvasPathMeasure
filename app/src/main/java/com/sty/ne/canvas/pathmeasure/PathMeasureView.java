package com.sty.ne.canvas.pathmeasure;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tian on 2019/10/15.
 */

public class PathMeasureView extends View {
    private Paint mPaint = new Paint();
    private Paint mLinePaint = new Paint(); //坐标系

    private Path mPath = new Path();
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    private Matrix mMatrix = new Matrix();
    private Bitmap mBitmap;
    private float mFloat;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);

        //缩小图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);

        canvas.translate(getWidth() / 2, getHeight() / 2);

        /**
         * pathMeasure需要关联一个创建好的path，forceClosed会影响path的测量结果，
         */
//        Path path = new Path();
//        path.lineTo(0, 200);
//        path.lineTo(200, 200);
//        path.lineTo(200, 0);

//        PathMeasure pathMeasure = new PathMeasure();
//        pathMeasure.setPath(path, true);
//        Log.i("TAG", "onDraw:forceClosed=true " + pathMeasure.getLength()); //800
//
//        PathMeasure pathMeasure1 = new PathMeasure();
//        pathMeasure1.setPath(path, false);
//        Log.i("TAG", "onDraw:forceClosed=false " + pathMeasure1.getLength()); //600
//
//        PathMeasure pathMeasure2 = new PathMeasure(path, false);
//        Log.i("TAG", "onDraw:PathMeasure(path, false) " + pathMeasure2.getLength()); //600
//
//        path.lineTo(200, -200);
//        Log.i("TAG", "onDraw:PathMeasure(path, false) " + pathMeasure2.getLength()); //600
//
//        // 如果path进行了调整，需要重新调用setPath()方法进行关联
//        pathMeasure2.setPath(path, false);
//        Log.i("TAG", "onDraw:PathMeasure(path, false) " + pathMeasure2.getLength()); //800

//        canvas.drawPath(path, mPaint);

        /**
         * getSegment
         */
//        Path path = new Path();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//
//        Path dst = new Path();
//        dst.lineTo(-300, -300); //添加一条直线
//
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        //截取一部分存入dst中，并且使用moveTo保持截取得到的path第一个点位置保持不变
//        //startD:距离起始点的长度-表示开始位置  stopD:距离起始点的长度，表示结束位置
//        pathMeasure.getSegment(200, 1000, dst, true);
//
//        canvas.drawPath(path, mPaint);
//        canvas.drawPath(dst, mLinePaint);

        /**
         * pathMeasure.getLength() 获取的仅仅是当前曲线的长度，而非整个path的长度
         */
//        Path path = new Path();
//        path.addRect(-100, -100, 100, 100, Path.Direction.CW); //添加一个矩形
//        path.addOval(-200, -200, 200, 200, Path.Direction.CW); //添加一个椭圆
//        canvas.drawPath(path, mPaint);
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        Log.i("TAG", "onDraw:forceClosed=false " + pathMeasure.getLength()); //800
//        //跳转到下一条曲线
//        pathMeasure.nextContour();
//        Log.i("TAG", "onDraw:forceClosed=false " + pathMeasure.getLength()); //1256.1292


        /**
         * 箭头旋转动画
         */
        mPath.reset();
        mPath.addCircle(0, 0, 200, Path.Direction.CW); //顺时针方向
        canvas.drawPath(mPath, mPaint);

        mFloat += 0.01;
        if(mFloat >= 1) {
            mFloat = 0;
        }

//        PathMeasure pathMeasure = new PathMeasure(mPath, false);
//        //pos[0],pos[1]:距离path起点distance长度的点的坐标值  //参考show/postan1.png, show/postan2.png
//        //tan[0]：pos点切线与x轴夹角在单位圆中临边的长度  tan[1]:pos点切线与x轴夹角在单位圆中对边的长度
////        pathMeasure.getPosTan(0, pos, tan);
////        pathMeasure.getPosTan(pathMeasure.getLength() * 7 / 8, pos, tan); //顺时针方向
//        pathMeasure.getPosTan(pathMeasure.getLength() * mFloat, pos, tan); //顺时针方向
//
//        Log.i("TAG", "onDraw: pos[0]= " + pos[0] + ";pos[1]=" + pos[1]);
//        Log.i("TAG", "onDraw: tan[0]= " + tan[0] + ";tan[1]=" + tan[1]);
//
//        //计算当前的切线与x轴夹角度数
//        double degrees = Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI;
//        Log.i("TAG", "onDraw: degrees= " + degrees);
//
//        mPath.addCircle(pos[0], pos[1], 12, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);
//
//        mMatrix.reset();
//        //进行角度旋转
//        mMatrix.postRotate((float) degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//        //将图片的绘制中心点与当前点重合
//        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
//        canvas.drawBitmap(mBitmap, mMatrix, mPaint);


        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        //将pos信息和tan信息保存在mMatrix上
        pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, mMatrix,
                PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        //将图片的旋转坐标调整到图片的中心位置
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);


        invalidate();
    }
}
