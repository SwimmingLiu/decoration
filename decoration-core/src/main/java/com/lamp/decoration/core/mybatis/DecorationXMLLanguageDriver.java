package com.lamp.decoration.core.mybatis;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lamp.decoration.core.mybatis.SQLFragment.FragmentInfo;
import com.lamp.foundation.base.extension.persistence.PersistenceUtils;

import lombok.Setter;

public class DecorationXMLLanguageDriver extends XMLLanguageDriver {

    private static final Set<Class<? extends Annotation>> STATEMENT_ANNOTATION_TYPES = Stream
        .of(Select.class, Update.class, Insert.class, Delete.class)
        .collect(Collectors.toSet());


    private static final Field NODE_HANDLER_MAP_FIELDS;

    private static final Map<Class<?>, Supplement> SUPPLEMENTS = new HashMap<>();

    static {
        NODE_HANDLER_MAP_FIELDS = FieldUtils.getDeclaredField(XMLScriptBuilder.class, "nodeHandlerMap", true);
        registerSupplement(new InsertSupplement());
        registerSupplement(new SelectSupplement());
        registerSupplement(new UpdateSupplement());
    }

    public static void registerSupplement(Supplement supplement) {
        SUPPLEMENTS.put(supplement.match(), supplement);
    }

    private Constructor<?> includeHandlerConstructor;

    @Setter
    private SQLFragment sqlFragment;


