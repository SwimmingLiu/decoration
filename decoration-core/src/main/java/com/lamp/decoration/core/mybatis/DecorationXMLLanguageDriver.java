package com.lamp.decoration.core.mybatis;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.decoration.core.mybatis.SQLFragment.FragmentInfo;

public class DecorationXMLLanguageDriver extends XMLLanguageDriver {

    private Object includeHandlerInstance;

    private SQLFragment sqlFragment;

    public void init() {
        try {
            Class<?> includeHandlerClass = this.createIncludeHandler();
            includeHandlerInstance = includeHandlerClass.newInstance();
            DecorationIncludeHandler decorationIncludeHandler = new DecorationIncludeHandler();
            FieldUtils.writeField(includeHandlerInstance, "decorationIncludeHandler", decorationIncludeHandler, true);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        DecorationXMLScriptBuilder builder = new DecorationXMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    /**
     * <pre>
     *     调用逻辑，
     *       DecorationXMLLanguageDriver.createSqlSource,
     *         XMLLanguageDriver.createSqlSource
     *           DecorationXMLLanguageDriver.createSqlSource 这里构建 DecorationXMLScriptBuilder
     * </pre>
     */
    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        return super.createSqlSource(configuration, this.supplement(script), parameterType);
    }

    private String supplement(String script) {
        if (script.indexOf('#') > -1 && script.contains("<script>")) {
            script = "<script>" + script + "</script>";
        }
        return script;
    }

    public Class<?> createIncludeHandler() {
        try {
            ClassPool pool = ClassPool.getDefault();
            //CtClass outerClass = pool.get(XMLScriptBuilder.class.getName());

            pool.importPackage("java.utils.List");
            pool.importPackage("org.apache.ibatis.parsing.XNode");
            pool.importPackage("org.apache.ibatis.scripting.xmltags.SqlNode");

            CtClass innerClass = pool.makeClass(XMLScriptBuilder.class.getName() + "$IncludeHandler");
            innerClass.setSuperclass(pool.get("java.lang.Object"));
            innerClass.addInterface(pool.get(XMLScriptBuilder.class.getName() + "$NodeHandler"));
            CtMethod handleNode = CtMethod.make(
                "public void handleNode() { " +
                "}",
                innerClass
            );
            innerClass.addMethod(handleNode);

            CtField decorationIncludeHandlerField = CtField.make(
                "private com.lamp.decoration.core.mybatis.DecorationXMLLanguageDriver.DecorationIncludeHandler decorationIncludeHandler;",
                innerClass);

            return innerClass.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }


    class DecorationXMLScriptBuilder extends XMLScriptBuilder {


        private Map<String, Object> nodeHandlerMap = new HashMap<>();

        @SuppressWarnings("unchecked")
        public DecorationXMLScriptBuilder(Configuration configuration, XNode context, Class<?> parameterType) {
            super(configuration, context, parameterType);
            try {
                Map<String, Object> nodeHandlerMap = (Map<String, Object>) FieldUtils.readField(this, "nodeHandlerMap", true);
                nodeHandlerMap.put("nodeHandler", includeHandlerInstance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public class DecorationIncludeHandler {

        private DecorationXMLScriptBuilder decorationXMLScriptBuilder;


        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            String test = nodeToHandle.getStringAttribute("test");
            String name = nodeToHandle.getStringAttribute("name");

            FragmentInfo fragmentInfo = sqlFragment.getFragmentInfo(name);
            if (Objects.isNull(fragmentInfo.getSqlNode())) {
                SqlNode sqlNode;
                if (fragmentInfo.getContent().indexOf('<') != -1) {
                    TextSqlNode textSqlNode = new TextSqlNode(fragmentInfo.getContent());
                    if (textSqlNode.isDynamic()) {
                        sqlNode = textSqlNode;
                    } else {
                        sqlNode = new StaticTextSqlNode(fragmentInfo.getContent());
                    }
                } else {
                    SqlSource sqlSource = createSqlSource(decorationXMLScriptBuilder.getConfiguration(), supplement(fragmentInfo.getContent()), null);
                    try {
                        sqlNode = (SqlNode) FieldUtils.readField(sqlSource, "rootSqlNode", true);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                fragmentInfo.setSqlNode(sqlNode);
            }

            IfSqlNode ifSqlNode = new IfSqlNode(fragmentInfo.getSqlNode(), test);
            targetContents.add(ifSqlNode);
        }

    }
}
