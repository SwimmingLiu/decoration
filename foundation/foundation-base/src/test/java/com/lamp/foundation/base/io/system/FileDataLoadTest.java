package com.lamp.foundation.base.io.system;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class FileDataLoadTest {

    @Test
    public void test() throws IOException {
        FileDataLoad<TestData> fileDataLoad = new FileDataLoad<>("./.test/test.json", TestData.class);
        TestData testData = new TestData();
        testData.setPath("1231231312312");
        testData.setFileName("t1231-----123est.json");

        fileDataLoad.persist(testData);

        TestData newTestData = fileDataLoad.read();
        Assert.assertEquals(testData, newTestData);
        // 快照
    }


    public void test_a(){

    }

}
