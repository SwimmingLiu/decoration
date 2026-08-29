/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.decoration.core.spring;

import java.util.List;

import com.lamp.decoration.core.spring.plugs.DecorationCorsConfiguration;

/**
 * 所有的插件配置写到这里
 *
 * @author laohu
 */
public class PlugsConfig {

    private boolean corsEnable = false;

    private List<DecorationCorsConfiguration> corsConfigurationList;


    public boolean isCorsEnable() {
        return corsEnable;
    }

    public void setCorsEnable(boolean corsEnable) {
        this.corsEnable = corsEnable;
    }

    public List<DecorationCorsConfiguration> getCorsConfigurationList() {
        return corsConfigurationList;
    }

    public void setCorsConfigurationList(List<DecorationCorsConfiguration> corsConfigurationList) {
        this.corsConfigurationList = corsConfigurationList;
    }

}
