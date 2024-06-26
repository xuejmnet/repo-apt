package com.eq.apt.repo.processor.templates;

/**
 * create time 2024/5/17 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptConstant {

    public static final String PROXY_TEMPLATE = "package @{package};\n" +
            "\n" +
            "@{imports}" +
            "\n" +
            "/**\n" +
            " * this file automatically generated by easy-query, don't modify it\n" +
            " * 当前文件是scf4j自动生成的请不要随意修改\n" +
            " *\n" +
            " * @author scf4j\n" +
            " */\n" +
            " @Component\n" +
            "public class @{classImplName} implements @{classInterfaceName} {\n" +
            "    private final ApplicationContext applicationContext;\n" +
            "    private final EasyEntityQuery easyEntityQuery;\n" +
            "\n" +
            "    public @{classImplName}(ApplicationContext applicationContext,EasyEntityQuery easyEntityQuery){\n" +
            "        this.applicationContext = applicationContext;\n" +
            "        this.easyEntityQuery = easyEntityQuery;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public Class<@{argClassName}> tableClass() {\n" +
            "        return @{argClassName}.class;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public ApplicationContext getApplicationContext() {\n" +
            "        return applicationContext;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public EntityQueryable<@{argClassProxyName}, @{argClassName}> getQuery() {\n" +
            "        return easyEntityQuery.queryable(tableClass());\n" +
            "    }\n" +
            "}";


}
