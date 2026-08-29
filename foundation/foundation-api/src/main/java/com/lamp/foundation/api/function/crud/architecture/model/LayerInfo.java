package com.lamp.foundation.api.function.crud.architecture.model;


import lombok.Data;

/**
 * <pre>
 *     层 核心
 *
 *     输入 无方法，
 *     输出（是否带 返回对象） 无方法
 *     转换类 ， 固定模式
 *     是否有接口
 *
 *     目录，后罪名，
 *
 *     方法 模版
 *     类 模板
 *
 * </pre>
 */
@Data
public class LayerInfo {

    private String name;

    private Object interfaceInfo;

    private Object classInfo;

    /**
     * 没有方法
     */
    private Object inputParameter;

    /**
     * 没有方法
     */
    private Object outputParameter;

    private Object upLayerInfo;

    private Object downLayerInfo;


    @Data
    public static class LayerNode {

        private String name;

        private String path;

        private String fileName;

        private String classTemplate;

        private LayerNode children;

        private Object MethodInfo;


    }

}
