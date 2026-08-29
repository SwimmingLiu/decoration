package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;
import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;
import com.lamp.decoration.foundation.network.redis.protocol.ResultHandle;

public interface SortedSetCommandsElement {

    CombinationElement ZADD = CombinationElement.create().setComman("zadd").setLength(4).buildBoolean();

    CombinationElement ZADD_MORE = CombinationElement.create().setComman("zadd").setExecutioMode(EecutionMode.MAP_MSET).build();

    CombinationElement ZCARD = CombinationElement.create().setComman("zcard").setLength(2).build();

    CombinationElement ZCOUNT = CombinationElement.create().setComman("zcount").setLength(4).build();

    CombinationElement ZINCRBY = CombinationElement.create().setComman("zincrby").setLength(4).build();

    CombinationElement ZRANGE =
        CombinationElement.create().setComman("zrange").setLength(5).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();

    CombinationElement ZRANGEBYSCORE =
        CombinationElement.create().setComman("zrangebyscore").setLength(5).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();

    CombinationElement ZRANK = CombinationElement.create().setComman("zrank").setLength(3).build();

    CombinationElement ZREN = CombinationElement.create().setComman("zrem").setLength(3).build();

    CombinationElement ZREN_MORE = CombinationElement.create().setComman("zrem").setExecutioMode(EecutionMode.STRING_MGET).build();

    CombinationElement ZREMRANGEBYRANK = CombinationElement.create().setComman("zremrangebyrank").setLength(4).build();

    CombinationElement ZREMRANGEBYSCORE = CombinationElement.create().setComman("zremrangebyscore").setLength(4).build();

    CombinationElement ZREVRANGE =
        CombinationElement.create().setComman("zrevrange").setLength(5).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();

    CombinationElement ZREVRANGEBYSCORE =
        CombinationElement.create().setComman("zrevrangebyscore").setLength(5).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();

    CombinationElement ZREVRANK = CombinationElement.create().setComman("zrevrank").setLength(3).build();

    CombinationElement ZSCORE =
        CombinationElement.create().setComman("zscore").setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).setResultHandle(
            ResultHandle.stringToLongHandle).setLength(3).build();

    CombinationElement ZUNIONSTORE =
        CombinationElement.create().setComman("zunionstore").setBoo(false).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();

    CombinationElement ZINTERSTORE =
        CombinationElement.create().setComman("zinterstore").setBoo(false).setResolveNetProtocol(ResolveNetProtocol.sortedSetNetProtocol).build();


}
