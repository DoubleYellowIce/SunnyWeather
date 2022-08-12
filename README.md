### 演示图

<p align="center"><img src="https://user-images.githubusercontent.com/65336599/139616040-bba91aab-4060-4eec-a887-a47d40ac56fa.gif" width="280" height="550"/><img src="https://user-images.githubusercontent.com/65336599/139671076-e6d0d377-f86e-4354-8842-2856cc93b488.gif" width="280" height="550"/></p>

### 为什么你要Star这个项目

尽管SunnyWeather本身是一款简单的安卓天气应用，但麻雀虽小，五脏俱全。

- 架构清晰。

  SunnyWeather遵循MVVM架构+Repository架构来进行编码。

  <p align="center"><img width="418" alt="mvvm" src="https://user-images.githubusercontent.com/65336599/139617319-203e65f4-ec94-454a-8fb9-605937a1445d.png"></p>

- 模块划分。

  尽管SunnyWeather本身不大，但仍将其划分为了两个module，一个为app主module，该module主要来存放应用的核心代码，如Activity，ViewModel，另一个是app_data子module，如其名所示，该module主要存放model类以及获取网络数据的相关类。

  在企业开发中，往往都是多module进行开发的，而不是将全部代码都放至一个app主module里，这么做的好处是module可以在不同应用中共享，但这同时也会带来一定的不便，比如说子类module无法获取到主module的类，再比如说如何统一管理多个module的第三方库依赖，SunnyWeather作为一个小应用，用来提前熟悉一下企业的开发模式是再恰当不过。

- 主流第三方库的使用

  - Retrofit

    Retrofit作为目前安卓主流的网络获取库，其重要性不言而喻，SunnyWeather可以很好演示如何使用Retrofit从后台获取数据。

  - Dagger

    Dagger是目前主流的依赖注入库，但Dagger本身就有匕首的意思，匕首可御敌，使用不当也会伤到自己，Dagger依赖注入库也是这个理，使用恰当可以减少很多初始化的代码，但其使用难度不小，此外使用不当也会使编码得更加复杂，SunnyWeather就很好演示了如何使用Dagger进行依赖注入，比如说

    - 如何向Application，Activity这种Dagger无法直接初始化的类进行依赖注入。
    - 如何搭配Retrofit网络库来获取网络数据更加优雅～

- 良好的编码风格

  在如今的开发过程中，并不是说`人和项目只要有一个能跑就行`
  ，而是需要项目具有一定的可维护性，而良好的代码风格正是可维护性基本要求之一，下面代码截取至[MainActivity.kotlin](./app/src/main/java/com/sunnyweather/main/MainActivity.kt)
  文件。

  ```kotlin
      private fun init() {
  				...
          initLocationFunctions()
          if (permissionForLocationIsGranted()) {
              startLocateUser()
          } else {
              val requestCallback = RequestCallback { allGranted, _, _ ->
                  if (allGranted) {
                      startLocateUser()
                  } else {
                      doNothing()
                  }
              }
              requestLocationPermission(requestCallback)
          }
          getCurrentLocationCombineWeatherInfo()
      }
  ```

  init()方法负责执行初始化工作，即使你不知道这个应用的基本信息，从代码里大致也能推断出其内容。

  1. 先初始化定位功能。
  2. 判断是否有定位权限。
    1. 有。直接定位用户。
    2. 无。去请求定位权限，当用户同意时再进行定位。
  3. 根据定位获取天气功能。

  从这上面三点就可以进一步推出这个应用至少有获取用户所在城市的天气信息的功能，这样的代码在项目还有很多处，这里不一一列举了。

### 其它信息

- 数据来源

  天气数据来源于[心知天气](https://www.seniverse.com/)，由于采用的是该网站的免费API，有如下限制。

  - 每分钟只能进行20次的数据刷新，因此在实际使用中可能会出现无法刷新数据的情况。
  - 只能获取未来3天的天气预报(包括今天)。

- 地图定位

  - 植入了百度地图SDK来实现定位功能。

* 使用的第三方开源库

  比较常见的开源库，比如说Retrofit，Dagger，就不再赘述，这里列举是不常见的第三方开源库。

  * 使用[PermissionX](https://github.com/guolindev/PermissionX)来获取定位权限。
  * 使用地址选择器库[AndroidPicker](https://github.com/gzu-liyujiang/AndroidPicker)。

### 参考

1. [《第一行代码》](https://item.jd.com/10026226142664.html)

