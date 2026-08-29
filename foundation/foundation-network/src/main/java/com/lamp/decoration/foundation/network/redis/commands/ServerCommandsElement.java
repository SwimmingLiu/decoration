package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.protocol.ResolveNetProtocol;
import com.lamp.decoration.foundation.network.redis.protocol.ResultHandle;

public interface ServerCommandsElement {
	/**
	 * 执行一个 AOF文件 重写操作。重写会创建一个当前 AOF 文件的体积优化版本
	 */
	static final CombinationElement BGREWRITEAOF  = CombinationElement.create( ).setComman( "bgrewriteaof" ).setLength(1).setResolveNetProtocol( ResolveNetProtocol.resolveStringNetProtocol ).build( );

	/**
	 * 在后台异步(Asynchronously)保存当前数据库的数据到磁盘
	 */
	static final CombinationElement BGSAVE = CombinationElement.create().setComman("bgsave").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).build();

	/**
	 * 返回 CLIENT SETNAME 命令为连接设置的名字
	 */
	static final CombinationElement CLIENT_GETNAME = CombinationElement.create().setComman("client").setLength(2).setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).build();

	/**
	 * 关闭地址为 ip:port 的客户端
	 */
	static final CombinationElement CLIENT_KILL = CombinationElement.create().setComman("client").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 为当前连接分配一个名字
	 */
	static final CombinationElement CLIENT_SETNAME = CombinationElement.create().setComman("client").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 取得运行中的 Redis 服务器的配置参数
	 */
	static final CombinationElement CONFIG_GET = CombinationElement.create().setComman("config").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveManyToListStringNetProtocol).setResultHandle(
		ResultHandle.typeReferenceListString).build();

	/**
	 * 动态地调整 Redis 服务器的配置(configuration)而无须重启
	 */
	static final CombinationElement CONFIG_SET = CombinationElement.create().setComman("config").setLength(4).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 返回当前数据库的 key 的数量
	 */
	static final CombinationElement DBSIZE = CombinationElement.create().setComman("dbsize").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveIntNetProtocol).build();

	/**
	 * 清空整个 Redis 服务器的数据(删除所有数据库的所有 key )
	 */
	static final CombinationElement FLUSHALL = CombinationElement.create().setComman("flushall").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 清空整个 Redis 服务器的数据(删除所有数据库的所有 key )
	 */
	static final CombinationElement FLUSHDB = CombinationElement.create().setComman("flushdb").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 清空整个 Redis 服务器的数据(删除所有数据库的所有 key )
	 */
	static final CombinationElement LASTSAVE = CombinationElement.create().setComman("lastsave").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveIntNetProtocol).build();

	/**
	 * 实时打印出 Redis 服务器接收到的命令，调试用
	 */
	static final CombinationElement MONITOR = CombinationElement.create().setComman("monitor").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 用于复制功能(replication)的内部命令
	 */
	static final CombinationElement PSYNC = CombinationElement.create().setComman("psync").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * SAVE 命令执行一个同步保存操作，将当前 Redis 实例的所有数据快照(snapshot)以 RDB 文件的形式保存到硬盘
	 */
	static final CombinationElement SAVE = CombinationElement.create().setComman("save").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();


	/**
	 * 停止所有客户端;如果有至少一个保存点在等待，执行 SAVE 命令;如果 AOF 选项被打开，更新 AOF 文件;关闭 redis 服务器(server)
	 */
	static final CombinationElement SHUTDOWN = CombinationElement.create().setComman("shutdown").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveStringNetProtocol).build();


	/**
	 * 在 Redis 运行时动态地修改复制(replication)功能的行为
	 */
	static final CombinationElement SLAVEOF = CombinationElement.create().setComman("slaveof").setLength(3).setResolveNetProtocol(ResolveNetProtocol.resolveStateNetProtocol).build();

	/**
	 * 返回当前服务器时间
	 */
	static final CombinationElement TIME = CombinationElement.create().setComman("time").setLength(1).setResolveNetProtocol(ResolveNetProtocol.resolveManyToListStringNetProtocol).build();


}
