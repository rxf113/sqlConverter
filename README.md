### 对 druid sql parser 的实践

自定义对象或者数据格式，达到随心所欲的操作sql( **比如需要细粒度的权限控制的多租户系统** ，不同的角色、不同的权限，查询的同一张表的不同字段，或者不同条件)。如果每次都手动修改字符串很麻烦。

基于 druid sql parser 写的一个sql转换器，按照通用的对象格式，完成对sql的转换。

实现 原始sql + 控制对象 动态转换为满足条件的sql

项目运行test  

