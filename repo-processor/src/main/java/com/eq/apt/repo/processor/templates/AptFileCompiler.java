package com.eq.apt.repo.processor.templates;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * create time 2024/5/17 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptFileCompiler {
    private final String entityClassName;
    private final String entityClassImplName;
    private final String argClassName;
    private final String argClassProxyName;
    private final String packageName;
    private Set<String> imports;

    public AptFileCompiler(String packageName,String entityClassName,String entityClassImplName,String argClassName,String argClassProxyName) {
        this.packageName = packageName;
        this.entityClassName = entityClassName;
        this.entityClassImplName = entityClassImplName;
        this.argClassName = argClassName;
        this.argClassProxyName = argClassProxyName;
        this.imports = new LinkedHashSet<>();
    }

    public String getEntityClassImplName() {
        return entityClassImplName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void addImports(String fullClassPackageName) {
        if(fullClassPackageName!=null){
            imports.add("import " + fullClassPackageName + ";");
        }
    }

    public String getArgClassName() {
        return argClassName;
    }

    public String getArgClassProxyName() {
        return argClassProxyName;
    }
}
