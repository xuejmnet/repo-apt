package com.eq.apt.repo.processor.processor;

import com.easy.query.core.annotation.EntityProxy;
import com.eq.apt.repo.processor.annotations.ProxyRepository;
import com.eq.apt.repo.processor.templates.AptCreatorHelper;
import com.eq.apt.repo.processor.templates.AptFileCompiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * create time 2024/5/17 13:17
 * 文件说明
 *
 * @author xuejiaming
 */
//@SupportedAnnotationTypes({"com.eq.apt.repo.processor.annotations.ProxyRepository"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProxyRepositoryProcessor extends AbstractProcessor {
    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(ProxyRepository.class.getCanonicalName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {


            AtomicReference<String> entityClassNameReference = new AtomicReference<>();

//            StringBuilder tablesContent = new StringBuilder();
            roundEnv.getElementsAnnotatedWith(ProxyRepository.class).forEach((Consumer<Element>) entityClassElement -> {

//                String proxyEntityName = entityClassElement.getSimpleName().toString();

//                for (String entityIgnoreSuffix : entityIgnoreSuffixes) {
//                    if (proxyEntityName.endsWith(entityIgnoreSuffix.trim())) {
//                        proxyEntityName = proxyEntityName.substring(0, proxyEntityName.length() - entityIgnoreSuffix.length());
//                        break;
//                    }
//                }
//                entityClassElement.get
                String fullClassName=null;
                if (entityClassElement instanceof TypeElement) {
                    TypeElement typeElement = (TypeElement) entityClassElement;
                    // 获取接口的父接口，即 CrudRepository
                    List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
                    if(interfaces.size()!=1){
                        return;
                    }
                    TypeMirror interfaceType = interfaces.get(0);
                    if (interfaceType instanceof DeclaredType) {
                        DeclaredType declaredType = (DeclaredType) interfaceType;

                        // 获取泛型参数
                        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                        if(typeArguments.size()!=2){
                            return;
                        }
                        TypeMirror typeArgument = typeArguments.get(1);
                        fullClassName=typeArgument.toString();
                    }
                }
                if(fullClassName==null){
                    return;
                }
                String argClassName = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
                String argClassProxyName = argClassName+"Proxy";

                entityClassNameReference.set(entityClassElement.toString());

                //每一个 entity 生成一个独立的文件

                String entityFullName = entityClassNameReference.get();
                String realGenPackage = guessTablesPackage(entityFullName);
                String entityClassName = entityClassElement.getSimpleName().toString();
                String proxyImplName = entityClassName + "Impl";
                String proxyPackage = guessTablesProxyPackage(fullClassName);


                AptFileCompiler aptFileCompiler = new AptFileCompiler(realGenPackage, entityClassName, proxyImplName,argClassName,argClassProxyName);
                aptFileCompiler.addImports("org.springframework.context.ApplicationContext");
                aptFileCompiler.addImports("org.springframework.stereotype.Component");
                aptFileCompiler.addImports("com.easy.query.api.proxy.entity.select.EntityQueryable");
                aptFileCompiler.addImports("com.easy.query.api.proxy.client.EasyEntityQuery");
                aptFileCompiler.addImports(entityFullName);
                aptFileCompiler.addImports(proxyPackage+"."+argClassProxyName);
                aptFileCompiler.addImports(fullClassName);
                System.out.println("123");
//                AptValueObjectInfo aptValueObjectInfo = new AptValueObjectInfo(entityClassName);
//                do {
//                    fillPropertyAndColumns(aptFileCompiler, aptValueObjectInfo, classElement, ignoreProperties);
//                    classElement = (TypeElement) typeUtils.asElement(classElement.getSuperclass());
//                } while (classElement != null);
//
                String content = buildTablesClass(aptFileCompiler);
                genClass(realGenPackage, proxyImplName, content);

            });
        }
        return false;
    }

    private void genClass(String genPackageName, String className, String genContent) {
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(genPackageName + "." + className);
                writer = sourceFile.openWriter();
                writer.write(genContent);
                writer.flush();
//
//
//            String defaultGenPath = sourceFile.toUri().getPath();
//
//            //真实的生成代码的目录
//            String realPath;
//
//            //用户配置的路径为绝对路径
//            if (isAbsolutePath(basePath)) {
//                realPath = basePath;
//            }
//            //配置的是相对路径，那么则以项目根目录为相对路径
//            else {
//                String projectRootPath = getProjectRootPath(defaultGenPath);
//                realPath = new File(projectRootPath, basePath).getAbsolutePath();
//            }
//
//            //通过在 test/java 目录下执行编译生成的
//            boolean fromTestSource = isFromTestSource(defaultGenPath);
//            if (fromTestSource) {
//                realPath = new File(realPath, "src/test/java").getAbsolutePath();
//            } else {
//                realPath = new File(realPath, "src/main/java").getAbsolutePath();
//            }
//
//            File genJavaFile = new File(realPath, (genPackageName + "." + className).replace(".", "/") + ".java");
//            if (!genJavaFile.getParentFile().exists() && !genJavaFile.getParentFile().mkdirs()) {
//                System.out.println(">>>>>ERROR: can not mkdirs by easy-query processor for: " + genJavaFile.getParentFile());
//                return;
//            }
//
//            writer = new PrintWriter(new FileOutputStream(genJavaFile));
//            writer.write(genContent);
//            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
    private String buildTablesClass(AptFileCompiler aptFileCompiler) {
        return AptCreatorHelper.createProxy(aptFileCompiler);
    }
    private String guessTablesPackage(String entityClassName) {
        StringBuilder guessPackage = new StringBuilder();
        if (!entityClassName.contains(".")) {
            guessPackage.append("impl");// = "table";
        } else {
            guessPackage.append(entityClassName, 0, entityClassName.lastIndexOf(".")).append(".impl");
        }
        return guessPackage.toString();
    }
    private String guessTablesProxyPackage(String entityClassName) {
        StringBuilder guessPackage = new StringBuilder();
        if (!entityClassName.contains(".")) {
            guessPackage.append("proxy");// = "table";
        } else {
            guessPackage.append(entityClassName, 0, entityClassName.lastIndexOf(".")).append(".proxy");
        }
        return guessPackage.toString();
    }
}
