package com.lamp.decoration.foundation.network.http.cloud;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManage {

	private Map<String/* name or ak */, ThirdPartyConfig> configToName = new ConcurrentHashMap<>();

	private Map<String/* name or ak */, ThirdPartyConfig> configToBusiness = new ConcurrentHashMap<>();

	private Map<String, ThirdPartyConfig> configToManufacturer = new ConcurrentHashMap<>();

	private ThirdPartyConfig defaultConfig;

	public synchronized void setThirdPartyConfig(ThirdPartyConfig thirdPartyConfig) {
	}

	public synchronized void setDefault(ThirdPartyConfig thirdPartyConfig) {
		defaultConfig = thirdPartyConfig;
	}

	public synchronized void setManufacturer(ThirdPartyConfig thirdPartyConfig) {
	}

	public ThirdPartyConfig getConfig(ThirdPartyConfig thirdPartyConfig) {
		return null;
	}
}
