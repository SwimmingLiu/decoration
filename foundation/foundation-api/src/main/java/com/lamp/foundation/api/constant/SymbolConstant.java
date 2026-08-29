package com.lamp.foundation.api.constant;


import com.lamp.foundation.api.root.Constant;

public interface SymbolConstant extends Constant {

    String SINGLE_QUOTATION_PAIR = ASCIIConstant.M_SINGLE_QUOTATION + "" + ASCIIConstant.M_SINGLE_QUOTATION;

    String DOUBLE_QUOTATION_PAIR = ASCIIConstant.M_DOUBLE_QUOTATION + "" + ASCIIConstant.M_DOUBLE_QUOTATION;

    String DOUBLE_QUOTATION_TREE = DOUBLE_QUOTATION_PAIR + ASCIIConstant.M_DOUBLE_QUOTATION;

}
