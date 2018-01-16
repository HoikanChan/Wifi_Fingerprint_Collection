#指纹库采集App
******************************************
##工程文件说明
******************************************
java：

Acitivity：

1. DataOrigination：初始设置页面
2. WiFiAcquireActivity：采集指纹库页面
3. SelectApActivity：选择定位AP页面
   →5.DataMapActivity：wifi强度分布图页面
4. DataShowActivity：定位指纹库页面

Adapter:

MyGridAdapter:2中的GridView适配器

SelectListAdapter：3中的ListView适配器

MyListAdapter:4中的外层ListView适配器

MyListItemAdapter：4中的内层ListView适配器

DataMapAdapter：5中GridView适配器

Bean：

FingerprintBean、WifiFinPtBean：指纹数据封装类

GridBean、ListBean：GridView和ListView的数据封装类

util：工具类
Constant：数据库表、列名

CustomProgressDialog：加载动画弹出窗

DbManager：数据库SQL操作

MathManager:公式操作类

MySqlitehelper：SQLite初始设置类

WIFI:wifi信息的读取类

