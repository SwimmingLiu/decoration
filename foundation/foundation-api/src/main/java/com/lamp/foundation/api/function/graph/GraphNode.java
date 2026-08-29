package com.lamp.foundation.api.function.graph;

import java.util.Set;

import lombok.Data;

/**
 * <pre>
 *   类之间的 关联
 *   那 字段之间的关联了
 *   那 方法 调用关联
 * </pre>
 */
@Data
public class GraphNode<T> {

    private Set<GraphNode<T>> importers;

    private Set<GraphNode<T>> importedModules;

}
