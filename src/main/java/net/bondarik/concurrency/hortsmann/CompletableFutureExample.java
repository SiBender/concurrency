package net.bondarik.concurrency.hortsmann;


import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Запрашиваем пользователя URL
 * В отдельном потоке читаем страницу
 * Затем показываем все ссылки на странице
 */
public class CompletableFutureExample {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UrlValidator urlValidator = new UrlValidator();

    private static final Executor executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        while (true) {
            String url = scanner.nextLine();

            if (!urlValidator.isValid(url)) {
                System.out.println(url + " is not a URL!");
            } else {
                CompletableFuture
                        .supplyAsync(getParseSupplier(url), executor)
                        .thenAccept(System.out::println);

                System.out.println("Page " + url + " in progress");
            }
        }
    }

    private static Supplier<String> getParseSupplier(String url) {
        return () -> {
            Set<String> links = new HashSet<>();

            Document doc = null;
            try {
                doc = Jsoup.connect(url)
                        .data("query", "Java")
                        .userAgent("Mozilla")
                        .timeout(3000)
                        .get();
            } catch (IOException e) {
                return "Error. HTTP status code = " + ((HttpStatusException) e).getStatusCode();
            }

            Elements elements = doc.select("a[href]");
            for (Element element : elements) {
                links.add(element.attr("href"));
            }

            System.out.println("url " + url + " parsing finished " + links.size());
            return String.join("\n", links);
        };
    }

}
