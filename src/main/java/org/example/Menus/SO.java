package org.example.Menus;


import java.io.File;
import java.util.Scanner;


public class SO {


    public static void main (String[] args) {

        File file = new File("Ficha1Ex1");
        int value1 = Integer.parseInt(args[0]);
        int value2 = Integer.parseInt(args[1]);


        System.out.println("Soma dos valores: " + (value1 + value2));
        System.out.println("Subtração dos valores: " + (value1 - value2));
        System.out.println("Multiplicação dos valores: " + (value1 * value2));
        System.out.println("Divisão dos valores: " + (value1 + value2));


    }


}
