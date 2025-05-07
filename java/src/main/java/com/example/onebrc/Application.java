package com.example.onebrc;

import com.example.onebrc.calculate.CalculateAverage;
import com.example.onebrc.calculate.CalculateAverage2;

import java.io.IOException;
import java.util.Objects;

class Application {
    public static void main(String[] args) throws IOException {
        final var url = Application.class.getClassLoader().getResource("measurements.txt");
        if (Objects.isNull(url)) {
            System.out.println("ファイルが存在しません。");
            return;
        }
        final var path = url.getPath();
        System.out.printf("ファイルの場所：%s%n", path);

        long start = System.currentTimeMillis();

        final var cls = new CalculateAverage2();
        final var result = cls.getAverage(path);
        result.keySet().stream().sorted().forEach(key -> {
            // keyでソートして表示する
            final var value = result.get(key);
            System.out.printf("%s=%s¥n", key, value);
        });

        long end = System.currentTimeMillis();
        System.out.println();
        final var diff = end - start;
        System.out.println(diff + "ms");
    }
}
