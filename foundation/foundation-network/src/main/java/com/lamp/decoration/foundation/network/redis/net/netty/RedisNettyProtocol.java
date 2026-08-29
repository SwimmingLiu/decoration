package com.lamp.decoration.foundation.network.redis.net.netty;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public interface RedisNettyProtocol<T> {

	static final byte EXCEPTION_HEAN = '-';

	static final byte STATUS_HEAN = '+';

	static final byte INTEGER_HEAN = ':';

	static final byte STRING_HEAN = '$';

	static final byte LIST_HEAD = '*';

	public boolean isComplete();

	public void distinguish(ByteBuf buf);

	public T getData();

	public boolean isException();

	abstract class AbstractRedisNettyProtocol<T> implements RedisNettyProtocol<T> {

		boolean complete;

		boolean readIntSuccess = false;

		T data;

		int readLength = -1;

		int getReadLength(ByteBuf buf) {
			readLength = buf.writerIndex() - buf.readerIndex();
			return readLength;
		}

		boolean isHaveReadData(ByteBuf buf) {
			return getReadLength(buf) == 0;
		}

		void setComplete(T data) {
			this.data = data;
			this.complete = true;
		}

		public boolean isComplete() {

			return complete;
		}

		public T getData() {
			T t = data;
			this.data = null;
			this.complete = false;
			this.readIntSuccess = false;
			return t;
		}

	}

	abstract class DataFetchRedisNettyProtocol<T> extends AbstractRedisNettyProtocol<T> {

		ByteBuf exceptionContentBuf = Unpooled.buffer(1024);

		private String exceptionContent;

		long intAnalysis(ByteBuf buf, long data, int readLength) {
			byte b;
			for (;;) {
				b = buf.readByte();
				readLength--;
				if (b == '\r' && readLength > 0) {
					buf.readByte();
					break;
				}
				if (b == '\n')
					break;
				data = data * 10 + b - '0';
				if (readLength == 0) {
					this.readLength = 0;
					return data;
				}
			}
			readLength--;
			this.readLength = readLength;
			this.readIntSuccess = true;
			return data;
		}

		void readLine(ByteBuf buf, long readLength) {
			byte b;
			for (;;) {
				b = buf.readByte();
				readLength--;
				if (b == '\r' && readLength > 0) {
					buf.readByte();
					break;
				}
				if (b == '\n')
					break;
				if (readLength == 0) {
					this.readLength = 0;
					return;
				}
				exceptionContentBuf.writeByte(b);
			}
			setComplete(null);
			exceptionContent = (String) exceptionContentBuf.getCharSequence(0, exceptionContentBuf.writerIndex(),
					Charset.defaultCharset());
			exceptionContentBuf.writerIndex(0).readerIndex(0);
		}

		public String getExceptionContent() {
			getData();
			return exceptionContent;
		}
	}

	abstract class ExceptionRedisNettyProtocol<T> extends DataFetchRedisNettyProtocol<T> {

		private boolean exception;

		private boolean exceptionTesting;

		public void distinguish(ByteBuf buf) {
			if (isHaveReadData(buf))
				return;
			if (this.exceptionTesting) {
				analysis(buf);
				return;
			}
			if (this.exception) {
				readLine(buf, readLength);
				return;
			}
			this.readLength -= 1;
			byte mark = buf.readByte();
			if (mark == EXCEPTION_HEAN) {
				this.exception = true;
				readLine(buf, readLength);
			} else {
				this.exceptionTesting = true;
				if (this.readLength == 0)
					return;
				analysis(buf);
			}
		}

		public boolean isException() {
			return exception;
		}

		byte getmarking() {
			return (byte) '1';
		}

		abstract void analysis(ByteBuf buf);

		public T getData() {
			this.exception = false;
			this.exceptionTesting = false;
			return super.getData();
		}

	}

	static class StateRedisNettyProtocol extends ExceptionRedisNettyProtocol<Boolean> {

		private static final byte[] STATE_CONTENT = { 'O', 'K', '\r', '\n' };

		private int readIndex = 0;

		private boolean exception;

		@Override
		public void analysis(ByteBuf buf) {
			if (exception) {
				readLine(buf, readLength);
			} else {
				byte b;
				for (;;) {
					if (!((b = buf.readByte()) == STATE_CONTENT[readIndex++])) {
						exception = true;
						exceptionContentBuf.writeByte(b);
						readLine(buf, --readLength);
						break;
					}
					readLength--;
					if (readIndex == 4)
						setComplete(true);
					if (readLength == 0)
						return;
				}
			}
		}

		@Override
		public Boolean getData() {
			readIndex = 0;
			exception = false;
			return super.getData();
		}
	}

	static class IntRedisNettyProtocol extends ExceptionRedisNettyProtocol<Long> {

		boolean isNeg;

		@Override
		public void analysis(ByteBuf buf) {
			long da = data == null ? 0 : data;
			byte b;
			if (data == null) {
				b = buf.readByte();
				readLength--;
				isNeg = b == '-';
				if (!isNeg) {
					da = da * 10 + b - '0';
				}
			}
			data = intAnalysis(buf, da, readLength);
			if (this.readIntSuccess) {
				setComplete(isNeg ? -data : data);
			}
		}
	}

	static class StringRedisNettyProtocol extends ExceptionRedisNettyProtocol<ByteBuf> {

		private long surplusLength = 0;

		void stringAnalysis(ByteBuf buf, boolean sequence) {
			long length;
			if (!readIntSuccess) {
				surplusLength = intAnalysis(buf, 0, readLength);
				if(surplusLength == -1) {
					super.setComplete(null);
					return;
				}
				if (readLength == 0)
					return;
				
			}
			length = surplusLength > this.readLength ? readLength : surplusLength;
			ByteBuf byteBuf = ByteBufManage.getReadByteBuf();
			buf.readBytes(byteBuf, (int) length);
			if ((surplusLength = surplusLength - length) == 0) {
				this.readLength = this.readLength-(int)length-2;
				buf.readerIndex(buf.readerIndex() + 2);
				super.setComplete(byteBuf);
			}else {
				
				this.readLength = this.readLength-(int)length;
			}
		}

		@Override
		public void analysis(ByteBuf buf) {
			stringAnalysis(buf, true);
		}

	}

	static abstract class ManyRedisNettyProtocol<T> extends ExceptionRedisNettyProtocol<T> {

		StringRedisNettyProtocol stringRedisNettyProtocol = new StringRedisNettyProtocol();

		IntRedisNettyProtocol intRedisNettyProtocol = new IntRedisNettyProtocol();

		ExceptionRedisNettyProtocol<?> currentProtocol;

		protected long manyLength = 0;

		protected long readLine = 0;

		void getObject() {}

		void setData(Object data) {}
		
		void start() {}
		
		void end() {}
		
		void middle() {}

		void analysis(ByteBuf buf) {
			if (!readIntSuccess) {
				manyLength = intAnalysis(buf, manyLength, readLength);
				getObject();
				if (manyLength == 0) {
					setComplete(null);
					return;
				}
				start();
			}

			if (readLine == 0) {

			}
			for (;;) {
				
				if (currentProtocol == null) {
					currentProtocol = buf.readByte() == ':' ? intRedisNettyProtocol : stringRedisNettyProtocol;
					if (this.readLength-- == 1)
						return;
				}
				currentProtocol.readLength= this.readLength;
				currentProtocol.analysis(buf);
				this.readLength = currentProtocol.readLength;
				if (currentProtocol.isComplete()) {
					readLine++;
					setData(currentProtocol.getData());
					currentProtocol = null;
				}
				if (readLine == manyLength) {
					end();
					setComplete(null);
					break;
				}
				if (this.readLength == 0) {
					break;
				}
			}

		}
	}

	static class SetRedisNetProtocol extends ManyRedisNettyProtocol<Set<String>> {

		Set<String> set;

		void getObject() {
			set = new HashSet<>((int) manyLength);
		}

		void setData(Object data) {
			set.add(ByteBufManage.getString());
		}
		
		@Override
		void setComplete(Set<String> data) {
			super.setComplete(set);
		}
	}

	static class ListRedisNetProtocol extends ManyRedisNettyProtocol<Long> {
		
		private static final byte START = '[';
		
		private static final byte END =']';
		
		private static final byte fen = '"';
		
		private static final byte[] fff = "\",\"".getBytes(); 
		
		void start() {
			ByteBufManage.setByte(START);
			ByteBufManage.setByte(fen);
		}

		void end() {
			ByteBufManage.setByte(fen);
			ByteBufManage.setByte(END);
		}
		
		void setData(Object data) {
			if(readLine < manyLength) {
				ByteBufManage.setByteArray(fff);
			}
		}
		
	}

	static class MapRedisNetProtocol extends ManyRedisNettyProtocol<Long> {
		
		
		private static final byte START = '{';
		
		private static final byte END ='}';
		
		private static final byte fen = '"';

		private static final byte[] fff = "\",\"".getBytes(); 
		
		private static final byte[] ffff = "\":\"".getBytes(); 
		
		void start() {
			ByteBufManage.setByte(START);
			ByteBufManage.setByte(fen);
		}

		void end() {
			ByteBufManage.setByte(fen);
			ByteBufManage.setByte(END);
		}
		
		void setData(Object data) {
			
			
			if (readLine < manyLength) {
				if (readLine % 1 == 0) {
					ByteBufManage.setByteArray(ffff);
				}else {
					ByteBufManage.setByteArray(fff);
				}
			}
		}
	}

}
