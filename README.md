# Java LLD Cheat Sheet 🏗️

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Design Patterns](https://img.shields.io/badge/Design_Patterns-15+-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen?style=for-the-badge)

A comprehensive cheat sheet to **revise** Java Low Level Design (LLD) concepts in **less time**. Perfect for system design interviews, code reviews, and building production-ready applications.

> 🎯 Focus: Design Patterns, SOLID Principles, OOP Concepts, and Best Practices  
> ⭐ Leave a star if you find this helpful (contributions welcome!)

## Table of Contents

1. [SOLID Principles](#solid-principles)
2. [Design Patterns](#design-patterns)
   - [Creational Patterns](#creational-patterns)
   - [Structural Patterns](#structural-patterns)
   - [Behavioral Patterns](#behavioral-patterns)
3. [OOP Concepts](#oop-concepts)
4. [Java Essentials for LLD](#java-essentials-for-lld)
5. [Concurrency & Multithreading](#concurrency--multithreading)
6. [Collections Framework](#collections-framework)
7. [Exception Handling](#exception-handling)
8. [Best Practices](#best-practices)
9. [Common LLD Interview Problems](#common-lld-interview-problems)

---

## SOLID Principles

### 1. Single Responsibility Principle (SRP)
**A class should have only one reason to change**

```java
// ❌ Bad: Multiple responsibilities
class User {
    private String name;
    private String email;

    public void save() {
        // Database logic
    }

    public void sendEmail() {
        // Email logic
    }
}

// ✅ Good: Single responsibility
class User {
    private String name;
    private String email;
    // Only user data
}

class UserRepository {
    public void save(User user) {
        // Database logic
    }
}

class EmailService {
    public void sendEmail(User user) {
        // Email logic
    }
}
```

### 2. Open/Closed Principle (OCP)
**Open for extension, closed for modification**

```java
// ❌ Bad: Modifying existing code
class DiscountCalculator {
    public double calculate(String type, double amount) {
        if (type.equals("REGULAR")) return amount * 0.1;
        if (type.equals("PREMIUM")) return amount * 0.2;
        // Adding new type requires modification
        return 0;
    }
}

// ✅ Good: Extensible design
interface DiscountStrategy {
    double calculate(double amount);
}

class RegularDiscount implements DiscountStrategy {
    public double calculate(double amount) {
        return amount * 0.1;
    }
}

class PremiumDiscount implements DiscountStrategy {
    public double calculate(double amount) {
        return amount * 0.2;
    }
}
```

### 3. Liskov Substitution Principle (LSP)
**Derived classes must be substitutable for their base classes**

```java
// ❌ Bad: Violates LSP
class Bird {
    public void fly() { }
}

class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }
}

// ✅ Good: Proper abstraction
interface Bird { }

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void fly() { }
}

class Penguin implements Bird {
    public void swim() { }
}
```

### 4. Interface Segregation Principle (ISP)
**Clients shouldn't be forced to depend on interfaces they don't use**

```java
// ❌ Bad: Fat interface
interface Worker {
    void work();
    void eat();
    void sleep();
}

// ✅ Good: Segregated interfaces
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    public void work() { }
    public void eat() { }
}

class Robot implements Workable {
    public void work() { }
}
```

### 5. Dependency Inversion Principle (DIP)
**Depend on abstractions, not concretions**

```java
// ❌ Bad: Direct dependency
class EmailService {
    public void send(String message) { }
}

class NotificationService {
    private EmailService emailService = new EmailService();

    public void notify(String message) {
        emailService.send(message);
    }
}

// ✅ Good: Dependency injection
interface MessageService {
    void send(String message);
}

class EmailService implements MessageService {
    public void send(String message) { }
}

class NotificationService {
    private MessageService messageService;

    public NotificationService(MessageService service) {
        this.messageService = service;
    }

    public void notify(String message) {
        messageService.send(message);
    }
}
```

---

## Design Patterns

### Creational Patterns

#### 1. Singleton Pattern
**Ensures only one instance of a class exists**

```java
// Thread-safe Singleton with lazy initialization
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {
        // Private constructor
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}

// Enum Singleton (Recommended)
public enum DatabaseConnectionEnum {
    INSTANCE;

    public void connect() {
        // Connection logic
    }
}
```

#### 2. Factory Pattern
**Creates objects without specifying exact classes**

```java
// Product interface
interface Vehicle {
    void drive();
}

// Concrete products
class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving a car");
    }
}

class Bike implements Vehicle {
    public void drive() {
        System.out.println("Riding a bike");
    }
}

// Factory
class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "bike":
                return new Bike();
            default:
                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

// Usage
Vehicle vehicle = VehicleFactory.createVehicle("car");
vehicle.drive();
```

#### 3. Builder Pattern
**Constructs complex objects step by step**

```java
public class User {
    private final String firstName;  // Required
    private final String lastName;   // Required
    private final int age;          // Optional
    private final String phone;     // Optional
    private final String address;   // Optional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

// Usage
User user = new User.UserBuilder("John", "Doe")
    .age(30)
    .phone("123-456-7890")
    .build();
```

#### 4. Prototype Pattern
**Creates objects by cloning existing instances**

```java
public abstract class Shape implements Cloneable {
    protected String type;

    public abstract void draw();

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

class Circle extends Shape {
    public Circle() {
        type = "Circle";
    }

    public void draw() {
        System.out.println("Drawing Circle");
    }
}

// Usage
Circle original = new Circle();
Circle cloned = (Circle) original.clone();
```

### Structural Patterns

#### 1. Adapter Pattern
**Allows incompatible interfaces to work together**

```java
// Target interface
interface MediaPlayer {
    void play(String filename);
}

// Adaptee
class Mp3Player {
    public void playMp3(String filename) {
        System.out.println("Playing mp3: " + filename);
    }
}

// Adapter
class MediaAdapter implements MediaPlayer {
    private Mp3Player mp3Player;

    public MediaAdapter() {
        this.mp3Player = new Mp3Player();
    }

    public void play(String filename) {
        mp3Player.playMp3(filename);
    }
}
```

#### 2. Decorator Pattern
**Adds new functionality to objects dynamically**

```java
// Component
interface Coffee {
    double getCost();
    String getDescription();
}

// Concrete Component
class SimpleCoffee implements Coffee {
    public double getCost() {
        return 2.0;
    }

    public String getDescription() {
        return "Simple Coffee";
    }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return coffee.getCost() + 0.5;
    }

    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }
}

// Usage
Coffee coffee = new MilkDecorator(new SimpleCoffee());
System.out.println(coffee.getDescription() + " $" + coffee.getCost());
```

#### 3. Facade Pattern
**Provides simplified interface to complex subsystem**

```java
// Complex subsystem
class CPU {
    public void start() { }
    public void execute() { }
    public void shutdown() { }
}

class Memory {
    public void load() { }
    public void clear() { }
}

class HardDrive {
    public void read() { }
    public void write() { }
}

// Facade
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;

    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }

    public void start() {
        cpu.start();
        memory.load();
        hardDrive.read();
        cpu.execute();
    }

    public void shutdown() {
        cpu.shutdown();
        memory.clear();
    }
}
```

### Behavioral Patterns

#### 1. Observer Pattern
**Defines one-to-many dependency between objects**

```java
// Subject
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Observer
interface Observer {
    void update(String message);
}

// Concrete Subject
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}

// Concrete Observer
class NewsChannel implements Observer {
    private String name;

    public NewsChannel(String name) {
        this.name = name;
    }

    public void update(String news) {
        System.out.println(name + " received: " + news);
    }
}
```

#### 2. Strategy Pattern
**Defines family of algorithms and makes them interchangeable**

```java
// Strategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal");
    }
}

// Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}
```

#### 3. Template Method Pattern
**Defines skeleton of algorithm in base class**

```java
abstract class DataProcessor {
    // Template method
    public final void process() {
        readData();
        processData();
        saveData();
    }

    abstract void readData();
    abstract void processData();

    // Hook method (optional override)
    void saveData() {
        System.out.println("Saving to default storage");
    }
}

class CSVDataProcessor extends DataProcessor {
    void readData() {
        System.out.println("Reading CSV file");
    }

    void processData() {
        System.out.println("Processing CSV data");
    }
}
```

#### 4. Command Pattern
**Encapsulates requests as objects**

```java
// Command interface
interface Command {
    void execute();
    void undo();
}

// Receiver
class Light {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

// Concrete Commands
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

// Invoker
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }

    public void pressUndo() {
        command.undo();
    }
}
```

---

## OOP Concepts

### Encapsulation
```java
public class BankAccount {
    private double balance;  // Private field

    // Public methods to access/modify
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

### Inheritance
```java
// Base class
public class Vehicle {
    protected String brand;
    protected int year;

    public void start() {
        System.out.println("Vehicle starting");
    }
}

// Derived class
public class Car extends Vehicle {
    private int numberOfDoors;

    @Override
    public void start() {
        System.out.println("Car starting with key");
    }

    public void honk() {
        System.out.println("Beep beep!");
    }
}
```

### Polymorphism
```java
// Compile-time (Method Overloading)
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }
}

// Runtime (Method Overriding)
interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}
```

### Abstraction
```java
// Abstract class
public abstract class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Abstract method
    public abstract double calculateSalary();

    // Concrete method
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Salary: " + calculateSalary());
    }
}

// Concrete implementation
public class Developer extends Employee {
    private double bonus;

    public Developer(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
}
```

---

## Java Essentials for LLD

### Interfaces vs Abstract Classes

```java
// Interface (Java 8+)
interface Flyable {
    // Public static final by default
    int MAX_ALTITUDE = 10000;

    // Abstract method
    void fly();

    // Default method (Java 8+)
    default void glide() {
        System.out.println("Gliding...");
    }

    // Static method (Java 8+)
    static void checkWeather() {
        System.out.println("Checking weather...");
    }
}

// Abstract class
abstract class Animal {
    protected String name;

    // Constructor
    public Animal(String name) {
        this.name = name;
    }

    // Abstract method
    abstract void makeSound();

    // Concrete method
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}
```

### Enums
```java
public enum OrderStatus {
    PENDING("Order is pending"),
    PROCESSING("Order is being processed"),
    SHIPPED("Order has been shipped"),
    DELIVERED("Order has been delivered"),
    CANCELLED("Order has been cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinal() {
        return this == DELIVERED || this == CANCELLED;
    }
}

// Usage
OrderStatus status = OrderStatus.PENDING;
System.out.println(status.getDescription());
```

### Generics
```java
// Generic class
public class Box<T> {
    private T content;

    public void set(T content) {
        this.content = content;
    }

    public T get() {
        return content;
    }
}

// Generic method
public class Utils {
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

// Bounded generics
public class NumberBox<T extends Number> {
    private T number;

    public double getDoubleValue() {
        return number.doubleValue();
    }
}

// Wildcards
public void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}

public void addNumbers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
}
```

### Functional Interfaces & Lambdas
```java
// Functional interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// Lambda expressions
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

// Built-in functional interfaces
Predicate<String> isEmpty = s -> s.isEmpty();
Function<String, Integer> strLength = String::length;
Consumer<String> printer = System.out::println;
Supplier<Double> randomSupplier = Math::random;

// Stream API
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.stream()
    .filter(name -> name.startsWith("A"))
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

---

## Concurrency & Multithreading

### Thread Creation
```java
// Method 1: Extending Thread
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

// Method 2: Implementing Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

// Method 3: Lambda
Runnable task = () -> System.out.println("Lambda task");

// Usage
MyThread thread1 = new MyThread();
thread1.start();

Thread thread2 = new Thread(new MyRunnable());
thread2.start();

Thread thread3 = new Thread(task);
thread3.start();
```

### Synchronization
```java
public class Counter {
    private int count = 0;
    private final Object lock = new Object();

    // Method synchronization
    public synchronized void increment() {
        count++;
    }

    // Block synchronization
    public void decrement() {
        synchronized (lock) {
            count--;
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}
```

### Thread-Safe Collections
```java
// Thread-safe alternatives
Map<String, String> concurrentMap = new ConcurrentHashMap<>();
List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
Queue<String> concurrentQueue = new ConcurrentLinkedQueue<>();
BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

// CopyOnWriteArrayList - Good for read-heavy scenarios
List<String> cowList = new CopyOnWriteArrayList<>();
```

### ExecutorService
```java
// Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
Future<Integer> future = executor.submit(() -> {
    // Some computation
    return 42;
});

// Get result
try {
    Integer result = future.get(5, TimeUnit.SECONDS);
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    e.printStackTrace();
}

// Shutdown
executor.shutdown();
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}
```

### Locks
```java
import java.util.concurrent.locks.*;

public class SafeCounter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Using ReentrantLock
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    // Using ReadWriteLock
    public void write(int value) {
        rwLock.writeLock().lock();
        try {
            count = value;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int read() {
        rwLock.readLock().lock();
        try {
            return count;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
```

---

## Collections Framework

### Collection Hierarchy
```
Collection
├── List
│   ├── ArrayList       // Dynamic array, O(1) access
│   ├── LinkedList      // Doubly-linked list, O(1) add/remove at ends
│   └── Vector          // Synchronized ArrayList (legacy)
├── Set
│   ├── HashSet         // No order, O(1) operations
│   ├── LinkedHashSet   // Maintains insertion order
│   └── TreeSet         // Sorted, O(log n) operations
└── Queue
    ├── PriorityQueue   // Heap-based priority queue
    ├── ArrayDeque      // Resizable array deque
    └── LinkedList      // Also implements Queue

Map (separate hierarchy)
├── HashMap             // No order, O(1) operations
├── LinkedHashMap       // Maintains insertion order
├── TreeMap             // Sorted by keys, O(log n)
└── Hashtable          // Synchronized HashMap (legacy)
```

### Common Operations
```java
// List operations
List<String> list = new ArrayList<>();
list.add("item");                    // O(1) amortized
list.add(0, "first");                // O(n)
list.get(0);                         // O(1)
list.remove(0);                      // O(n)
list.contains("item");               // O(n)
Collections.sort(list);              // O(n log n)

// Set operations
Set<String> set = new HashSet<>();
set.add("item");                     // O(1)
set.remove("item");                  // O(1)
set.contains("item");                // O(1)

// Map operations
Map<String, Integer> map = new HashMap<>();
map.put("key", 1);                   // O(1)
map.get("key");                      // O(1)
map.remove("key");                   // O(1)
map.containsKey("key");              // O(1)
map.getOrDefault("key", 0);          // O(1)
map.putIfAbsent("key", 1);           // O(1)

// Queue operations
Queue<String> queue = new LinkedList<>();
queue.offer("item");                 // O(1)
queue.poll();                        // O(1)
queue.peek();                        // O(1)

// Deque operations
Deque<String> deque = new ArrayDeque<>();
deque.addFirst("first");             // O(1)
deque.addLast("last");               // O(1)
deque.removeFirst();                 // O(1)
deque.removeLast();                  // O(1)
```

---

## Exception Handling

### Exception Hierarchy
```
Throwable
├── Error (Don't catch)
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   └── IndexOutOfBoundsException
    └── IOException (Checked)
        ├── FileNotFoundException
        └── SocketException
```

### Best Practices
```java
// Custom exception
public class InsufficientFundsException extends Exception {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds: " + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// Try-with-resources
public String readFile(String path) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        return reader.lines()
            .collect(Collectors.joining("\n"));
    }
}

// Multiple catch blocks
public void processData(String data) {
    try {
        int value = Integer.parseInt(data);
        int result = 10 / value;
        System.out.println(result);
    } catch (NumberFormatException e) {
        System.err.println("Invalid number format");
    } catch (ArithmeticException e) {
        System.err.println("Division by zero");
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
    } finally {
        System.out.println("Cleanup code");
    }
}

// Rethrowing with wrapping
public void processFile(String path) throws ProcessingException {
    try {
        // File processing logic
    } catch (IOException e) {
        throw new ProcessingException("Failed to process file", e);
    }
}
```

---

## Best Practices

### 1. Immutability
```java
public final class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public ImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>(hobbies);  // Defensive copy
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return new ArrayList<>(hobbies);  // Return copy
    }
}
```

### 2. Equals and HashCode
```java
public class Person {
    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return age == person.age && 
               Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

### 3. Composition over Inheritance
```java
// Instead of inheritance
class Car extends Engine { }  // ❌

// Use composition
class Car {
    private Engine engine;     // ✅

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        engine.start();
    }
}
```

### 4. Defensive Programming
```java
public class BankAccount {
    private double balance;

    public void withdraw(double amount) {
        // Validate inputs
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(amount);
        }

        balance -= amount;
    }

    public void setEmail(String email) {
        // Null check
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }
}
```

### 5. Resource Management
```java
// Always use try-with-resources for AutoCloseable resources
try (FileInputStream fis = new FileInputStream("file.txt");
     BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

    return br.lines()
        .collect(Collectors.toList());

} catch (IOException e) {
    log.error("Failed to read file", e);
    throw new ProcessingException("File reading failed", e);
}
```

---

## Common LLD Interview Problems

### 1. Design Parking Lot System
```java
// Key classes
class ParkingLot {
    private List<Level> levels;
    private int capacity;

    public Ticket parkVehicle(Vehicle vehicle) { }
    public void unparkVehicle(Ticket ticket) { }
    public boolean isFull() { }
}

class Level {
    private List<ParkingSpot> spots;
    private int floor;

    public boolean parkVehicle(Vehicle vehicle) { }
}

abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;
}

class ParkingSpot {
    private boolean isOccupied;
    private Vehicle vehicle;
    private SpotType type;
}
```

### 2. Design Library Management System
```java
// Key interfaces and classes
interface Searchable {
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByISBN(String isbn);
}

class Library implements Searchable {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private Map<String, Loan> activeLoans;

    public void issueBook(String memberId, String bookId) { }
    public void returnBook(String loanId) { }
}

class Book {
    private String isbn;
    private String title;
    private List<Author> authors;
    private BookStatus status;
}

class Member {
    private String memberId;
    private List<Loan> loanHistory;
    private int booksIssued;

    public boolean canBorrow() {
        return booksIssued < MAX_BOOKS_ALLOWED;
    }
}
```

### 3. Design ATM System
```java
// State Pattern for ATM
interface ATMState {
    void insertCard(ATM atm, Card card);
    void authenticatePin(ATM atm, int pin);
    void selectOperation(ATM atm, Operation operation);
    void cashWithdrawal(ATM atm, double amount);
    void displayBalance(ATM atm);
    void returnCard(ATM atm);
}

class IdleState implements ATMState {
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        atm.changeState(new HasCardState());
    }
}

class ATM {
    private ATMState currentState;
    private Card currentCard;
    private CashDispenser cashDispenser;

    public ATM() {
        currentState = new IdleState();
    }

    public void changeState(ATMState newState) {
        this.currentState = newState;
    }
}
```

### 4. Design Elevator System
```java
// Strategy Pattern for scheduling
interface ElevatorScheduler {
    Elevator selectElevator(Request request, List<Elevator> elevators);
}

class FCFSScheduler implements ElevatorScheduler {
    public Elevator selectElevator(Request request, List<Elevator> elevators) {
        // First Come First Serve logic
    }
}

class Elevator {
    private int currentFloor;
    private Direction direction;
    private Queue<Request> requests;
    private ElevatorState state;

    public void addRequest(Request request) { }
    public void processRequests() { }
}

class ElevatorController {
    private List<Elevator> elevators;
    private ElevatorScheduler scheduler;

    public void handleRequest(Request request) {
        Elevator elevator = scheduler.selectElevator(request, elevators);
        elevator.addRequest(request);
    }
}
```

### 5. Design Online Shopping System
```java
// Key components
class Product {
    private String productId;
    private String name;
    private double price;
    private int quantity;
}

class ShoppingCart {
    private Map<Product, Integer> items;

    public void addItem(Product product, int quantity) { }
    public void removeItem(Product product) { }
    public double calculateTotal() { }
}

class Order {
    private String orderId;
    private List<OrderItem> items;
    private OrderStatus status;
    private PaymentInfo payment;
    private ShippingInfo shipping;

    public void placeOrder() { }
    public void cancelOrder() { }
}

// Observer Pattern for notifications
interface OrderObserver {
    void update(Order order);
}

class EmailNotification implements OrderObserver {
    public void update(Order order) {
        // Send email about order status
    }
}
```

---

## Quick Reference

### Time Complexities
| Data Structure | Access | Search | Insert | Delete |
|---------------|--------|--------|--------|--------|
| ArrayList | O(1) | O(n) | O(1)* | O(n) |
| LinkedList | O(n) | O(n) | O(1) | O(1) |
| HashMap | - | O(1) | O(1) | O(1) |
| TreeMap | - | O(log n) | O(log n) | O(log n) |
| HashSet | - | O(1) | O(1) | O(1) |
| TreeSet | - | O(log n) | O(log n) | O(log n) |
| PriorityQueue | - | O(n) | O(log n) | O(log n) |

*Amortized time complexity

### When to Use What?

**Creational Patterns:**
- **Singleton**: Database connections, Logger, Configuration
- **Factory**: When object creation logic is complex
- **Builder**: Objects with many optional parameters
- **Prototype**: When object creation is expensive

**Structural Patterns:**
- **Adapter**: Integrating third-party libraries
- **Decorator**: Adding features dynamically
- **Facade**: Simplifying complex APIs

**Behavioral Patterns:**
- **Observer**: Event handling, MVC
- **Strategy**: Multiple algorithms for same task
- **Template Method**: Common algorithm structure
- **Command**: Undo/Redo, Queuing operations

---

## Contributing

Feel free to contribute by:
1. Adding more design patterns
2. Including more real-world examples
3. Adding common interview questions
4. Improving explanations

---

Made with ❤️ for Java developers preparing for LLD interviews.

**Remember**: Good design is not about using all patterns, but knowing when and where to use them appropriately.
