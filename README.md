# Testcamera
## 自定义摄像头简单说明


该demo用于人脸检测和身份证检测拍摄的图片处理，人脸检测用android系统自带API。自定义摄像头核心功能，包括手势缩放、点击聚焦、摄像头前后切换以及基本拍照功能。外包括图片保持不失真情况下图片压缩到100k以下（相册选择功能有写）。
代码简单易懂，如果需不同覆盖物，只需自己写一套UI，底层自定义摄像头拿过去用就可以了。


### 代码简单说明
#### 人脸拍摄
  跳转人脸拍摄界面
```java
  //MainActivity.this即当前跳转activity
  XinyiCameraSelector.create(MainActivity.this).intentFaceView(10);
  ```  

  拍摄人脸图片返回(当前跳转的activity)
```java
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
#### 身份证拍摄（两种方式，可根据自己想要选择一种）
（1） 直接跳转拍摄界面拍摄
```java
    //MainActivity.this即当前跳转activity
    //false:表示直接跳转拍身份证拍摄界面；true:表示跳转拍摄完提交界面，有提交操作
  XinyiCameraSelector.create(MainActivity.this).intentIdCardView(20, false);
  ```  

  拍摄人脸图片返回(当前跳转的activity)
```java
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
  ```  

  拍摄身份证图片返回(当前跳转的activity)
```java
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
                //提交按钮返回
                byte[] picOne = data.getByteArrayExtra("idCardPicOne");//身份证正面
                byte[] picTwo = data.getByteArrayExtra("idCardPicTwo");//身份证反面
        }
  ``` 
  



