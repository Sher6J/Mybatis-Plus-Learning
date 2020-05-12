package cn.sher6j.mybatisplus.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author sher6j
 * @create 2020-05-12-18:14
 */
@Data //生成get/set方法和有参无参构造
public class User {
    /**
     * IdType.AUTO：自动增长
     * 下面两种自带策略不需要设置注解
     * IdType.ID_WORKER：MP自带策略，会生成19位值，数字类型使用这种策略，比如long
     * IdType.ID_WORKER_STR：MP自带策略，会生成19位值，字符串类型使用这种策略，比如string
     * IdType.INPUT：MybatisPlus不会自动生成id，需要自己手动输入
     * IdType.NONE：没有策略，也是自己手动输入，一般不用，用INPUT即可
     * IdType.UUID：随机唯一值，Universally Unique Identifier，通用唯一识别码
     */
//    @TableId(type = IdType.AUTO) //设置需要的主键生成策略
    private Long id;
    private String name;
    private Integer age;
    private String email;

    /**
     * 下面两个字段实现MP的自动填充：
     *     1. 在实体类里面为自动填充属性添加注解
     *     2. 创建类实现接口MetaObjectHandler，并实现接口中的方法
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT) //让版本默认为1
    private Integer version;

    @TableLogic //逻辑删除注解
    private Integer deleted;
}
