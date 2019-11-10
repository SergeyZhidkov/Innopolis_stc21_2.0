package com.stc_21.tasks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Тестирование метода Cleanup. В тестах мы проверим поведение метода для полей объекта произвольного класса ExampleClass и для Map.
 */
public class CleanupTest
{
    /**
     * Поля, необходимые для тестирования.
     */
    private ExampleClass exampleClass;
    private Map<String, String> exampleMap;
    private Set<String> fieldsToCleanup;
    private Set<String> fieldsToOutput;

    /**
     * Создание объектов тестирования.
     */

    @Before
    public void setup(){

        exampleClass = new ExampleClass();
        exampleMap = new HashMap<>();
        fieldsToCleanup = new HashSet<>();
        fieldsToOutput = new HashSet<>();
        exampleMap.put("Фёдор Бондарчук","Князь Мышкин");
        exampleMap.put("Анна Букловская","Настасья Филлиповна");
        exampleMap.put("Иван Охлабыстин","Парфён Рогожин");
    }


    /**
     * Тест на проверку обнуления полей объекта exampleClass из списка fieldsToCleanup.
     */

    @Test
    public void cleanupObjectBySetTest()
    {
        fieldsToCleanup.add("bt");
        fieldsToCleanup.add("sh");
        fieldsToCleanup.add("age");
        fieldsToCleanup.add("lg");
        fieldsToCleanup.add("fl");
        fieldsToCleanup.add("dbl");
        fieldsToCleanup.add("ch");
        fieldsToCleanup.add("bool");
        fieldsToCleanup.add("name");
        fieldsToCleanup.add("passport");
        CleanupObjectByFields.cleanup(exampleClass, fieldsToCleanup, fieldsToOutput);
        assertEquals((byte)0,exampleClass.bt);
        assertEquals((short)0,exampleClass.sh);
        assertEquals((int)0,exampleClass.age);
        assertEquals((long) 0,exampleClass.lg);
        assertEquals((double) 0,exampleClass.dbl, (double) 0);
        assertEquals((float) 0,exampleClass.fl,(float) 0);
        assertEquals((char)0,exampleClass.ch);
        assertFalse(exampleClass.bool);
        assertNull(exampleClass.name);
        assertNull( exampleClass.passport );
    }

    /**
     * Тест на проверку вывода в консоль полей объекта exampleClass из списка fieldsToOutput.
     */

    @Test
    public void printFieldsFromSetTest(){
        fieldsToOutput.add("bt");
        fieldsToOutput.add("sh");
        fieldsToOutput.add("age");
        fieldsToOutput.add("lg");
        fieldsToOutput.add("fl");
        fieldsToOutput.add("dbl");
        fieldsToOutput.add("ch");
        fieldsToOutput.add("bool");
        fieldsToOutput.add("name");
        fieldsToOutput.add("passport");
        CleanupObjectByFields.cleanup(exampleClass, fieldsToCleanup, fieldsToOutput);
    }

    /**
     * Тест на проверку удаления полей объекта типа Map по ключам, указанным в fieldsToCleanup.
     */

    @Test
    public void cleanupMapBySetTest(){
        fieldsToCleanup.add("Фёдор Бондарчук");
        int expected = exampleMap.size();
        CleanupObjectByFields.cleanup(exampleMap, fieldsToCleanup, fieldsToOutput);
        int actual = exampleMap.size()+1;
        assertEquals(expected,actual);
    }

    /**
     * Тест на проверку вывода в консоль значений объекта типа Map по ключам, указанным в fieldsToOutput.
     */

    @Test
    public void printMapValueFromSetTest(){
        fieldsToOutput.add("Анна Букловская");
        CleanupObjectByFields.cleanup(exampleMap, fieldsToCleanup, fieldsToOutput);
    }

    /**
     * Тест на проверку вывода исключения IllegalAccessException при отсутствии ключа, указанного в fieldsToCleanup.
     */

    @Test
    public void IllegalAccessExceptionTest(){
        try{
        fieldsToOutput.add("Роман Качанов");
        CleanupObjectByFields.cleanup(exampleMap, fieldsToCleanup, fieldsToOutput);
        }catch (Exception e){
            System.out.println(e);
        }

    }


}
