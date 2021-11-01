# 演示图
<img src="https://user-images.githubusercontent.com/65336599/139616040-bba91aab-4060-4eec-a887-a47d40ac56fa.gif" width="300" height="550"/></br></br>
# 简介
SunnyWeather是一款简单的安卓天气应用。</br></br>
**语言**</br>
SunnyWeather使用Kotlin和Java混合开发,语言占比如下图所示。</br>
![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=DoubleYellowIce&exclude_repo=MagaCommunity)</br></br>
**架构**</br>
该应用采用了MVVM架构。</br>
<img width="418" alt="mvvm" src="https://user-images.githubusercontent.com/65336599/139617319-203e65f4-ec94-454a-8fb9-605937a1445d.png"></br></br>
**数据来源**</br>
天气数据来源于[心知天气](https://www.seniverse.com/)，由于采用的是该网站的免费API，有如下不足之处。</br>
* 每分钟只能进行20次的数据刷新，因此在实际使用中可能会出现无法刷新数据的情况。</br>
* 只能获取未来3天的天气预报(包括今天)。</br></br>
**地图定位**</br>
* 定位功能由百度地图API提供
