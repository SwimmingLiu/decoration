/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.foundation.api.security;

import java.io.OutputStream;

import javax.crypto.Cipher;

/**
 * TODO
 * <pre>
 *     通过计算 密钥类型，密钥长度，工作类型，填充类型，加上 明文数量，是计算出密文长度，这样可以一次申请内存
 *     非对称密钥都需要，分段加密
 *
 *     对称密钥都不需要分段加解密，目前只需要对称密钥的分段加解密
 *
 * </pre>
 */
public interface CipherStream {
	
	
	byte[] getByte(Cipher cipher, byte[] by, int length, boolean subsection) throws Exception ;
	
	void stream(Cipher cipher, byte[] by, OutputStream ots, int length, boolean subsection) throws Exception;
	
	
}
