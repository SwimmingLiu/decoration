package com.lamp.decoration.foundation.network.redis.pipi;

public interface PipiRedisCommands<K,T>
	extends PipiHashCommands<K, T>, PipiSortedSetCommands<T>, PipiListCommands<T>, PipiSetCommands {

}
