package web;

import javax.ejb.Stateless;
import java.io.File;

    /**
     * Задача №12:
     * Реализовать EJB бин с методом, возвращающим содержимое домашнего каталога пользователя (рекурсивно вглубь).
     * Создать JSP страницу, выводящую в виде дерева результат работы EJB из предыдущего пункта.
     *
     * Запуск проэкта осуществляется через JBoss 18.0.0.Final (wildfly-18.0.0.Final).
     * @version 1.0
     */

    @Stateless
    public class FolderBean {

        /**
         * Метод определяет текущую папку пользователя, вызывает метод, реализующий основную логику программы и является
         * методом, вызываемым в index.jsp.
         * @return  Возвращает список файлов и папок в текущем каталоге пользователя.
         */

        public String startMethod(){
            File userDir = new File(System.getProperty("user.dir"));
            StringBuilder stringBuilder = new StringBuilder();
            return displayATreeOfDirectoriesAndFiles(userDir,stringBuilder);
        }

        /**
         * Метод определяет содержимое домашнего каталога пользователя (рекурсивно вглубь) и структурирует вывод информации.
         * @param userDir Каталог с папками и файлами. Начальное значение - текущий каталог пользователя
         * @param stringBuilder Строковый параметр для вывода информации.
         * @return Возвращает значение stringBuilder-а.
         */
        public String displayATreeOfDirectoriesAndFiles(File userDir,StringBuilder stringBuilder) {

            File[] arrayFromUserDir = userDir.listFiles();

            assert arrayFromUserDir != null;
            stringBuilder.append("<ul>");
            for (int i = 0; i < arrayFromUserDir.length; i++) {

                if (arrayFromUserDir[i].isDirectory()) {
                    stringBuilder.append("<li>" + arrayFromUserDir[i].getName() + "  --DIR" + "</li>");
                    stringBuilder.append("<ul>");
                    displayATreeOfDirectoriesAndFiles(arrayFromUserDir[i], stringBuilder);
                    stringBuilder.append("</ul>");
                } else if (arrayFromUserDir[i].isFile()) {
                    stringBuilder.append("<li>" + arrayFromUserDir[i].getName() + "  --file" + "</li>");
                }
                else stringBuilder.append("another format" );
            }
            stringBuilder.append("</ul>");
            return stringBuilder.toString();
        }
    }







