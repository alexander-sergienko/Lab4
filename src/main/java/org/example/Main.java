package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<User> users;

        // Чтение данных
        try (Reader reader = new FileReader("books.json")) {
            User visitor = gson.fromJson(reader, User.class);
            users = gson.fromJson(reader, new TypeToken<>() {
            });
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        // Задание 1
        System.out.println("Список пользователей:");
        users.forEach(System.out::println);

        System.out.println();

        // Задание 2
        Set<Book> books = new HashSet<>();
        for (User user : users) {
            books.addAll(user.getFavoriteBooks());
        }

        System.out.println("\nКоличество уникальных книг: " + books.size());
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println();

        // Задание 3
        System.out.println("\nКниги, отсортированные по году издания:");
        List<Book> booksSorted = users.stream()
                .flatMap(user -> user.getFavoriteBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .distinct()
                .toList();

        booksSorted.forEach(System.out::println);


        System.out.println();

        // Задание 4
        List<User> usersLikedJane = new ArrayList<>();

        boolean isJane = booksSorted.stream()
                        .anyMatch(e -> e.getAuthor().equals("Jane Austen"));

        System.out.println("\nПользователи, которым нравятся книги Джейн Остин:");
        System.out.println(isJane);
    }
}

@Getter
class User {

    private final String name;
    private final String surname;
    private final String phone;
    private final Boolean subscribed;
    private List<Book> favoriteBooks;

    public User(String name, String surname, String phone, Boolean subscribed) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + phone + " " + subscribed + " " + favoriteBooks;
    }
}

@Getter
class Book {

    private final String name;
    private final String author;
    private final Integer publishingYear;
    private final String isbn;
    private final String publisher;

    public Book(String name, String author, Integer publishingYear, String isbn, String publisher) {
        this.name = name;
        this.author = author;
        this.publishingYear = publishingYear;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return name + " " + author + " " + publishingYear + " " + isbn + " " + publisher;
    }
}