# Design Principles

This document covers fundamental design principles that are crucial for writing clean, maintainable, and scalable code.

## Table of Contents

1. [SOLID Principles](#solid-principles)
2. [OOP Concepts](#oop-concepts)

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
