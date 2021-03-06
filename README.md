# Learn English 重写升级版

使用的技术如下：
- Spring Boot
- Thymeleaf
- MySQL
- MyBatis / Mybatis Generator / Mybatis PageHelper
- Shiro
- Dozer
- Hibernate Validator


注意：
1. 返回 JSON 类型结果时使用统一结果类`ResultBean`
2. 系统处理出错时，抛出统一的异常`ProcessException`,由统一异常处理器进行处理
3. 需要新增表的Mapper等时，使用`MyBatis-Generator`插件生成对应表的 Mapper 等
4. Mapper 接口需要添加 @Mapper 注解
5. 数据库操作时不要忘记使用事物注解
6. 分页查询时使用 MyBatis 的分页查询插件
7. Bean 间复制使用 dozer
8. 使用 Hibernate Validator 校验bean属性


修改表字段后，需要修改
1. mapper 文件
2. 对应的实体类文件


问题：
1. service如果没有接口使用下会报错