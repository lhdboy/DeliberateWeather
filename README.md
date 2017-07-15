# 走心天气

使用高德地图web服务api开发的安卓天气APP

若要把项目跑起来，先去[高德开发平台](http://lbs.amap.com)注册一个开发者账号

## 配置项目

### 配置获取天气信息接口的key

注册开发者账号后，去控制台创建一个应用，应用类型选择**web服务API**，拿到key，把key填到`DeliberateWeather/app/src/main/java/com/toosame/weather/utils/HttpClient.java`第29行那段用星号包括的字符里

具体参考：[获取Web服务API的Key](http://lbs.amap.com/api/webservice/guide/create-project/get-key)

### 配置定位SDK

再去控制台创建一个应用，在创建的应用上点击"添加新Key"按钮，在弹出的对话框中，依次：输入应用名名称，选择绑定的服务为“Android平台SDK”，输入发布版安全码  SHA1、调试版安全码 SHA1、以及 Package

具体参考：[获取定位SDK的Key还有配置获取SHA1值](http://lbs.amap.com/api/android-location-sdk/guide/create-project/get-key)

把拿到的Key放到`DeliberateWeather/app/src/main/AndroidManifest.xml`第45行那段用星号包括的字符里

Ok，配置完成
