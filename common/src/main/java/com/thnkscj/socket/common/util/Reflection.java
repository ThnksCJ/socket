package com.thnkscj.socket.common.util;

import com.thnkscj.socket.common.packet.PacketRegistry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class Reflection {
    public static void registerPackets(String packetName, String packageName) {
        findAllClassesUsingClassLoader(packageName).forEach(clazz -> {
            if (clazz.getSimpleName().equals(packetName)) {
                try {
                    PacketRegistry.registerPacket(clazz);
                } catch (Exception e) {
                    // handle the exception
                }
            }
        });
    }

    private static Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
