package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter1 = new AtomicInteger();
    public static AtomicInteger counter2 = new AtomicInteger();
    public static AtomicInteger counter3 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        new Thread(() -> {
            for (String text : texts) {
                if (isPalindrom(text)) {
                    counting(text.length());
                }
            }

        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isOrderCorrect(text)) {
                    counting(text.length());
                }
            }
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                if (isTheSameLetter(text)) {
                    counting(text.length());
                }
            }
        }).start();
        System.out.println("Красивых слов с длиной 3: " + counter1);
        System.out.println("Красивых слов с длиной 4: " + counter2);
        System.out.println("Красивых слов с длиной 5: " + counter3);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isTheSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOrderCorrect(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrom(String text) {
        int i = 0;
        int j = text.length() - 1;
        while (i < j) {
            while (!Character.isLetter(text.charAt(i)) && i < j) {
                ++i;
            }
            while (!Character.isLetter(text.charAt(j)) && i < j) {
                --j;
            }
            if (Character.toLowerCase(text.charAt(i++)) != Character.toLowerCase(text.charAt(j--))) {
                return false;
            }
        }
        return true;
    }

    public static void counting(int textLength) {
        if (textLength == 3) {
            counter1.getAndIncrement();
        }
        if (textLength == 4) {
            counter2.getAndIncrement();
        }
        if (textLength == 5) {
            counter3.getAndIncrement();
        }
    }
}