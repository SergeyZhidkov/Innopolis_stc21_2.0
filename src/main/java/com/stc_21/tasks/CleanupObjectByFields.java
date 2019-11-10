package com.stc_21.tasks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Реализация метода  void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput),
 * принимающий любой объект и две коллекции строк. В объекте, посредством reflection реализоано: поля, перечисленные в fieldsToClenup устанавливаются в значение null,
 * или, если поля примитивных типов в их значение по умолчанию. Поля, перечисленные в fieldsToOutput конвертируются в строку и результат преобразования выводится
 * в консоль. Если переданный первым параметром объект является реализацией
 * интерфейса Map, то для списка fieldsToCleanup удаляются ключи из мапы, для fieldsToOutput выводятся в консоль значения,
 * хранящиеся в мапе. При отсутствии в объекте/мапе нужных полей/ключей - программа падает с IllegalArgumentException, оставив объект неизменным.
 */

public class CleanupObjectByFields {


    public static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        if (Map.class.isInstance(object)) {
            cleanupMapBySet(object, fieldsToCleanup);
            printMapValueFromSet(object,fieldsToOutput);
        } else {
            cleanupObjectBySet(object, fieldsToCleanup);
            printFieldsFromSet(object, fieldsToOutput);
        }
    }

    private static void cleanupObjectBySet(Object object, Set<String> fieldsToCleanup) {
        try {
            for (String str :
                    fieldsToCleanup) {
                Field field = object.getClass().getDeclaredField(str);
                if (field.getType().getCanonicalName().equals("boolean")) {
                    field.set(object, false);
                } else if (field.getType().getCanonicalName().equals("char")) {
                    field.set(object, '\u0000');
                } else if (field.getType().isPrimitive()) {
                    if (field.getType().getCanonicalName().equals("long")) {
                        Long megaZero = 0L;
                        field.set(object, megaZero);
                    } else if (field.getType().getCanonicalName().equals("int")) {
                        Integer megaZero = 0;
                        field.set(object, megaZero);
                    } else if (field.getType().getCanonicalName().equals("byte")) {
                        Byte megaZero = 0;
                        field.set(object, megaZero);
                    } else if (field.getType().getCanonicalName().equals("short")) {
                        Short megaZero = 0;
                        field.set(object, megaZero);
                    } else if (field.getType().getCanonicalName().equals("float")) {
                        Float megaZero = 0F;
                        field.set(object, megaZero);
                    } else if (field.getType().getCanonicalName().equals("double")) {
                        Double megaZero = 0D;
                        field.set(object, megaZero);
                    }
                } else field.set(object, null);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    private static void printFieldsFromSet(Object object, Set<String> fieldsToOutput) {
        try {
            for (String str :
                    fieldsToOutput) {
                Field field = object.getClass().getDeclaredField(str);
                if (field.getType().getCanonicalName().equals("boolean")) {
                    String strBoolean = String.valueOf(field.get(object));
                    System.out.println(strBoolean);
                } else if (field.getType().getCanonicalName().equals("char")) {
                    String strChar = String.valueOf(field.get(object));
                    System.out.println(strChar);
                } else if ((field.getType().isPrimitive())) {
                    String strNumber = String.valueOf(field.get(object));
                    System.out.println(strNumber);
                } else if (field.getType().equals(String.class)) {
                    System.out.println(field.get(object));
                } else {
                    if (field.get(object) != null) {
                        System.out.println(field.get(object).toString());
                    } else System.out.println(field.get(object));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void cleanupMapBySet(Object object, Set<String> fieldsToCleanup) {
        try {
            Class workClass = object.getClass();
            Method methodContainsKey = workClass.getMethod("containsKey", Object.class);
            Method methodRemove = workClass.getMethod("remove", Object.class);
            for (String st :
                    fieldsToCleanup) {
                if (!(boolean)methodContainsKey.invoke(object, st)) {
                    throw new IllegalAccessException();
                }
            }
            for (String st :
                    fieldsToCleanup) {
                if (methodContainsKey.invoke(object, st).equals(true)) {
                    methodRemove.invoke(object, st);
                } else throw new IllegalAccessException();
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

        }
    }

    private static void printMapValueFromSet(Object object, Set<String> fieldsToOutput) {
            try {
                Class workClass = object.getClass();
                Method methodContainsKey = workClass.getMethod("containsKey", Object.class);
                Method methodGetValue = workClass.getMethod("get", Object.class);
                for (String st :
                        fieldsToOutput) {
                    if (!(boolean)methodContainsKey.invoke(object, st)) {
                        throw new IllegalAccessException();
                    }
                }
                for (String st :
                        fieldsToOutput) {
                    System.out.println(methodGetValue.invoke(object,st).toString());
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
        }
    }

}