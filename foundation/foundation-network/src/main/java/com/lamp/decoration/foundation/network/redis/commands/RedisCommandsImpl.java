package com.lamp.decoration.foundation.network.redis.commands;

import java.util.List ;
import java.util.Map ;

import com.lamp.decoration.foundation.network.redis.annotation.OperationsEntity;
import com.lamp.decoration.foundation.network.redis.entity.BlockListResult;
import com.lamp.decoration.foundation.network.redis.entity.SortedSetParameter;
import com.lamp.decoration.foundation.network.redis.entity.ZunionstoreParameter;
import com.lamp.decoration.foundation.network.redis.utils.KeyCreateUtils;


public class RedisCommandsImpl< K , T > implements RedisCommands< K , T >{

	private StringCommands< T >   sc;
	
	private HashCommands< K , T > hc;
	
	private ListCommands< T >     lc;
	
	private SetCommands   setc;
	
	private SortedSetCommands<T>  ssc;
	
	private OperationsEntity operationsEntity;
	
	@SuppressWarnings( "unchecked" )
	public RedisCommandsImpl( OperationsEntity operationsEntity ) throws Exception{
		this.operationsEntity = operationsEntity;
		sc    = new StringCommandsImpl<>( KeyCreateUtils.getInstance( ).createKeyCreate( operationsEntity.getString( ) ),null );
		lc    = new ListCommandsImpl<  >( KeyCreateUtils.getInstance( ).createKeyCreate( operationsEntity.getList( ) )  , null);	
		setc  = new SetCommandsImpl     ( KeyCreateUtils.getInstance( ).createKeyCreate( operationsEntity.getSet( )  )  , null );
		if(operationsEntity.getHash().getMapKey() != null){
			hc    = new HashCommandsImpl<  >( KeyCreateUtils.getInstance( ).createKeyCreate( operationsEntity.getHash( ) )  , null );
		}
		if(operationsEntity.getSortedSet().getMapKey() != null){
			ssc   = new SortedSetCommandsImpl<>(KeyCreateUtils.getInstance( ).createKeyCreate( operationsEntity.getSortedSet( ) ) , null );
		}
	}
	
	@Override
	public BlockListResult<T> blpop ( T key ) {
		return lc.blpop(key);
	}

	@Override
	public BlockListResult<T> blpop ( long key ) {
		return lc.blpop(key);
	}

	@Override
	public BlockListResult<T> blpop ( String key ) {
		return lc.blpop(key);
	}

	@Override
	public BlockListResult<T> blpop ( T key , int timeout ) {
		return lc.blpop(key, timeout);
	}

	@Override
	public BlockListResult<T> blpop ( long key , int timeout ) {
		return lc.blpop(key, timeout);
	}

	@Override
	public BlockListResult<T> blpop ( String key , int timeout ) {
		return lc.blpop(key, timeout);
	}

	@Override
	public BlockListResult<T> brpop ( T key ) {
		return lc.blpop(key);
	}

	@Override
	public BlockListResult<T> brpop ( long key ) {
		return lc.brpop(key);
	}

	@Override
	public BlockListResult<T> brpop ( String key ) {
		return lc.brpop(key);
	}

	@Override
	public BlockListResult<T> brpop ( T key , int timeout ) {
		return lc.brpop(key, timeout);
	}

	@Override
	public BlockListResult<T> brpop ( long key , int timeout ) {
		return lc.brpop(key, timeout);
	}

	@Override
	public BlockListResult<T> brpop ( String key , int timeout ) {
		return lc.brpop(key, timeout);
	}

	@Override
	public BlockListResult<T> brpoplpush ( T source , T destination ) {
		return lc.brpoplpush(source, destination);
	}

	@Override
	public BlockListResult<T> brpoplpush ( long source , long destination ) {
		return lc.brpoplpush(source, destination);
	}

	@Override
	public BlockListResult<T> brpoplpush ( String source , String destination ) {
		return lc.brpoplpush(source, destination);
	}

	@Override
	public BlockListResult<T> brpoplpush ( T source , T destination , int timeout ) {
		return lc.brpoplpush(source, destination);
	}

	@Override
	public BlockListResult<T> brpoplpush ( long source , long destination , int timeout ) {
		return lc.brpoplpush(source, destination, timeout);
	}

	@Override
	public BlockListResult<T> brpoplpush ( String source , String destination , int timeout ) {
		return lc.brpoplpush(source, destination, timeout);
		}

