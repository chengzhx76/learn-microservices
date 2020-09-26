package com.github.chengzhx76.jdk.loader;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @Description
 * Java 类的热替换 —— 概念、设计与实现
 * https://developer.ibm.com/zh/articles/j-lo-hotswapcls/
 * @Author admin
 * @Date 2020/9/26 10:31
 * @Version 3.0
 */
public class CustomizeClassLoader extends ClassLoader {
    private String basedir;
    private Set<String> dynaClsNames;


    public CustomizeClassLoader(String basedir, String[] names) throws IOException {
        super(null);
        this.basedir = basedir;
        this.dynaClsNames = new HashSet<>();
        load(names);

    }

    private void load(String[] names) throws IOException {
        for (String name : names) {
            if (name.endsWith(".class")) {
                loadClassByMe(names, null);
            } else if (name.endsWith(".jar")) {
                loadJarDirectly(name);
            }
        }
    }
    private void loadClassByMe(String[] names, JarFile jarFile) throws IOException {
        for (String name : names) {
            loadClsDirectly(name, jarFile);
            dynaClsNames.add(name);
        }
    }

    private void loadJarDirectly(String jarName) throws IOException {
        StringBuffer sb = new StringBuffer(basedir);
        sb.append(File.separator).append(jarName);
        JarFile jar = new JarFile(sb.toString());
        Enumeration<JarEntry> entries = jar.entries();
        List<String> clsList = new ArrayList<>();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String clsName = entry.getName();
            if (clsName.endsWith(".class")) {
                clsList.add(clsName);
            }
        }
        String[] names =clsList.toArray(new String[clsList.size()]);
        loadClassByMe(names, jar);
    }

    private Class<?> loadClsDirectly(String clsName, JarFile jarFile) throws IOException {
        String name = clsName.substring(0, clsName.lastIndexOf(".class"));
        String clsNameToPkgPath = name.replace('.', File.separatorChar);
        Class<?> cls = null;
        if (jarFile != null) {
            ZipEntry entry = jarFile.getEntry(clsName);
            if (entry != null) {
                InputStream clsStream = jarFile.getInputStream(entry);
                cls = instantiateClass(name, clsStream, entry.getSize());
            }
        } else {
            StringBuffer sb = new StringBuffer(basedir);
            sb.append(File.separator).append(clsNameToPkgPath).append(".class");
            File clsFile = new File(sb.toString());
            cls = instantiateClass(name, new FileInputStream(clsFile), clsFile.length());
        }
        return cls;
    }

    private Class<?> instantiateClass(String name, InputStream clsStream, long len) throws IOException {
        byte[] raw = new byte[(int) len];
        clsStream.read(raw);
        clsStream.close();
        return defineClass(name, raw, 0, raw.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> cls = findLoadedClass(name);
        if (!this.dynaClsNames.contains(name) && cls == null) {
            cls = getSystemClassLoader().loadClass(name);
        }
        if (cls == null) {
            throw new ClassNotFoundException(name);
        }
        if (resolve) {
            resolveClass(cls);
        }
        return super.loadClass(name, resolve);
    }
}
