package com.lamp.foundation.base.lang.util;

public class AliCloudUtils {

	private static final String BUCKET_NAMING_REGEX = "^[a-z0-9][a-z0-9-]{1,61}[a-z0-9]$";
	
    public static boolean validateBucketName(String bucketName) {
        if (bucketName == null) {
            return false;
        }
        return bucketName.matches(BUCKET_NAMING_REGEX);
    }

    public static void ensureBucketNameValid(String bucketName) {
        if (!validateBucketName(bucketName)) {
            throw new IllegalArgumentException("");
        }
    }
}