	@Override
	public T lindex ( T key , int index ) {
		return lc.lindex(key, index);
	}

	@Override
	public T lindex ( long key , int index ) {
		return lc.lindex(key, index);
	}

	@Override
	public T lindex ( String key , int index ) {
		return lc.lindex(key, index);
	}

	@Override
	public long linsert ( String key , String pivot , String value ) {
		return lc.linsert(key, pivot, value);
	}

	@Override
	public long llen ( T key ) {
		return lc.llen(key);
	}

	@Override
	public long llen ( String key ) {
		return lc.llen(key);
	}

	@Override
	public long llen ( long key ) {
		return lc.llen(key);
	}

	@Override
	public T lpop ( T key ) {
		return lc.lpop(key);
	}

	@Override
	public T lpop ( String key ) {
		return lc.lpop(key);
	}

	@Override
	public T lpop ( long key ) {
		return lc.lpop(key);
	}

	@Override
	public long lpush ( T keyValue ) {
		return lc.lpush(keyValue);
	}

	@Override
	public long lpush ( List< T > keyValue ) {
		return lc.lpush(keyValue);
	}

	@Override
	public long lpushx ( T keyValue ) {
		return lc.lpushx(keyValue);
	}

	@Override
	public List<T> lrange ( T key , int start , int end ) {
		return lc.lrange(key, start, end);
	}

	@Override
	public List<T> lrange( String key , int start , int end){
		return lc.lrange(key, start, end);
	}

	@Override
	public List<T> lrange(long key, int start , int end){
		return lc.lrange(key, start, end);
	}

	@Override
	public long lren(String key, int count , String value){
		return lc.lren(key, count, value);
	}

	@Override
	public boolean lset(T key, int index){
		return lc.lset(key, index);
	}

	@Override
	public boolean ltrim(T key, int start, int end){
		return lc.ltrim(key, start, end);
	}

	@Override
	public boolean ltrim(String key, int start, int end){
		return lc.ltrim(key, start, end);
	}

	@Override
	public boolean ltrim(long key, int start, int end){
		return lc.ltrim(key, start, end);
	}

	@Override
	public T rpop(T key){
		return lc.rpop(key);
	}

	@Override
	public T rpop(long key){
		return lc.rpop(key);
	}

	@Override
	public T rpop(String key){
		return lc.rpop(key);
	}

	@Override
	public T rpoplpush(T source, T destination){
		return lc.rpoplpush(source, destination);
	}

	@Override
	public T rpoplpush(long source, long destination){
		return lc.rpoplpush(source, destination);
	}

	@Override
	public T rpoplpush(String source, String destination){
		return lc.rpoplpush(source, destination);
	}

	@Override
	public long rpush(T keyValue){
		return lc.rpush(keyValue);
	}

	@Override
	public long rpush(List<T> keyValue){
		return lc.rpush(keyValue);
	}

	@Override
	public long rpushx (T keyValue){
		return lc.rpushx(keyValue);
	}

	@Override
	public boolean del(String key){
		return lc.del(key);
	}

	@Override
	public boolean exists(String key){
		return lc.exists(key);
	}

	@Override
	public boolean expire(String key, int seconds){
		return lc.expire(key, seconds);
	}

	@Override
	public boolean pexpire(String key, int milliseconds){
		return lc.pexpire(key, milliseconds);
	}

	@Override
	public boolean expireat(String key, long timestamp){
		return lc.expireat(key, timestamp);
	}

	@Override
	public boolean pexpireat(String key, long milliseconds){
		return lc.pexpireat(key, milliseconds);
	}

	@Override
	public boolean rename(String key, String newkey){
		return lc.rename(key, newkey);
	}

	@Override
	public boolean renamenx(String key, String newkey){
		return lc.renamenx(key, newkey);
	}

	@Override
	public boolean hdel(T key){
		return hc.hdel(key);
	}

	@Override
	public boolean hdel(String key, K field){
		return hc.hdel(key, field);
	}

	@Override
	public boolean hdel(Long key, K field){
		return hc.hdel(key, field);
	}

	@Override
	public boolean hdel(List<T> key){
		return hc.hdel(key);
	}

	@Override
	public boolean hexists(T key){
		return hc.hexists(key);
	}

	@Override
	public boolean hexists(String key, String field){
		return hc.hexists(key, field);
	}

