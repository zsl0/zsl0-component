package com.zsl.custombox.authentication.mapper;

import com.zsl.custombox.authentication.model.pojo.login.Menu;

import java.util.List;

/**
 * 菜单Mapper
 *
 * @Author zsl
 * @Date 2022/6/9 14:45
 * @Email 249269610@qq.com
 */
public interface MenuMapper {
    /**
     * 查询全部menu
     */
    List<Menu> queryAll();

    /**
     * 根据id查询
     */
    Menu queryById(Long id);

    /**
     * 通过roleId查询菜单
     */
    List<Menu> queryByRoleId(Long roleId);

    /**
     * 通过List<Long>查询菜单 (需要去重)
     */
    List<Menu> queryByRoleIdes(List<Long> roleIdes);

    /**
     * 通过List<Long>查询菜单 (需要去重:未实现)
     */
    List<Menu> queryByUserId(Long userId);

    /**
     * 新增
     */
    void insert(Menu menu);

    /**
     * 批量插入
     */
    void insertBatch(List<Menu> menus);

    /**
     * 修改
     */
    void update(Menu menu);

    /**
     * 删除
     */
    void deleteById(Long id);
}
