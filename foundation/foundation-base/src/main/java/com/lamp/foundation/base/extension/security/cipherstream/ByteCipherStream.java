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

package com.lamp.foundation.base.extension.security.cipherstream;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;

import com.lamp.foundation.api.security.CipherStream;

public class ByteCipherStream implements CipherStream {

    @Override
    public byte[] getByte(Cipher cipher, byte[] by, int length, boolean subsection) throws Exception {
        int bylength;
        if (!subsection && (bylength = by.length) > length) {
            int more = bylength % length, num = bylength / length, offset = 0, i = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(length * (num + 1));
            for (; ; ) {
                bos.write(cipher.doFinal(by, offset, length));
                offset += length;
                if (++i == num) {
                    break;
                }
            }
            if (more > 0) {
                bos.write(cipher.doFinal(by, offset, more));
            }
            return bos.toByteArray();
        } else {
            return cipher.doFinal(by);
        }
    }

    @Override
    public void stream(Cipher cipher, byte[] by, OutputStream ots, int length, boolean subsection) throws Exception {

    }

}
