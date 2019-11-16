package com.stc_21.tasks;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

/**
 * Реализация игры американского математика Джона Хортона Конуэйя "Жизнь".
 * Данная реализация представляет собой многопоточную версию игры с визуализацией на базе пакета Swing.
 * Входным параметом является количество поколений, которое задаётся с консоли.
 * Начальная конфигурация поля задаётся случайным образом и записывается в файл start.txt.
 * Конечная конфигурация записывается в файл finish.txt.
 * Завершение программы осуществзяется закрытием игрового окна.
 *
 * @version 1.1
 */

public class ThreadsGame extends JFrame {
    int SIZE_OF_FIELD = 50;
    private int x, y;
    boolean[][] nextGenretion = new boolean[SIZE_OF_FIELD][SIZE_OF_FIELD];
    boolean[][] todayGeneretion = new boolean[SIZE_OF_FIELD][SIZE_OF_FIELD];
    Random random = new Random();

    /**
     * Конструктор класса Game. Используется для отображения основного поля игры.
     */
    public ThreadsGame() {
        setTitle("Game Of Life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(530, 550);
        setLocation(400, 100);
        setVisible(true);
    }

    /**
     * Метод отрисовывает элементы поля.
     *
     * @param g
     */

    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < SIZE_OF_FIELD; x++) {
            for (int y = 0; y < SIZE_OF_FIELD; y++) {
                g.setColor(Color.red);
                if (todayGeneretion[x][y]) {
                    g.drawString("0", x * 10 + 5, y * 10 + 50);
                }
            }
        }
    }

    /**
     * Метод читает данные из файла и заполняет ими массив.
     *
     * @param filePath Путь к файлу для чтения.
     * @return buff Возвращает заполненный массив.
     * @throws IOException Возникает при ошибках Ввода/Вывода.
     */

    public boolean[][] readFromFile(String filePath) throws IOException {
        FileReader file = new FileReader(filePath);
        BufferedReader br = new BufferedReader(file);
        String line;
        boolean[][] buff = new boolean[SIZE_OF_FIELD][SIZE_OF_FIELD];
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] str = line.split(" ");
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                buff[i][j] = Boolean.parseBoolean(str[j]);
            }
            i++;
        }
        return buff;
    }


    /**
     * Заполняет массив случайным образом.
     *
     * @return buff Возвращает заполненный массив.
     */
    public boolean[][] getStartRandomValue() {
        boolean[][] buff = new boolean[SIZE_OF_FIELD][SIZE_OF_FIELD];
        for (int x = 0; x < SIZE_OF_FIELD; x++) {
            for (int y = 0; y < SIZE_OF_FIELD; y++) {
                buff[x][y] = random.nextBoolean();
            }
        }
        return buff;
    }

    /**
     * Записыват данные из переданного массива в указанный файл.
     *
     * @param filePath Путь к файлу для записи.
     * @param bool     Массив, который надо записать в файл.
     * @throws IOException Ошибки Ввода/вывода.
     */
    public void writeToFile(String filePath, boolean[][] bool) throws IOException {
        FileWriter file = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(file);
        for (boolean[] firstCoordinate : bool) {
            for (boolean el : firstCoordinate) {
                bw.write(el + " ");
            }
            bw.write('\n');
        }
        bw.close();
        file.close();
    }


    /**
     * Метод управляет игровым процессом.
     * В методе на каждую строку запускается своя нить.
     *
     * @param generations Количество поколений, заданное с консоли.
     * @throws IOException          Исключение возникает при ошибке Ввода/Вывода.
     * @throws InterruptedException Исключение возникает при работе с потоками.
     * @see class LifeInThreads;
     */
    public void game(int generations) throws IOException, InterruptedException {
        writeToFile("Resources/start.txt", getStartRandomValue());
        todayGeneretion = readFromFile("Resources/start.txt");
        repaint();
        for (int i = 0; i < generations; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                LifeInThreads lifeInThreads = new LifeInThreads(j);
                lifeInThreads.start();
            }
            for (int k = 0; k < SIZE_OF_FIELD; k++) {
                System.arraycopy(nextGenretion[k], 0, todayGeneretion[k], 0, SIZE_OF_FIELD);
            }
            repaint();
            Thread.sleep(200);
            System.out.println("Живёт поколение:" + i);
        }
        writeToFile("Resources/finish.txt", todayGeneretion);
    }

    /**
     *
     */

    public class LifeInThreads extends Thread {
        int numberOfLine;

        /**
         * Конструктор класса LifeInThreads
         *
         * @param numberOfLine Номер сроки для обработки отдельным потоком.
         */

        LifeInThreads(int numberOfLine) {
            this.numberOfLine = numberOfLine;
        }

        /**
         * Метод реализует основную логику игры.
         * В нём определяется в каком состоянии будет находться каждый элемент игрового поля:
         * останется ли он в неизменном состоянии (пустой либо живущий), умрёт ли в нём жизнь или зарадится новая.
         */
        public void run() {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                int numberOfNeighbors = countsNeighbors(numberOfLine, j);
                nextGenretion[numberOfLine][j] = todayGeneretion[numberOfLine][j];
                if (numberOfNeighbors == 3) {
                    nextGenretion[numberOfLine][j] = true;
                }
                if (numberOfNeighbors < 2 || numberOfNeighbors > 3) {
                    nextGenretion[numberOfLine][j] = false;
                }
            }
        }

        /**
         * Метод осуществляет подсчёт количества соседей каждой конкретной клетки поля.
         *
         * @param x Указание координаты ячейки в массиве по оси икс.
         * @param y Указание координаты ячейки в массиве по оси игрик.
         * @return count Возвращает количество соседей каждой конкретной клетки поля.
         */
        public int countsNeighbors(int x, int y) {
            int count = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int neighborsX = x + i;
                    int neighborsY = y + j;
                    if (neighborsX < 0) neighborsX = SIZE_OF_FIELD - 1;
                    if (neighborsY < 0) neighborsY = SIZE_OF_FIELD - 1;
                    if (neighborsX > SIZE_OF_FIELD - 1) neighborsX = 0;
                    if (neighborsY > SIZE_OF_FIELD - 1) neighborsY = 0;
                    if (todayGeneretion[neighborsX][neighborsY]) {
                        count++;
                    }
                }
            }
            if (todayGeneretion[x][y]) {
                count--;
            }
            return count;
        }
    }

}



