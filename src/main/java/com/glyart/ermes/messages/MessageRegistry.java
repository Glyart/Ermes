package com.glyart.ermes.messages;

import com.glyart.ermes.utils.ErmesDataInput;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.HashMap;
import java.util.Map;

public class MessageRegistry {

    private static final Map<Integer, Class<? extends Message>> ID_TO_MESSAGE = new HashMap<>();
    private static final Map<Class<? extends Message>, Integer> MESSAGE_TO_ID = new HashMap<>();

    public static int getMessageID(Class<? extends Message> clazz) {
        return MESSAGE_TO_ID.get(clazz);
    }

    public static Class<? extends Message> getMessageClass(int id) {
        return ID_TO_MESSAGE.get(id);
    }

    public static <T extends Message> T createMessage(ErmesDataInput input) throws Exception {
        int id = input.readInt();
        Class<T> clazz = (Class<T>) getMessageClass(id);
        T message = clazz.getConstructor().newInstance();
        message.read(input);
        return message;
    }

    public static void register(Class<? extends Message> clazz) {
        // We use the hashcode of the Class name as an ID, it could create issues but it seems to work.
        int id = clazz.getName().hashCode();
        if (ID_TO_MESSAGE.containsKey(id))
            throw new RuntimeException("There is already a Message with ID: " + id + ", " + clazz.getName());

        ID_TO_MESSAGE.put(id, clazz);
        MESSAGE_TO_ID.put(clazz, id);
    }

    public static void registerMessages(String packageName) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .acceptPackages(packageName)
                .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesImplementing(Message.class.getName())) {
                if (!classInfo.getPackageName().startsWith(packageName))
                    continue;

                register((Class<? extends Message>) classInfo.loadClass());
            }
        }
    }

}
