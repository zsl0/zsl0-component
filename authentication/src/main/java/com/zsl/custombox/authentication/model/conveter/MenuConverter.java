package com.zsl.custombox.authentication.model.conveter;

import com.zsl.custombox.authentication.model.pojo.login.Menu;
import com.zsl.custombox.authentication.model.pojo.login.MenuNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author zsl
 * @Date 2022/6/10 16:09
 * @Email 249269610@qq.com
 */
@Mapper
public interface MenuConverter {
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    @Mappings({@Mapping(source = "menuId", target = "menuId"),
            @Mapping(source = "menuName", target = "menuName")})
    MenuNode menu2MenuNode(Menu menu);

    List<MenuNode> menus2MenuNodes(List<Menu> menus);
}
