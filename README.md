# Learn English 重写升级版

使用的技术如下：
- Spring Boot
- Thymeleaf
- MySQL
- MyBatis / Mybatis Generator / Mybatis PageHelper
- Shiro


注意：
1. 返回 JSON 类型结果时使用统一结果类`ResultBean`
2. 系统处理出错时，抛出统一的异常`ProcessException`,由统一异常处理器进行处理
3. 需要新增表的Mapper等时，使用`MyBatis-Generator`插件生成对应表的 Mapper 等
4. Mapper 接口需要添加 @Mapper 注解
5. 数据库操作时不要忘记使用事物注解
6. 分页查询时使用 MyBatis 的分页查询插件
