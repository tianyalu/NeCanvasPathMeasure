### NeCanvasPathMeasure PathMeasure路径测量方法及箭头旋转动画
#### 一、PathMeasure路径测量方法
##### 1. PathMeasure
pathMeasure需要关联一个创建好的path，forceClosed会影响path的测量结果
```android
    PathMeasure pathMeasure = new PathMeasure();
    pathMeasure.setPath(path, true);
```
##### 2. pathMeasure.getSegment()
截取一部分存入dst中，并且使用moveTo保持截取得到的path第一个点位置保持不变
```android
    Path path = new Path();
    path.addRect(-200, -200, 200, 200, Path.Direction.CW);

    Path dst = new Path();
    dst.lineTo(-300, -300); //添加一条直线

    PathMeasure pathMeasure = new PathMeasure(path, false);
    //startD:距离起始点的长度-表示开始位置  stopD:距离起始点的长度，表示结束位置
    pathMeasure.getSegment(startD, stopD, dst, true);

    canvas.drawPath(path, mPaint);
    canvas.drawPath(dst, mLinePaint);
```
##### 3. pathMeasure.getLength()
获取的仅仅是当前曲线的长度，而非整个path的长度
```android 
    Path path = new Path();
    path.addRect(-100, -100, 100, 100, Path.Direction.CW); //添加一个矩形
    path.addOval(-200, -200, 200, 200, Path.Direction.CW); //添加一个椭圆
    canvas.drawPath(path, mPaint);
    PathMeasure pathMeasure = new PathMeasure(path, false);
    Log.i("TAG", "onDraw:forceClosed=false " + pathMeasure.getLength()); //800
    //跳转到下一条曲线
    pathMeasure.nextContour();
    Log.i("TAG", "onDraw:forceClosed=false " + pathMeasure.getLength()); //1256.1292
```
##### 4. pathMeasure.getPosTan()
获取距离path起点distance长度的点的坐标及角度信息
```android
    PathMeasure pathMeasure = new PathMeasure(mPath, false);
    //pos[0],pos[1]:距离path起点distance长度的点的坐标值  //参考show/postan1.png, show/postan2.png
    //tan[0]：pos点切线与x轴夹角在单位圆中临边的长度  tan[1]:pos点切线与x轴夹角在单位圆中对边的长度
//        pathMeasure.getPosTan(0, pos, tan);
//        pathMeasure.getPosTan(pathMeasure.getLength() * 7 / 8, pos, tan); //顺时针方向
    pathMeasure.getPosTan(pathMeasure.getLength() * mFloat, pos, tan); //顺时针方向

    Log.i("TAG", "onDraw: pos[0]= " + pos[0] + ";pos[1]=" + pos[1]);
    Log.i("TAG", "onDraw: tan[0]= " + tan[0] + ";tan[1]=" + tan[1]);

    //计算当前的切线与x轴夹角度数
    double degrees = Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI;
    Log.i("TAG", "onDraw: degrees= " + degrees);

    mPath.addCircle(pos[0], pos[1], 12, Path.Direction.CW);
    canvas.drawPath(mPath, mPaint);
```
![image](https://github.com/tianyalu/NeCanvasPathMeasure/blob/master/show/postan1.png)
![image](https://github.com/tianyalu/NeCanvasPathMeasure/blob/master/show/postan2.png)
##### 5. pathMeasure.getMatrix()
获取距离path起点distance长度的点的矩阵信息
```android
    PathMeasure pathMeasure = new PathMeasure(mPath, false);
    //将pos信息和tan信息保存在mMatrix上
    pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, mMatrix,
            PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
    //将图片的旋转坐标调整到图片的中心位置
    mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
    canvas.drawBitmap(mBitmap, mMatrix, mPaint);
```
#### 二、箭头旋转动画
##### 示例如下图：
![image](https://github.com/tianyalu/NeCanvasPathMeasure/blob/master/show/show.gif)

