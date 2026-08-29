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

package com.lamp.foundation.base.lang.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IncrementThreadFactory implements ThreadFactory {

    private final static String SEQARATOR = "_";


    public static IncrementThreadFactory create(String name) {
        return new IncrementThreadFactory(name);
    }

    private final AtomicInteger threadIndex = new AtomicInteger(0);

    private final String name;

    private final String separator;


    public IncrementThreadFactory(String name) {
        this(name, SEQARATOR);
    }

    public IncrementThreadFactory(String name, String separator) {
        this.name = name;
        this.separator = separator;

    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + separator + this.threadIndex.incrementAndGet());
    }


}
