package com.lamp.decoration.foundation.network.redis.commands;


import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.utils.DataConversionUtils;


public class ScriptCommandsImpl<T> extends AbstractLedis<T> implements ScriptCommands,ScriptCommandsElement{
	


	public ScriptCommandsImpl(KeyCreate<T> keyCreate, String dataSource) {
		super(keyCreate, dataSource);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Boolean scriptExists(String script) {
		return combination(SCRIPT_EXISTS, DataConversionUtils.getDataConversionList("exists",script));
	}
	

	@Override
	public String scriptLoad(String script) {
		return combination(SCRIPT_LOAD, DataConversionUtils.getDataConversionList("load",script));
	}

	@Override
	public String evalsha(String script) {
		return combination(EVALSHA , DataConversionUtils.getDataConversionList(script,0));
	}



}
