package com.lamp.decoration.foundation.network.redis.pipi;

import java.util.List ;
import java.util.Map ;

import com.lamp.decoration.foundation.network.redis.commands.RedisCommands;
import com.lamp.decoration.foundation.network.redis.entity.AsynResult;
import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;
import com.lamp.decoration.foundation.network.redis.entity.SortedSetParameter;
import com.lamp.decoration.foundation.network.redis.entity.ZunionstoreParameter;


/**
 * 重新在 new一个 redisCommands对象
 * @author muqi
 */
public class PipiRedisCommandsImpl< K , T > implements PipiRedisCommands<K, T>{

	private RedisCommands< K , T > redisCommands;
	
	public PipiRedisCommandsImpl(RedisCommands< K , T > redisCommands) {
		this.redisCommands = redisCommands;
	}
	
	@SuppressWarnings( { "unchecked", "hiding" } )
	private  <T>T  getAsynResult(Object object){
		return (T)object;
	}
	
	public AsynResult<BlockListResult<T>> blpop ( T key ) {
		return getAsynResult( redisCommands.blpop( key )) ;
	}

	
	public AsynResult<BlockListResult< T >> blpop ( long key ) {
		return getAsynResult(redisCommands.blpop(key)) ;
	}

	
	public AsynResult<BlockListResult< T >> blpop ( String key ) {
		return getAsynResult(redisCommands.blpop(key));
	}

	
	public AsynResult<BlockListResult< T >> blpop ( T key , int timeout ) {
		return getAsynResult(redisCommands.blpop(key)) ;
	}

	
	public AsynResult<BlockListResult< T >> blpop ( long key , int timeout ) {
		return getAsynResult(redisCommands.blpop(key,timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> blpop ( String key , int timeout ) {
		return getAsynResult(redisCommands.blpop(key,timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpop ( T key ) {
		return getAsynResult(redisCommands.brpop(key)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpop ( long key ) {
		return getAsynResult(redisCommands.brpop(key)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpop ( String key ) {
		return getAsynResult(redisCommands.brpop(key)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpop ( T key , int timeout ) {
		return getAsynResult(redisCommands.brpop(key, timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpop ( long key , int timeout ) {
		return getAsynResult(redisCommands.blpop(key,timeout));
	}

	
	public AsynResult<BlockListResult< T >> brpop ( String key , int timeout ) {
		return getAsynResult(redisCommands.brpop(key,timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( T source , T destination ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( long source , long destination ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( String source , String destination ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( T source , T destination , int timeout ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination,timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( long source , long destination , int timeout ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination,timeout)) ;
	}

	
	public AsynResult<BlockListResult< T >> brpoplpush ( String source , String destination , int timeout ) {
		return getAsynResult(redisCommands.brpoplpush(source, destination,timeout)) ;
	}

	
	public AsynResult<T> lindex ( T key , int index ) {
		return getAsynResult(redisCommands.lindex(key, index)) ;
	}

	
	public AsynResult<T> lindex ( long key , int index ) {
		return getAsynResult(redisCommands.lindex(key, index)) ;
	}

	
	public AsynResult<T> lindex ( String key , int index ) {
		return getAsynResult(redisCommands.lindex(key, index)) ;
	}

	
	public AsynResult<Long> linsert ( String key , String pivot , String value ) {
		return getAsynResult(redisCommands.linsert(key, pivot, value)) ;
	}

	
	public AsynResult<Long> llen ( T key ) {
		return getAsynResult(redisCommands.llen(key)) ;
	}

	
	public AsynResult<Long> llen ( String key ) {
		return getAsynResult(redisCommands.llen(key)) ;
	}

	
	public AsynResult<Long> llen ( long key ) {
		return getAsynResult(redisCommands.llen(key)) ;
	}

	
	public AsynResult<T> lpop ( T key ) {
		return getAsynResult(redisCommands.lpop(key)) ;
	}

	
	public AsynResult<T> lpop ( String key ) {
		return getAsynResult(redisCommands.lpop(key)) ;
	}

	
	public AsynResult<T> lpop ( long key ) {
		return getAsynResult(redisCommands.lpop(key)) ;
	}

	
	public AsynResult<Long> lpush ( T keyValue ) {
		return getAsynResult(redisCommands.lpush(keyValue)) ;
	}

	
	public AsynResult<Long> lpush ( List< T > keyValue ) {
		return getAsynResult(redisCommands.lpush(keyValue)) ;
	}

	
	public AsynResult<Long> lpushx ( T keyValue ) {
		return getAsynResult(redisCommands.lpushx(keyValue)) ;
	}

	
	public AsynResult<List<T>> lrange ( T key , int start , int end ) {
		return getAsynResult(redisCommands.lrange(key, start, end)) ;
	}

	
	public AsynResult<List<T>> lrange ( String key , int start , int end ) {
		return getAsynResult(redisCommands.lrange(key, start, end)) ;
	}

	
	public AsynResult<List<T>> lrange ( long key , int start , int end ) {
		return getAsynResult(redisCommands.lrange(key, start, end)) ;
	}

	
	public AsynResult<Long> lren ( String key , int count , String value ) {
		return getAsynResult(redisCommands.lren(key, count, value)) ;
	}

	
	public AsynResult<Boolean> lset ( T key , int index ) {
		return getAsynResult(redisCommands.lset(key, index)) ;
	}

	
	public AsynResult<Boolean> ltrim ( T key , int start , int end ) {
		return getAsynResult(redisCommands.ltrim(key, start, end)) ;
	}

	
	public AsynResult<Boolean> ltrim ( String key , int start , int end ) {
		return getAsynResult(redisCommands.ltrim(key, start, end)) ;
	}

	
	public AsynResult<Boolean> ltrim ( long key , int start , int end ) {
		return getAsynResult(redisCommands.ltrim(key, start, end)) ;
	}

	
	public AsynResult<T> rpop ( T key ) {
		return getAsynResult(redisCommands.rpop(key)) ;
	}

	
	public AsynResult<T> rpop ( long key ) {
		return getAsynResult(redisCommands.rpop(key)) ;
	}

	
	public AsynResult<T> rpop ( String key ) {
		return getAsynResult(redisCommands.rpop(key)) ;
	}

	
	public AsynResult<T> rpoplpush ( T source , T destination ) {
		return getAsynResult(redisCommands.rpoplpush(source, destination)) ;
	}

	
	public AsynResult<T> rpoplpush ( long source , long destination ) {
		return getAsynResult(redisCommands.rpoplpush(source, destination)) ;
	}

	
	public AsynResult<T> rpoplpush ( String source , String destination ) {
		return getAsynResult(redisCommands.rpoplpush(source, destination)) ;
	}

	
	public AsynResult<Long> rpush ( T keyValue ) {
		return getAsynResult(redisCommands.rpush(keyValue)) ;
	}

	
	public AsynResult<Long> rpush ( List< T > keyValue ) {
		return getAsynResult(redisCommands.rpush(keyValue)) ;
	}
	
	public AsynResult<Long> rpushx ( T keyValue ) {
		return getAsynResult(redisCommands.rpushx(keyValue)) ;
	}

	
	public AsynResult<Boolean> del ( String key ) {
		return getAsynResult(redisCommands.del(key)) ;
	}

	
	public AsynResult<Boolean> exists ( String key ) {
		return getAsynResult(redisCommands.exists(key)) ;
	}

	
	public AsynResult<Boolean> expire ( String key , int seconds ) {
		return getAsynResult(redisCommands.expire(key, seconds)) ;
	}

	
	public AsynResult<Boolean> pexpire ( String key , int milliseconds ) {
		return getAsynResult(redisCommands.pexpire(key, milliseconds)) ;
	}

	
	public AsynResult<Boolean> expireat ( String key , long timestamp ) {
		return getAsynResult(redisCommands.expireat(key, timestamp)) ;
	}

	
	public AsynResult<Boolean> pexpireat ( String key , long milliseconds ) {
		return getAsynResult(redisCommands.pexpireat(key, milliseconds)) ;
	}

	
	public AsynResult<Boolean> persist ( String key ) {
		return getAsynResult(redisCommands.persist(key)) ;
	}

	
	public AsynResult<Boolean> rename ( String key , String newkey ) {
		return getAsynResult(redisCommands.rename(key, newkey)) ;
	}

	
	public AsynResult<Boolean> renamenx ( String key , String newkey ) {
		return getAsynResult(redisCommands.renamenx(key, newkey)) ;
	}

	
	public AsynResult<Boolean> hdel ( T key ) {
		return getAsynResult(redisCommands.hdel(key)) ;
	}

	
	public AsynResult<Boolean> hdel ( String key , K field ) {
		return getAsynResult(redisCommands.hdel(key,field)) ;
	}

	
	public AsynResult<Boolean> hdel ( Long key , K field ) {
		return getAsynResult(redisCommands.hdel(key,field)) ;
	}

	
	public AsynResult<Boolean> hdel ( List< T > key ) {
		return getAsynResult(redisCommands.hdel(key)) ;
	}

	
	public AsynResult<Boolean> hexists ( T key ) {
		return getAsynResult(redisCommands.hexists(key)) ;
	}

	
	public AsynResult<Boolean> hexists ( String key , String field ) {
		return getAsynResult(redisCommands.hexists(key,field)) ;
	}

	
	public AsynResult<Boolean> hexists ( long key , long field ) {
		return getAsynResult(redisCommands.hexists(key,field)) ;
	}

	
	public AsynResult<T> hget ( T key ) {
		return getAsynResult(redisCommands.hget(key)) ;
	}

	
	public AsynResult<T> hget ( String key , String field ) {
		return getAsynResult(redisCommands.hget(key,field)) ;
	}

	
	public AsynResult<T> hget ( long key , long field ) {
		return getAsynResult(redisCommands.hget(key,field)) ;
	}

	
	public AsynResult<Map<K,T>> hgetall ( T key ) {
		return getAsynResult(redisCommands.hgetall(key)) ;
	}

	
	public AsynResult<Map<K,T>> hgetall ( String key ) {
		return getAsynResult(redisCommands.hgetall(key)) ;
	}

	
	public AsynResult<Map<K,T>> hgetall ( long key ) {
		return getAsynResult(redisCommands.hgetall(key)) ;
	}

	
	public AsynResult<Long> hincrby ( T key , long increment ) {
		return getAsynResult(redisCommands.hincrby(key, increment)) ;
	}

	
	public AsynResult<Long> hincrby ( String key , String field , long increment ) {
		return getAsynResult(redisCommands.hincrby(key, field, increment)) ;
	}

	
	public AsynResult<Long> hincrby ( Long key , Long field , long increment ) {
		return getAsynResult(redisCommands.hincrby(key, field, increment)) ;
	}

	
	public AsynResult<List<K>> hkeys ( T key ) {
		return getAsynResult(redisCommands.hkeys(key)) ;
	}

	
	public AsynResult<List<K>> hkeys ( String key ) {
		return getAsynResult(redisCommands.hkeys(key)) ;
	}

	
	public AsynResult<List<K>> hkeys ( long key ) {
		return getAsynResult(redisCommands.hkeys(key)) ;
	}

	
	public AsynResult<List<T>> hvals ( T key ) {
		return getAsynResult(redisCommands.hvals(key)) ;
	}

	
	public AsynResult<List<T>> hvals ( String key ) {
		return getAsynResult(redisCommands.hvals(key)) ;
	}

	
	public AsynResult<List<T>> hvals ( long key ) {
		return getAsynResult(redisCommands.hvals(key)) ;
	}

	
	public AsynResult<Long> hlen ( T key ) {
		return getAsynResult(redisCommands.hlen(key)) ;
	}

	
	public AsynResult<Long> hlen ( String key ) {
		return getAsynResult(redisCommands.hlen(key)) ;
	}

	
	public AsynResult<Long> hlen ( long key ) {
		return getAsynResult(redisCommands.hlen(key)) ;
	}

	
	public AsynResult<List<T>> hmget ( List< T > t ) {
		return getAsynResult(redisCommands.hmget(t)) ;
	}

	
	public AsynResult<List<T>> hmget ( String key , List< T > t ) {
		return getAsynResult(redisCommands.hmget(key,t)) ;
	}

	
	public AsynResult<List<T>> hmget ( Number key , List< T > t ) {
		return getAsynResult(redisCommands.hmget(key, t)) ;
	}

	
	public AsynResult<Boolean> hmset ( List< T > t ) {
		return getAsynResult(redisCommands.hmset(t)) ;
	}

	
	public AsynResult<Boolean> hmset ( String key , List< T > t ) {
		return getAsynResult(redisCommands.hmset(key, t)) ;
	}

	
	public AsynResult<Boolean> hmset ( Number key , List< T > t ) {
		return getAsynResult(redisCommands.hmset(key, t)) ;
	}

	
	public AsynResult<Boolean> hset ( T key ) {
		return getAsynResult(redisCommands.hset(key)) ;
	}

	
	public AsynResult<Boolean> hset ( String key , T t ) {
		return getAsynResult(redisCommands.hset(key,t));
	}

	
	public AsynResult<Boolean> hset ( Number key , T t ) {
		return getAsynResult(redisCommands.hset(key, t)) ;
	}

	
	public AsynResult<Boolean> hsetnx ( T key ) {
		return getAsynResult(redisCommands.hsetnx(key)) ;
	}

	
	public AsynResult<Boolean> hsetnx ( String key , T t ) {
		return getAsynResult(redisCommands.hsetnx(key, t)) ;
	}

	
	public AsynResult<Boolean> hsetnx ( Number key , T t ) {
		return getAsynResult(redisCommands.hsetnx(key,t)) ;
	}

	
	public AsynResult<T> get ( T key ) {
		return getAsynResult(redisCommands.get(key)) ;
	}

	
	public AsynResult<T> get ( String key ) {
		return getAsynResult(redisCommands.get(key)) ;
	}

	
	public AsynResult<T> get ( long key ) {
		return getAsynResult(redisCommands.get(key)) ;
	}

	
	public AsynResult<T> getset ( T key ) {
		return getAsynResult(redisCommands.getset(key)) ;
	}

	
	public AsynResult<List<T>> mget ( List< T > keys ) {
		return getAsynResult(redisCommands.mget(keys)) ;
	}

	
	public AsynResult<List<T>> mgetstring ( List< String > keys ) {
		return getAsynResult(redisCommands.mgetstring(keys)) ;
	}

	
	public AsynResult<List<T>> mgetnumber ( List< ? extends Number > keys ) {
		return getAsynResult(redisCommands.mgetnumber(keys)) ;
	}

	
	public AsynResult<T> set ( T key ) {
		return getAsynResult(redisCommands.set(key)) ;
	}

	
	public Boolean setnx ( T key ) {
		return getAsynResult(redisCommands.setnx(key)) ;
	}

	
	public Boolean setnx ( T key , Object o ) {
		return getAsynResult(redisCommands.setnx(key)) ;
	}

	
	public Boolean setex ( T key , long seconds ) {
		return getAsynResult(redisCommands.setex(key,seconds)) ;
	}

	
	public Boolean setex ( T key , Object o , long seconds ) {
		return getAsynResult(redisCommands.setex(key,o,seconds)) ;
	}

	
	public Boolean psetex ( byte[] key , long milliseconds , byte[] value ) {
		return getAsynResult(redisCommands.psetex(key, milliseconds, value)) ;
	}

	
	public Boolean mset ( List< T > tuple ) {
		return getAsynResult(redisCommands.mset(tuple)) ;
	}

	
	public Boolean msetnx ( List< T > tuple ) {
		return getAsynResult(redisCommands.msetnx(tuple)) ;
	}

	
	public Long incr ( T key ) {
		return getAsynResult(redisCommands.incr(key)) ;
	}

	
	public Long incrby ( T key , long value ) {
		return getAsynResult(redisCommands.incrby(key, value)) ;
	}

	
	public Double incrby ( T key , double value ) {
		return getAsynResult(redisCommands.incrby(key, value)) ;
	}

	
	public Long decr ( T key ) {
		return getAsynResult(redisCommands.decr(key)) ;
	}

	
	public Long decrby ( T key , long value ) {
		return getAsynResult(redisCommands.decrby(key, value)) ;
	}

	
	public Long strlen ( T key ) {
		return getAsynResult(redisCommands.strlen(key)) ;
	}

	
	public AsynResult<Long> sadd ( String key , String member ) {
		return getAsynResult(redisCommands.sadd(key, member)) ;
	}

	
	public AsynResult<Long> sadd ( String key , List< String > member ) {
		return getAsynResult(redisCommands.sadd(key, member)) ;
	}

	
	public AsynResult<Long> scard ( String key ) {
		return getAsynResult(redisCommands.scard(key)) ;
	}

	
	public AsynResult<List<String>> sdiff ( List< String > key ) {
		return getAsynResult(redisCommands.sdiff(key)) ;
	}

	
	public AsynResult<Long> sdiffstore ( String destination , List< String > key ) {
		return getAsynResult(redisCommands.sdiffstore(destination, key)) ;
	}

	
	public AsynResult<List<String>> sinter ( List< String > key ) {
		return getAsynResult(redisCommands.sinter(key)) ;
	}

	
	public AsynResult<Long> sinterstore ( String destination , List< String > key ) {
		return getAsynResult(redisCommands.sinterstore(destination, key)) ;
	}

	
	public AsynResult<Boolean> sismember ( String key , String member ) {
		return getAsynResult(redisCommands.sismember(key, member)) ;
	}

	
	public AsynResult<List<String>> smemebers ( String key ) {
		return getAsynResult(redisCommands.smemebers(key)) ;
	}

	
	public AsynResult<Boolean> smove ( String source , String destination , String member ) {
		return getAsynResult(redisCommands.smove(source, destination, member)) ;
	}

	
	public AsynResult<String> spop ( String key ) {
		return getAsynResult(redisCommands.spop(key)) ;
	}

	
	public AsynResult<String> srandmember ( String key ) {
		return getAsynResult(redisCommands.srandmember(key)) ;
	}

	
	public AsynResult<List<String>> srandmember ( String key , int count ) {
		return getAsynResult(redisCommands.srandmember(key,count)) ;
	}

	
	public AsynResult<Boolean> srem ( String key , String member ) {
		return getAsynResult(redisCommands.srem(key, member)) ;
	}

	
	public AsynResult<Long> srem ( String key , List< String > member ) {
		return getAsynResult(redisCommands.srem(key, member)) ;
	}

	
	public AsynResult<List<String>> sunion ( List< String > key ) {
		return getAsynResult(redisCommands.sunion(key)) ;
	}

	
	public AsynResult<Long> sunionstore ( String destination , List< String > key ) {
		return getAsynResult(redisCommands.sunionstore(destination, key)) ;
	}

	
	public AsynResult<Boolean> zadd ( T key ) {
		return getAsynResult(redisCommands.zadd(key)) ;
	}

	
	public AsynResult<Long> zadd ( List< T > list ) {
		return getAsynResult(redisCommands.zadd(list)) ;
	}

	
	public AsynResult<Long> zcard ( String key ) {
		return getAsynResult(redisCommands.zcard(key)) ;
	}

	
	public AsynResult<Long> zcount ( String key , int min , int max ) {
		return getAsynResult(redisCommands.zcount(key, min, max)) ;
	}

	
	public AsynResult<Long> zincrby ( T key ) {
		return getAsynResult(redisCommands.zincrby(key)) ;
	}

	
	public AsynResult<List<T>> zrange ( String key , SortedSetParameter sortedSetParameter ) {
		return getAsynResult(redisCommands.zrange(key, sortedSetParameter));
	}

	
	public AsynResult<List<T>> zrangebyscore ( String key , SortedSetParameter sortedSetParameter ) {
		return getAsynResult(redisCommands.zrangebyscore(key, sortedSetParameter)) ;
	}

	
	public AsynResult<Long> zrank ( String key , String member ) {
		return getAsynResult(redisCommands.zrank(key, member)) ;
	}

	
	public AsynResult<Long> zrem ( T key ) {
		return getAsynResult(redisCommands.zrem(key)) ;
	}

	
	public AsynResult<Long> zrem ( List< T > list ) {
		return getAsynResult(redisCommands.zrem(list)) ;
	}

	
	public AsynResult<Long> zremrangebyrank ( String key , int start , int stop ) {
		return getAsynResult(redisCommands.zremrangebyrank(key, start, stop)) ;
	}

	
	public AsynResult<Long> zremrangebyscore ( String key , int min , int max ) {
		return getAsynResult(redisCommands.zremrangebyscore(key, min, max)) ;
	}

	
	public AsynResult<List<T>> zrevrange ( String key , SortedSetParameter sortedSetParameter ) {
		return getAsynResult(redisCommands.zrevrange(key, sortedSetParameter)) ;
	}

	
	public AsynResult<List<T>> zrevrangebyscore ( String key , SortedSetParameter sortedSetParameter ) {
		return getAsynResult(redisCommands.zrevrangebyscore(key, sortedSetParameter)) ;
	}

	
	public AsynResult<Long> zrevrank ( String key , String member ) {
		return getAsynResult(redisCommands.zrevrank(key, member));
	}

	
	public AsynResult<Long> zscore ( String key , String member ) {
		return getAsynResult(redisCommands.zscore(key, member)) ;
	}

	
	public AsynResult<List<T>> zunionstore ( String key , ZunionstoreParameter zunionstoreParameter ) {
		return getAsynResult(redisCommands.zunionstore(key, zunionstoreParameter)) ;
	}

	
	public AsynResult<List<T>> zinterstore ( String key , ZunionstoreParameter zunionstoreParameter ) {
		return getAsynResult(redisCommands.zinterstore(key, zunionstoreParameter)) ;
	}
	

}
