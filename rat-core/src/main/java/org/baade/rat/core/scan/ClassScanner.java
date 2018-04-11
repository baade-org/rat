package org.baade.rat.core.scan;

import org.baade.rat.core.SystemProperty;
import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.core.structure.tree.Tree;
import org.baade.rat.core.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * The type Class scanner.
 */
public class ClassScanner {

    private static final Logger log = LoggerFactory.getLogger(ClassScanner.class);

    static {
        log.debug("{}", SystemProperty.toDetailString());
    }

    /**
     * Do scan tree.
     *
     * @param basePackageName the base package name
     * @return the tree
     */
    public static Tree<ClassModel> doScan(String basePackageName) {
        Tree<ClassModel> tree = new Tree<>();
        String classPaths = SystemProperty.JAVA_CLASS_PATH.getValue();
        log.debug("CLASSPATH: {}", classPaths);

        String[] files = classPaths.split(File.pathSeparator);
        for (String filePath : files) {
//            System.out.println(filePath);
            File file = new File(filePath);
            if (ClassUtils.isJar(file.getName())) {
                log.debug("DO SCAN JAR: {}", filePath);
                scanJar(basePackageName, file, tree);
            }

            if (file.isDirectory()) {
                log.debug("DO SCAN DIR: ", filePath);
                scanDir(basePackageName, file, file.getAbsolutePath(), tree);
            }
        }

        return tree;
    }

    private static void scanJar(String basePackageName, File jarFile, Tree<ClassModel> tree) {
        try (JarInputStream inputStream = new JarInputStream(new FileInputStream(jarFile))) {
            JarEntry nextJarEntry = inputStream.getNextJarEntry();
            while (null != nextJarEntry) {
                String classFullName = nextJarEntry.getName();
                // 是class,但不是内部类
                if (ClassUtils.isClass(classFullName) && !ClassUtils.isInnerClass(classFullName)) {
                    classFullName = classFullName.replace("/", ".");
                    add(basePackageName, classFullName, tree);
                }
                nextJarEntry = inputStream.getNextJarEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scanDir(String basePackageName, File dirFile, String baseDirAbsolutePath, Tree<ClassModel> tree) {
        if (!dirFile.isDirectory()) {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                scanDir(basePackageName, file, baseDirAbsolutePath, tree);
            }
            String name = file.getName();
            if (ClassUtils.isClass(name) && !ClassUtils.isInnerClass(name)) {
                String fileAbsolutePath = file.getAbsolutePath();
                String classFullName = fileAbsolutePath //
                        .replace(baseDirAbsolutePath + File.separator, "") //
                        .replace(File.separator, ".");
                add(basePackageName, classFullName, tree);
            }
        }
    }

    private static void add(String basePackageName, String classFullName, Tree<ClassModel> tree) {
        if (classFullName.startsWith(basePackageName)) {
            log.debug("SCAN FOUND: ", classFullName);
            ClassModel cm = new ClassModel(classFullName);
            tree.put(cm, cm.getPath());
        }
    }


}
