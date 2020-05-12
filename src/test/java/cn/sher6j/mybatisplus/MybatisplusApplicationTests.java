package cn.sher6j.mybatisplus;

import cn.sher6j.mybatisplus.bean.User;
import cn.sher6j.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MybatisplusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询
     */
    @Test
    public void findAll() {
        //生成的SQL代码————逻辑删除的记录无法被查出：
        //SELECT id,name,age,email,create_time,update_time,version,deleted FROM user WHERE deleted=0
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 添加操作
     * 不需要设置id值（主键）
     * 默认MybatisPlus的主键生成策略，为19位id值，用的是snowflake算法
     */
    @Test
    public void insertUser() {
        User user = new User();
        user.setName("风清扬");
        user.setAge(55);
        user.setEmail("fqy@outlook.com");

        /**
         * 手动设置时间值
         */
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());

        /**
         * 不需要set方法设置对象值，使用MP方式实现数据添加————自动填充，见实体类注释
         */

        int insert = userMapper.insert(user);
        System.out.println("insert: " + insert);
    }

    /**
     * 测试修改
     */
    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1260172666553044994l);
        user.setAge(3);
        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    /**
     * 测试乐观锁修改，先查再改，常用
     */
    @Test
    public void updateOptimisticLocker() {
        //根据id查询数据
        User user = userMapper.selectById(1260178375432790018l);
        //进行修改
        user.setAge(30);
        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    /**
     * 多个id的批量查询，常用
     */
    @Test
    public void testSelectBatchIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1l, 2l, 1260172666553044994l));
        users.forEach(System.out::println);
    }

    /**
     * 根据HashMap的条件查询，基本用不到
     */
    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 30);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testPage() {
        //1.创建page对象
        //传入两个参数：当前页 和 每页显示记录数
        Page<User> page = new Page<>(1, 3);

        //2.调用map分页查询的方法
        //调用map分页查询时，底层会将分页所有数据封装到page对象里面
        userMapper.selectPage(page, null);

        //3.通过page对象获取分页数据
        System.out.println(page.getCurrent()); //当前页
        System.out.println(page.getRecords()); //每页数据的list集合
        System.out.println(page.getSize()); //每页记录数
        System.out.println(page.getTotal()); //总记录数
        System.out.println(page.getPages()); //总页数
        System.out.println(page.hasNext()); //是否有下一页
        System.out.println(page.hasPrevious()); //是否有上一页
    }

    /**
     * 删除操作  本来是物理删除 但是配置了逻辑删除后，变为逻辑删除
     */
    @Test
    public void testDeleteById() {
        //底层生成的SQL语句：
        //UPDATE user SET deleted=1 WHERE id=? AND deleted=0
        int row = userMapper.deleteById(1l);
        System.out.println(row);
    }

    /**
     * 批量删除  物理删除 但是配置了逻辑删除后，变为逻辑删除
     */
    @Test
    public void testDeleteBatchIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
        System.out.println(rows);
    }

    /**
     * MP实现复杂查询操作
     */
    @Test
    public void testSelectQuery() {
        //1.创建对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //2.通过QueryWrapper设置条件

        //2.1 ge、gt、le、lt 大于等于、大于、小于等于、小于
        //查询age>=30的记录
//        wrapper.ge("age", 30);

        //2.2 eq、ne 等于、不等于
        //查询名称等于"风清扬"的数据
//        wrapper.eq("name", "风清扬");
        //查询名称不等于"风清扬"的数据
//        wrapper.ne("name", "风清扬");

        //2.3 between 范围内
//        wrapper.between("age", 20, 30);

        //2.4 like 模糊查询
        //生成的SQL语句中：AND name LIKE '%j%'
//        wrapper.like("name", "j");

        //2.5 orderBy、orderByDesc、orderByAsc 排序
//        wrapper.orderByDesc("id");

        //2.6 last 拼接到SQL的最后
//        wrapper.last("limit 1"); //只查一条数据

        //2.7 指定要查询的列
        wrapper.select("id", "name");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
