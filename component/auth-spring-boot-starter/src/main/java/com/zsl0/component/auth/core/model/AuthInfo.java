package com.zsl0.component.auth.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zsl0
 * created on 2022/12/12 21:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthInfo {
    private String uuid;
    private List<String> permissions;
}
