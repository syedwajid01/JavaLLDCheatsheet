# Design Patterns

This document provides a quick reference to common design patterns used in software engineering. Design patterns are reusable solutions to commonly occurring problems within a given context in software design.

## Table of Contents

1. [Creational Patterns](#creational-patterns)
2. [Structural Patterns](#structural-patterns)
3. [Behavioral Patterns](#behavioral-patterns)

---

## Creational Patterns

### 1. Singleton Pattern
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

### 2. Factory Pattern
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

### 3. Builder Pattern
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

### 4. Prototype Pattern
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

---

## Structural Patterns

### 1. Adapter Pattern
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

### 2. Decorator Pattern
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

### 3. Facade Pattern
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

---

## Behavioral Patterns

### 1. Observer Pattern
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

### 2. Strategy Pattern
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

### 3. Template Method Pattern
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

### 4. Command Pattern
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
