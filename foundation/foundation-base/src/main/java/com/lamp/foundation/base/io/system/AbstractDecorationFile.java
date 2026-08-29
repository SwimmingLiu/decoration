package com.lamp.foundation.base.io.system;

import com.lamp.foundation.api.lang.io.DecorationFile;

public abstract class AbstractDecorationFile implements DecorationFile {

    public DecorationFile getChild(String name) {
        String child = null;
        if (name.startsWith("./")) {
            child = name.substring(2);
        }
        if (name.startsWith(this.getPath())) {
            child = name.substring(this.getPath().length());
        }
        String[] splitArray = child.split("/");
        DecorationFile childFile = null;
        for (String s : splitArray) {
            childFile = this.getChildByName(s);
            if (!childFile.exists()) {
                return childFile;
            }
        }
        return childFile;
    }


    abstract DecorationFile getChildByName(String name);

}
