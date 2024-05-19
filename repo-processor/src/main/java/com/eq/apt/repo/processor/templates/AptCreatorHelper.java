package com.eq.apt.repo.processor.templates;

/**
 * create time 2024/5/17 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptCreatorHelper {
    public static String createProxy(AptFileCompiler aptFileCompiler) {

        String proxyTemplate = AptConstant.PROXY_TEMPLATE
                .replace("@{package}", aptFileCompiler.getPackageName())
                .replace("@{imports}", String.join("\n", aptFileCompiler.getImports()))
                .replace("@{classImplName}", aptFileCompiler.getEntityClassImplName())
                .replace("@{classInterfaceName}", aptFileCompiler.getEntityClassName())
                .replace("@{argClassName}", aptFileCompiler.getArgClassName())
                .replace("@{argClassProxyName}", aptFileCompiler.getArgClassProxyName());
        return proxyTemplate;
    }
}
