package com.lamp.decoration.foundation.network.redis.commands;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.lamp.decoration.foundation.network.redis.create.KeyCreate;
import com.lamp.decoration.foundation.network.redis.net.Connection;
import com.lamp.decoration.foundation.network.redis.net.netty.AgreementPretreatment;
import com.lamp.decoration.foundation.network.redis.protocol.DataConversion;
import com.lamp.decoration.foundation.network.redis.protocol.EecutionMode;


public class AbtractLedisPipeline<T> extends AbstractLedis<T> {

    private LinkedList<Object> list = new LinkedList<Object>();
    Connection conn = null;

    public AbtractLedisPipeline(KeyCreate keyCreate, String dataSource) {
        super(keyCreate, dataSource);
        try {
            conn = connectionPattern.getConnection();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public <T> T combinationScript(CombinationElement ce, List<DataConversion> dataList) {
        Connection conn = null;
        ByteBuffer buffer = null;
        try {
            conn = connectionPattern.getConnection();
            OutputStream out = conn.getOutputStream();
            ce.getAgreementPretreatment().perteatmentOut(out, 0);
            AgreementPretreatment.ListReferenceAgreementPretreatment(out, dataList, ce.getAgreementPretreatment().getLength());
            list.add(ce);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                buffer.clear();
            }
            connectionPattern.setConnection(conn);

        }
        return null;

    }

    @Override
    public <V> V combination(CombinationElement ce, List<DataConversion> dataList, List<T> objectList, KeyCreate<T> keyCreate) {
        Connection conn = null;
        ByteBuffer buffer = null;
        try {
            if (objectList.size() == 0) {
                keyCreate = (KeyCreate<T>) this.keyCreate;
                return (V) ce.getResultHandle().getNullOjbect(keyCreate);
            }
            OutputStream out = conn.getOutputStream();
            EecutionMode ecutionMode = ce.getAgreementPretreatment().getExecutioMode();
            ce.getAgreementPretreatment().perteatmentOut(out, ecutionMode.getMultiple() * objectList.size() + ecutionMode.getBase());
            if (ecutionMode == EecutionMode.STRING_MGET || ecutionMode == EecutionMode.MAP_MGET) {
                AgreementPretreatment.ListReferenceAgreementPretreatment(out, dataList, objectList, 1, keyCreate);
            } else if (ecutionMode == EecutionMode.STRING_MSET || ecutionMode == EecutionMode.MAP_MSET) {
                AgreementPretreatment.HashReferenceAgreementPretreatment(out, dataList, objectList, keyCreate);
            } else if (ecutionMode == EecutionMode.MAP_MSET) {
                int index = 0;

                for (; ; ) {

                    objectList.get(index++);
                }
            }
            return (V) null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (buffer != null) {
                buffer.clear();
            }
            connectionPattern.setConnection(conn);

        }
    }
}
