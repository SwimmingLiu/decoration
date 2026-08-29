package com.lamp.decoration.foundation.network.redis.commands;


import java.util.List;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;


public class ServerCommandsImpl<T>  extends AbstractLedis<T> implements ServerCommands ,ServerCommandsElement {

	public ServerCommandsImpl(KeyCreate<T> keyCreate, String dataSource) {
		super(keyCreate, dataSource);
	}

	@Override
	public String bgrewriteaof() {
		return combination(BGREWRITEAOF, DataConversionUtils.getDataConversionList());
	}

	@Override
	public String bgsave() {
		return combination(BGSAVE, DataConversionUtils.getDataConversionList());
	}

	@Override
	public String clientGetName() {
		List<DataConversion> dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate("getname");
		return combination(CLIENT_GETNAME, dataList);
	}
	
	@Override
	public boolean clientKill(String name){
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate("kill");
		dataList.get(1).setObjectAndKeyCreate(name);
		return combination(CLIENT_KILL, dataList);
	}

	@Override
	public boolean clientSetName(String name) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate("setname");
		dataList.get(1).setObjectAndKeyCreate(name);
		return combination(CLIENT_SETNAME, dataList);
	}

	@Override
	public List<String> configGet(String name) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate("get");
		dataList.get(1).setObjectAndKeyCreate(name);
		return combination(CONFIG_GET,dataList);
	}

	@Override
	public boolean configSet(String name, String config) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get( 0 ).setObjectAndKeyCreate("set");
		dataList.get(1).setObjectAndKeyCreate(name);
		dataList.get(2).setObjectAndKeyCreate(config);
		return combination(CONFIG_SET, dataList);
	}

	@Override
	public long dbsize() {
		return combination(DBSIZE, DataConversionUtils.getDataConversionList());
	}

	@Override
	public boolean flushall() {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		return combination(FLUSHALL, dataList);
	}

	@Override
	public boolean flushdb() {
		return combination(FLUSHDB, DataConversionUtils.getDataConversionList());
	}

	@Override
	public long lastSave() {
		return combination(LASTSAVE, DataConversionUtils.getDataConversionList());
	}

	@Override
	public boolean monitor() {
		return combination(MONITOR, DataConversionUtils.getDataConversionList());
	}

	@Override
	public String psync(String pattern, String param) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get(0).setObjectAndKeyCreate(pattern);
		dataList.get(1).setObjectAndKeyCreate(param);
		return combination(PSYNC, dataList);
	}

	@Override
	public boolean save() {
		return combination(SAVE,DataConversionUtils.getDataConversionList());
	}

	@Override
	public String shutdown() {
		return combination(SHUTDOWN,DataConversionUtils.getDataConversionList());
	}

	@Override
	public boolean slaveof(String host, String post) {
		List< DataConversion > dataList = DataConversion.getListDataConversion( );
		dataList.get(0).setObjectAndKeyCreate(host);
		dataList.get(1).setObjectAndKeyCreate(post);
		return combination(SLAVEOF,dataList);
	}

	@Override
	public List<String> time() {
		return combination(TIME, DataConversionUtils.getDataConversionList());
	}
	

}
