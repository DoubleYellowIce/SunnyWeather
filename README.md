# 演示图
<img src="https://user-images.githubusercontent.com/65336599/139616040-bba91aab-4060-4eec-a887-a47d40ac56fa.gif" width="280" height="550"/>  <img src="https://user-images.githubusercontent.com/65336599/139671076-e6d0d377-f86e-4354-8842-2856cc93b488.gif" width="280" height="550"/></br></br>

# 简介
SunnyWeather是一款简单的安卓天气应用。</br></br>

**开发目的**</br>
开发该项目的目的主要有3个。
* 学习Kotlin语言。
* 学习如何使用Retrofit第三方库从服务器获取数据。
* 学习如何利用MVVM框架构建一个应用</br></br>


**语言**</br>
SunnyWeather使用Kotlin和Java混合开发,语言占比如下图所示。</br>
![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=DoubleYellowIce&exclude_repo=MagaCommunity)</br></br>
**架构**</br>
该应用采用了MVVM架构。</br>
<img width="418" alt="mvvm" src="https://user-images.githubusercontent.com/65336599/139617319-203e65f4-ec94-454a-8fb9-605937a1445d.png"></br></br>
**数据来源**</br>
天气数据来源于[心知天气](https://www.seniverse.com/)，由于采用的是该网站的免费API，有如下限制。</br>
* 每分钟只能进行20次的数据刷新，因此在实际使用中可能会出现无法刷新数据的情况。</br>
* 只能获取未来3天的天气预报(包括今天)。</br></br>


**地图定位**</br>
* 定位功能由百度地图API提供。</br></br>

**使用的第三方开源库**</br>
比较常见的开源库，比如说Retrofit，就不再赘述，这里列举是不常见的第三方开源库。
* [PermissionX](https://github.com/guolindev/PermissionX)
* [AndroidPicker](https://github.com/gzu-liyujiang/AndroidPicker)</br></br>

**进度**</br>
目前该项目已经完成了七七八八，尽管后面可能还会添加一些小功能，比如说天气推送，但不会再大幅度改动。

# 参考
本项目主要参考了郭神的[《第一行代码》第三版](https://item.jd.com/10026226142664.html)中第十五章里面的内容，但由于该书发布时间距离现在已有几年时间，里面有些东西已不再适用，相比书中项目，该项目做了如下变动。</br>
* 由于无法申请到彩云天气的API，该项目采用的是心知天气的API。
* 同样地，由于换了API，该项目没有采用书中项目的搜索城市功能，而是采用了地址选择器。
* 该项目添加了定位功能。
