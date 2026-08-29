package com.lamp.foundation.base.function.crud.load.java;

import java.util.List;
import java.util.Objects;

import com.lamp.foundation.api.constant.ASCIIConstant;
import com.lamp.foundation.api.extension.databases.metadata.KeyType;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

import lombok.Data;

@Data
public class EntityBuild {

    private String basePath;

    private List<String> imports;

    private String license;

    private String superClass;

    private String superPackage;

    private boolean lombok = true;

    private StringBuilder builder = new StringBuilder();


    public void build(TableInfo tableInfo) {
        builder.append(this.license).append(ASCIIConstant.ALF).append(ASCIIConstant.ALF);
        builder.append(this.basePath).append(ASCIIConstant.ALF);
        this.imports.forEach(item -> {
            builder.append("import ").append(item).append(ASCIIConstant.M_SEMICOLON).append(ASCIIConstant.ALF);
        });
        if (Objects.nonNull(this.superClass)) {
            this.builder.append("import ").append(this.superClass).append(ASCIIConstant.M_SEMICOLON).append(ASCIIConstant.ALF);
        }
        if (this.lombok) {
            builder.append("import lombok.Data;\n ");
            builder.append("import lombok.AllArgsConstructor;\n");
            builder.append("import lombok.NoArgsConstructor;\n");
            if (Objects.nonNull(this.superClass)) {
                builder.append("import lombok.EqualsAndHashCode;");
            }

        }
        this.builder.append(this.superPackage).append(ASCIIConstant.ALF);

        if (this.lombok) {
            builder.append("@Data").append(ASCIIConstant.ALF);
            builder.append("@AllArgsConstructor").append(ASCIIConstant.ALF);
            builder.append("@NoArgsConstructor").append(ASCIIConstant.ALF);
            builder.append("@EqualsAndHashCode").append(ASCIIConstant.ALF);
        }
        tableInfo.getKeys().forEach(item -> {
            if (item.getLimitKeys().size() == 1) {
                if (item.getType() != KeyType.KEY) {
                    return;
                }
            }
            if(item.getType() == KeyType.KEY) {
                builder.append("@Key(");
                if(Objects.nonNull(item.getKeyName())){
                    builder.append("name = \"").append(item.getKeyName()).append("\"").append(ASCIIConstant.ADLE);
                }

            }
        });
        builder.append("@Entity(comment = \"测试表\", value = \"test_key\")").append(ASCIIConstant.ALF);
        builder.append("@Once(onecName = \"TestKey\", dbName = \"test_key\")").append(ASCIIConstant.ALF);
        builder.append("public class ").append(tableInfo.getName());
        if (Objects.nonNull(this.superClass)) {
            builder.append(" extends ").append(this.superClass);
        }
        builder.append(" {\n");




    }

}