	@Override
	public boolean hexists(long key , long field){
		return hc.hexists(key, field);
	}

	@Override
	public T hget (T key){
		return hc.hget(key);
	}

	@Override
	public T hget(String key, String field){
		return hc.hget(key, field);
	}

	@Override
	public T hget(long key, long field){
		return hc.hget(key, field);
	}

	@Override
	public Map<K, T> hgetall(T key){
		return hc.hgetall(key);
	}

	@Override
	public Map<K, T>hgetall(String key){
		return hc.hgetall(key);
	}

	@Override
	public Map<K, T> hgetall(long key){
		return hc.hgetall(key);
	}

	@Override
	public long hincrby(T key, long increment){
		return hc.hincrby(key, increment);
	}

	@Override
	public long hincrby(String key, String field, long increment){
		return hc.hincrby(key, field, increment);
	}

	@Override
	public long hincrby(Long key ,Long field ,long increment){
		return hc.hincrby(key, field, increment);
	}

	@Override
	public List<K> hkeys(T key){
		return hc.hkeys(key);
	}

	@Override
	public List<K> hkeys(String key){
		return hc.hkeys(key);
	}

	@Override
	public List<K> hkeys(long key){
		return hc.hkeys(key);
	}

	@Override
	public List<T> hvals(T key){
		return hc.hvals(key);
	}

	@Override
	public List<T> hvals(String key){
		return hc.hvals(key);
	}

	@Override
	public List<T> hvals(long key){
		return hc.hvals(key);
	}

	@Override
	public long hlen(T key){
		return hc.hlen(key);
	}

	@Override
	public long hlen(String key){
		return hc.hlen(key);
	}

	@Override
	public long hlen(long key){
		return hc.hlen(key);
	}

	@Override
	public List<T> hmget(List<T> t){
		return hc.hmget(t);
	}

	@Override
	public List<T> hmget(String key, List<T> t){
		return hc.hmget(key, t);
	}

	@Override
	public List<T> hmget(Number key, List<T> t){
		return hc.hmget(key, t);
	}

	@Override
	public boolean hmset(List<T> t){
		return hc.hmset(t);
	}

	@Override
	public boolean hmset(String key, List<T> t){
		return hc.hmset(key, t);
	}

	@Override
	public boolean hmset(Number key, List<T> t){
		return hc.hmset(key, t);
	}

	@Override
	public boolean hset(T key){
		return hc.hset(key);
	}

	@Override
	public boolean hset(String key, T t){
		return hc.hset(key, t);
	}

	@Override
	public boolean hset(Number key, T t){
		return hc.hset(key, t);
	}

	@Override
	public boolean hsetnx(T key){
		return hc.hsetnx(key);
	}

	@Override
	public boolean hsetnx(String key, T t){
		return hc.hsetnx(key, t);
	}

	@Override
	public boolean hsetnx(Number key, T t){
		return hc.hsetnx(key, t);
	}

	@Override
	public T get (T key) {
		return sc.get(key);
	}

	@Override
	public T get(String key){
		return sc.get(key);
	}

	@Override
	public T get(long key){
		return sc.get(key);
	}

	@Override
	public T getset(T key){
		return sc.getset(key);
	}

	@Override
	public List<T> mget(List<T> keys){
		return sc.mget(keys);
	}

	@Override
	public List<T> mgetstring(List<String> keys){
		return sc.mgetstring(keys);
	}

	@Override
	public List<T> mgetnumber(List<? extends Number> keys){
		return sc.mgetnumber(keys);
	}

	@Override
	public T set(T key){
		return sc.set(key);
	}

	@Override
	public Boolean setnx(T key){
		return sc.setnx(key);
	}

	@Override
	public Boolean setnx(T key,Object o){
		return sc.setnx(key, o);
	}

	@Override
	public Boolean setex(T key, long seconds){
		return sc.setnx(key, seconds);
	}

	@Override
	public Boolean setex(T key, Object o, long seconds){
		return sc.setex(key, o, seconds);
	}

	@Override
	public Boolean psetex(byte[] key, long milliseconds, byte[] value){
		return sc.psetex(key, milliseconds, value);
	}

	@Override
	public Boolean mset(List<T> tuple){
		return sc.mset(tuple);
	}

	@Override
	public Boolean msetnx(List<T> tuple){
		return sc.msetnx(tuple);
	}

	@Override
	public Long incr(T key){
		return sc.incr(key);
	}

