package com.thnkscj.socket.common.util;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reflection utilities
 *
 * @author Thnks_CJ
 */
public class Reflection {
    private static final Logger LOGGER = Logger.getLogger("Reflection");

    /**
     * Register all packets in the package
     *
     * @param packageName the package name they reside in
     */
    public static void registerPackets(String packetName, String packageName) {
        findAllClasses(packageName).forEach(clazz -> {
            if (Packet.class.isAssignableFrom(clazz)) {
                if (PacketRegistry.getPacketName((Class<? extends Packet>) clazz).equals(packetName)) {
                    PacketRegistry.registerPacket(ObjectUtil.unsafeCast(clazz));
                }
            } else {
                LOGGER.error(String.format("\"%s\" is not a subclass of Packet.class", packetName));
            }
        });
    }

    /**
     * Find all classes in the package and its sub-packages
     *
     * @param packageName the package name
     * @return a set of classes
     */
    public static Set<Class<?>> findAllClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        URL packageURL = ClassLoader.getSystemClassLoader().getResource(packageName.replace('.', '/'));

        if (packageURL != null) {
            File directory = new File(packageURL.getFile());
            if (directory.exists()) {
                findClassesInDirectory(directory, packageName, classes);
            }
        }

        return classes;
    }

    /**
     * Recursively find classes in a directory
     *
     * @param directory  the directory to scan
     * @param packageName the package name
     * @param classes    the set of found classes
     */
    private static void findClassesInDirectory(File directory, String packageName, Set<Class<?>> classes) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findClassesInDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    System.err.printf("Class \"%s\" not found%n", packageName + '.' + className);
                }
            }
        }
    }

    /**
     * Find all classes in the package
     *
     * @param packageName the package name they reside in
     * @return a set of classes
     */
    private static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null)
            return Set.of();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    /**
     * Get the class
     *
     * @param className   the class name
     * @param packageName the package name
     * @return the class
     */
    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            LOGGER.error(String.format("Class \"%s\" not found", className));
        }
        return null;
    }
}
