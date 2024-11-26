package com.app;

import java.util.Scanner;

public class App {
    private static final int[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    public static long expand(int r) {
        long result = 0;
        for (int i = 0; i < E.length; i++) {
            result <<= 1;
            result |= (r >> (32 - E[i])) & 1;
        }
        return result;
    }

    public static void desRound(int rPrev, long k) {
        long expandedR = expand(rPrev);
        System.out.println("Расширенный R(i-1): " + Long.toBinaryString(expandedR));

        long xorResult = expandedR ^ k;
        System.out.println("Результат XOR с Ki: " + Long.toBinaryString(xorResult));

        int[] b = new int[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (int)((xorResult >> (42 - 6 * i)) & 0x3F);
            System.out.printf("B%d: %6s\n", i + 1, Integer.toBinaryString(b[i] | 0x40).substring(1));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите R(i-1) (32-битное целое число): ");
        int rPrev = scanner.nextInt();
        System.out.println("Введите Ki (48-битное целое число): ");
        long k = scanner.nextLong();

        desRound(rPrev, k);
    }
}