	@Override
	public Long incrby(T key, long value){
		return sc.incrby(key, value);
	}

	@Override
	public Double incrby(T key, double value){
		return sc.incrby(key, value) ;
	}

	@Override
	public Long decr(T key){
		return sc.incr(key);
	}

	@Override
	public Long decrby(T key, long value){
		return sc.decrby(key, value);
	}

	@Override
	public Long strlen(T key){
		return sc.strlen(key);
	}

	@Override
	public boolean persist(String key){
		return lc.persist(key);
	}

	@Override
	public long sadd(String key, String member) {
		return setc.sadd(key, member);
	}

	@Override
	public long sadd(String key, List<String> member) {
		return setc.sadd(key, member);
	}

	@Override
	public long scard(String key) {
		return setc.scard(key);
	}

	@Override
	public List<String> sdiff(List<String> key) {
		return setc.sdiff(key);
	}

	@Override
	public long sdiffstore(String destination, List<String> key) {
		return setc.sdiffstore(destination, key);
	}

	@Override
	public List<String> sinter(List<String> key) {
		return setc.sinter(key);
	}

	@Override
	public long sinterstore(String destination, List<String> key) {
		return setc.sinterstore(destination, key);
	}

	@Override
	public boolean sismember(String key, String member) {
		return setc.sismember(key, member);
	}

	@Override
	public List<String> smemebers(String key) {
		return setc.smemebers(key);
	}

	@Override
	public boolean smove(String source, String destination, String member) {
		return setc.smove(source, destination, member);
	}

	@Override
	public String spop(String key) {
		return setc.spop(key);
	}

	@Override
	public String srandmember(String key) {
		return setc.srandmember(key);
	}

	@Override
	public List<String> srandmember(String key, int count) {
		return setc.srandmember(key, count);
	}

	@Override
	public boolean srem(String key, String member) {
		return setc.srem(key, member);
	}

	@Override
	public long srem(String key, List<String> member) {
		return setc.srem(key, member);
	}

	@Override
	public List<String> sunion(List<String> key) {
		return setc.sunion(key);
	}

	@Override
	public long sunionstore(String destination, List<String> key) {
		return setc.sunionstore(destination, key);
	}

	@Override
	public boolean zadd(T key) {
		return ssc.zadd(key);
	}

	@Override
	public long zadd(List<T> list) {
		return ssc.zadd(list);
	}

	@Override
	public long zcard(String key) {
		return ssc.zcard(key);
	}

	@Override
	public long zcount(String key, int min, int max) {
		return ssc.zcount(key, min, max);
	}

	@Override
	public long zincrby(T key) {
		return ssc.zincrby(key);
	}

	@Override
	public List<T> zrange(String key, SortedSetParameter sortedSetParameter) {
		return ssc.zrange(key, sortedSetParameter);
	}

	@Override
	public List<T> zrangebyscore(String key, SortedSetParameter sortedSetParameter) {
		return ssc.zrangebyscore(key, sortedSetParameter);
	}

	@Override
	public long zrank(String key, String member) {
		return ssc.zrank(key, member);
	}

	@Override
	public long zrem(T key) {
		return ssc.zrem(key);
	}

	@Override
	public long zrem(List<T> list) {
		return ssc.zrem(list);
	}

	@Override
	public long zremrangebyrank(String key, int start, int stop) {
		return ssc.zremrangebyrank(key, start, stop);
	}

	@Override
	public long zremrangebyscore(String key, int min, int max) {
		return ssc.zremrangebyscore(key, min, max);
	}

	@Override
	public List<T> zrevrange(String key, SortedSetParameter sortedSetParameter) {
		return ssc.zrevrange(key, sortedSetParameter);
	}

	@Override
	public List<T> zrevrangebyscore(String key, SortedSetParameter sortedSetParameter) {
		return ssc.zrevrangebyscore(key, sortedSetParameter);
	}

	@Override
	public long zrevrank(String key, String member) {
		return ssc.zrevrank(key, member);
	}

	@Override
	public long zscore(String key, String member) {
		return ssc.zscore(key, member);
	}

	@Override
	public List<T> zunionstore(String key, ZunionstoreParameter zunionstoreParameter) {
		return ssc.zunionstore(key, zunionstoreParameter);
	}

	@Override
	public List<T> zinterstore(String key, ZunionstoreParameter zunionstoreParameter) {
		return ssc.zunionstore(key, zunionstoreParameter);
	}

}
