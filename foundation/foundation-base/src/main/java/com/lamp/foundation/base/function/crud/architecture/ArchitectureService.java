package com.lamp.foundation.base.function.crud.architecture;

import java.util.HashMap;
import java.util.Map;

import com.lamp.foundation.base.function.crud.architecture.model.FieldLinkResult;
import com.lamp.foundation.base.function.crud.architecture.task.BaseMethodTask;
import com.lamp.foundation.base.function.crud.architecture.task.LinkTask;
import com.lamp.foundation.base.function.crud.architecture.task.MapperTask;
import com.lamp.foundation.base.function.crud.architecture.task.MethodTask;

public class ArchitectureService {

    private final Map<String, Object> layerMap = new HashMap<>();

    private final LinkTask linkTask = new LinkTask();

    private final MethodTask methodTask = new MethodTask();

    private final MapperTask mapperTask = new MapperTask();

    private final BaseMethodTask baseMethodTask = new BaseMethodTask();

    public void init() {

    }

    /**
     * 操作 entity 时
     */
    public void baseEntityLink(String fileName, Object entity) {
        Object object = layerMap.get(fileName);

        linkTask.build();
        if (fileName.endsWith("Mapper.java")) {
            baseMethodTask.build();
        }
    }

    public void mapperEntityLink(String fileName, Object entity) {
        Object object = layerMap.get(fileName);
        mapperTask.build();
        methodTask.build();
    }

    /**
     * 添加方法时
     */
    public void layerLink(String fileName, String methodName, String methodStatement) {
        methodTask.build();
    }


    /**
     * <pre>
     *     1. 自动模式
     *     2. 自动发现 关联点，并且提供修改信息，手动选择修改
     * </pre>
     * <pre>
     *     1. 所有关联的 O ， 通过 layer 链路找到
     *     2. Mapper， 通过 layer 找到
     *     3. 关联查询， 通过
     *     4. 关联的 E，通过 JoinColumn 与 JoinTable
     *          1.
     * </pre>
     */
    public FieldLinkResult fieldLink(String fileName, String methodName, String methodStatement) {
        return null;
    }



    public void build() {

    }

}
