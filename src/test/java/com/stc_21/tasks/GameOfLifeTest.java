package com.stc_21.tasks;


import org.junit.Test;


import java.io.*;



/**
 * Unit test for simple GameOfLife.
 * Для более наглядного и правдоподобного прохождения теста желательно закоменировать строчку "Thread.sleep(200);"
 * в классах Game и ThreadsGame. Она находится в методе game.(181-номер строки в классе Game,
 * 138-номер строки в классе ThreadsGame)
 */

public class GameOfLifeTest {

    @Test
    public void Measuring_the_runtime_of_a_single_threaded_and_multi_threaded_version_of_a_gameTest() throws IOException, InterruptedException {

        long startTimeGame = System.currentTimeMillis();
        new Game().game(50);
        long stopTimeGame = System.currentTimeMillis();

        long startTimeThreadsGame = System.currentTimeMillis();
        new Game().game(50);
        long stopTimeThreadsGame = System.currentTimeMillis();
        System.out.println("Время выполнения однопоточной версии: " + (stopTimeGame - startTimeGame));
        System.out.println("Время выполнения многопоточной версии: " + (stopTimeThreadsGame - startTimeThreadsGame));

    }
}
