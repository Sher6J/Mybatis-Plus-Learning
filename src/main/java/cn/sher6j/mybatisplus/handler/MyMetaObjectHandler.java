package cn.sher6j.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sher6j
 * @create 2020-05-12-19:33
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 使用MP实现添加操作，执行该方法
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setFieldValByName("createTime", date, metaObject);
        this.setFieldValByName("updateTime", date, metaObject);
        this.setFieldValByName("version", 1, metaObject);
    }

    /**
     * 使用MP实现更新操作，执行该方法
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
