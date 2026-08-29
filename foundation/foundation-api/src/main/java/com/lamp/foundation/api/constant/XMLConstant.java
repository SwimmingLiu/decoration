package com.lamp.foundation.api.constant;

import com.lamp.foundation.api.root.Constant;

public interface XMLConstant extends Constant {

    char LABEL_START = ASCIIConstant.MKAR_LESS_THAN_SIGN;

    char LABEL_END = ASCIIConstant.M_GREATER_THAN_SIGN;

    char CLOSE = ASCIIConstant.M_SLASH;

    /**
     * />
     */
    String LABEL_CLOSE_END = "" + CLOSE + LABEL_END;

    /**
     * </
     */
    String LABEL_START_CLOSE = "" + LABEL_START + CLOSE;

}
