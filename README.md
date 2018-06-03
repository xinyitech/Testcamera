# Testcamera
## 自定义摄像头简单说明


该demo用于人脸检测和身份证检测拍摄的图片处理，人脸检测用android系统自带API。自定义摄像头核心功能，包括手势缩放、点击聚焦、摄像头前后切换以及基本拍照功能。外包括图片保持不失真情况下图片压缩到100k以下（相册选择功能有写）。


代码简单易懂，如果需不同覆盖物，只需自己写一套UI，底层自定义摄像头拿过去用就可以了。


<目前功能简单，后期可根据需求不断优化处理>


### 代码简单说明
#### 人脸拍摄
  跳转人脸拍摄界面
```java
  //MainActivity.this即当前跳转activity
  XinyiCameraSelector.create(MainActivity.this).intentFaceView(10);
  ....
  ..
  // 拍摄人脸图片返回(当前跳转的activity)
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            byte[] pic = data.getByteArrayExtra("facePic");
            if (pic != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                imageView.setImageBitmap(bitmap);
            }
        }
  ``` 
  ![image](https://github.com/xinyitech/Testcamera/blob/master/raw/face.png)
  
#### 身份证拍摄（两种方式，可根据自己想要选择一种）
（1） 直接跳转拍摄界面拍摄
```java
    //MainActivity.this即当前跳转activity
    //false:表示直接跳转拍身份证拍摄界面；true:表示跳转拍摄完提交界面，有提交操作
  XinyiCameraSelector.create(MainActivity.this).intentIdCardView(20, false);
  ....
  ..
  // 拍摄身份证图片返回(当前跳转的activity)
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            byte[] pic = data.getByteArrayExtra("idCardPic");
                if (pic != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                    imageView.setImageBitmap(bitmap);
                }
        }
  ``` 
  
  
 （2） 先跳转提示界面再跳转拍摄界面拍摄（有提交操作）
```java
  //MainActivity.this即当前跳转activity
  //true:表示跳转拍摄完提交界面，有提交操作
  XinyiCameraSelector.create(MainActivity.this).intentIdCardView(20, false);
  ....
  ..
  //拍摄身份证图片返回(当前跳转的activity)
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
                //提交按钮返回
                byte[] picOne = data.getByteArrayExtra("idCardPicOne");//身份证正面
                byte[] picTwo = data.getByteArrayExtra("idCardPicTwo");//身份证反面
                ....
                //根据自己需求处理图片，eg:上传服务器等
        }
  ``` 
  
  
  

#### 如果自己想根据需求进行自定义界面（该块后期有时间，可优化传入图片即实现自定义效果）

```java
        //初始化自定义camera（），fl_camera_Layout 即自定义摄像拍摄范围（FrameLayout）
        camera_fragment = new XYCameraOverlapFragment();
        float rate = ScreenUtils.getScreenWidth(this) * 1.0f / ScreenUtils.getScreenHeight(this);
        Bundle bundle = new Bundle();
        bundle.putInt("currentCamera", currentCamera);
        bundle.putFloat("rate", rate);
        camera_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_camera_Layout, camera_fragment).commit();
        ....
        ..
        
        //开始拍照
           if (camera_fragment.isPreviewing()) {
                    camera_fragment.tackPicture(this);
                }     
        .....
        ...
       //自定义摄像头拍照回调
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
       //data即拍摄返回照片字节数组
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //正面拍摄时候，需将图片旋转，否则该图片会出现倒着的现象（注：身份证拍摄时候不需要旋转）
        Bitmap rotateBitmap = PictureUtils.rotateBitmap(this, bitmap, currentCamera);
        checkedFace(rotateBitmap);//检测人脸，系统自带人脸检测
        iv_result.setImageBitmap(rotateBitmap);
    } 
    
     //用系统自带人脸检测
    private void checkedFace(Bitmap bitmap) {
        int faceCount = PictureUtils.findFace(bitmap);
        if (faceCount == 0) {
            Toast.makeText(this, "未检测到人脸,请选择正确的人脸照片", Toast.LENGTH_SHORT).show();
            return;
        } else if (faceCount > 1) {
            Toast.makeText(this, "检测到多个人脸,请选择单个人脸照片", Toast.LENGTH_SHORT).show();
            return;
        } else if (faceCount == 1) {
            //给据业务做相应处理-------
            Toast.makeText(this, "检测到一个人脸", Toast.LENGTH_SHORT).show();

        }
    }
    
  ```  