    public void init() {
        try {
            Class<?> includeHandlerClass = this.createIncludeHandler();
            includeHandlerConstructor = includeHandlerClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        DecorationXMLScriptBuilder builder = new DecorationXMLScriptBuilder(configuration, script, parameterType);
        try {
            Object includehandler = includeHandlerConstructor.newInstance();
            DecorationIncludeHandler decorationIncludeHandler = new DecorationIncludeHandler();
            FieldUtils.writeField(includehandler, "decorationIncludeHandler", decorationIncludeHandler, true);
            decorationIncludeHandler.decorationXMLScriptBuilder = builder;
            builder.register("ref", includehandler);


        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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

        if (script.contains("<script>")) {
            return script;
        }
        script = this.keywordSupplement(script);
        if (script.indexOf('#') > -1 || script.indexOf('<') > -1) {
            return "<script>" + script + "</script>";
        }
        return script;
    }

    private String keywordSupplement(String script) {
        Method method = DecorationMapperAnnotationBuilder.getMethod();
        if (Objects.isNull(method)) {
            return script;
        }
        for (Class<? extends Annotation> annotationClass : STATEMENT_ANNOTATION_TYPES) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (Objects.nonNull(annotation)) {
                Supplement supplement = SUPPLEMENTS.get(annotation.annotationType());
                return supplement.supplement(script, method);
            }
        }
        return script;
    }

    public Class<?> createIncludeHandler() {
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.importPackage("java.utils.List");
            pool.importPackage("org.apache.ibatis.parsing.XNode");
            pool.importPackage("org.apache.ibatis.scripting.xmltags.SqlNode");

            CtClass innerClass = pool.makeClass(XMLScriptBuilder.class.getName() + "$IncludeHandler");
            innerClass.addInterface(pool.get(XMLScriptBuilder.class.getName() + "$NodeHandler"));

            CtField decorationIncludeHandlerField = CtField.make(
                "private com.lamp.decoration.core.mybatis.DecorationXMLLanguageDriver.DecorationIncludeHandler decorationIncludeHandler;",
                innerClass);

            innerClass.addField(decorationIncludeHandlerField);

            CtMethod handleNode = CtMethod.make(
                "public void handleNode(org.apache.ibatis.parsing.XNode arg0,java.util.List arg1) {" +
                "       this.decorationIncludeHandler.handleNode(arg0, arg1);" +
                "}",
                innerClass
            );
            innerClass.addMethod(handleNode);

            return pool.toClass(innerClass, XMLScriptBuilder.class, this.getClass().getClassLoader(), this.getClass().getProtectionDomain());
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }


    static class DecorationXMLScriptBuilder extends XMLScriptBuilder {

        private final Map<String, Object> nodeHandlerMap;

        @SuppressWarnings("unchecked")
        public DecorationXMLScriptBuilder(Configuration configuration, XNode context, Class<?> parameterType) {

            super(configuration, context, parameterType);
            try {
                nodeHandlerMap = (Map<String, Object>) FieldUtils.readField(NODE_HANDLER_MAP_FIELDS, this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public void register(String key, Object handler) {
            nodeHandlerMap.put(key, handler);
        }

    }

    public class DecorationIncludeHandler {

        private DecorationXMLScriptBuilder decorationXMLScriptBuilder;


        public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
            String test = nodeToHandle.getStringAttribute("test");
            String ref = nodeToHandle.getStringAttribute("name");

            FragmentInfo fragmentInfo = sqlFragment.getFragmentInfo(ref);
            if (Objects.isNull(fragmentInfo.getSqlNode())) {
                SqlNode sqlNode;
                if (fragmentInfo.getContent().indexOf('<') == -1) {
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
            if (Objects.nonNull(test)) {
                IfSqlNode ifSqlNode = new IfSqlNode(fragmentInfo.getSqlNode(), test);
                fragmentInfo.setSqlNode(ifSqlNode);
            }
            targetContents.add(fragmentInfo.getSqlNode());
        }

    }

    /**
     *
     */
    public interface Supplement {

        Class<?> match();

        String supplement(String sql, Method method);

    }

    public static abstract class AbstractSupplement implements Supplement {

        abstract String doSupplement(String sql, Method method);

        @Override
        public String supplement(String sql, Method method) {
            sql = this.doSupplement(sql, method);
            if (sql.contains("<t/>")) {
                sql = sql.replace("<t/>", this.getTableName(method));
            }
            return sql;
        }

        protected String getTableName(Method method) {
            String tableName = PersistenceUtils.getTableName(method);
            if (Objects.nonNull(tableName)) {
                return tableName;
            }
            Class<?> clazz = method.getDeclaringClass();
            if (method.getParameters().length == 1) {
                String format = "Failed to obtain table name, method %s , current class %s ， parameters type %s, ";
                throw new RuntimeException(String.format(format, method.getName(), clazz.getName(), method.getParameters()[0].getType().getName()));
            } else {
                String format = "Failed to obtain table name, method %s , current class %s ， parameters length not equal to 1 ";
                throw new RuntimeException(String.format(format, method.getName(), clazz.getName()));
            }
        }

    }


    public static class SelectSupplement extends AbstractSupplement {


        @Override
        public Class<?> match() {
            return Select.class;
        }

        @Override
        public String doSupplement(String sql, Method method) {
            if (sql.startsWith("<")) {
                return sql;
            }
            if (sql.startsWith("select") || sql.startsWith("SELECT")) {
                return sql;
            }
            if (sql.startsWith("from") || sql.startsWith("FROM")) {
                return "select * " + sql;
            }
            if (sql.startsWith("where") || sql.startsWith("WHERE")) {
                return "select * from " + this.getTableName(method) + " " + sql;
            }
            String format = "automatic recognition table by, method is %s";
            throw new RuntimeException(String.format(format, method.getName()));
        }
    }

    public static class InsertSupplement extends AbstractSupplement {

        @Override
        public Class<?> match() {
            return Insert.class;
        }

        @Override
        public String doSupplement(String sql, Method method) {
            if (sql.startsWith("<")) {
                return sql;
            }
            if (sql.startsWith("insert") || sql.startsWith("INSERT")) {
                return sql;
            }
            if (!sql.startsWith("(")) {
                return " insert into " + sql;
            }
            return "insert into " + this.getTableName(method) + "  " + sql;
        }
    }

    public static class UpdateSupplement extends AbstractSupplement {

        @Override
        public Class<?> match() {
            return Update.class;
        }

        @Override
        public String doSupplement(String sql, Method method) {
            if (sql.startsWith("<")) {
                return sql;
            }
            if (sql.startsWith("update") || sql.startsWith("UPDATE")) {
                return sql;
            }
            if (sql.startsWith("set") || sql.startsWith("SET")) {
                return " update " + this.getTableName(method) + " " + sql;
            }
            return "update" + this.getTableName(method) + " set  " + sql;
        }
    }

}
