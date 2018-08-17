
service/report
=============

report 报表服务-测试

## Build

```
docker build -t service/breed:<version> .
```

## Versions

- `1.1.0`
## 在src的util下面的是使用的导出工具 在的service/imp 下面是组装数据根据业务需求来组装数据
* 数据库的表的格式其实对数据组装有影响，数据结构设计的好对导出很方便 其实导出都是从行开始的吧不能一列一列的导出（好像word没这么回事）
