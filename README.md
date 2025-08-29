# Java LLD Cheat Sheet 🏗️

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Design Patterns](https://img.shields.io/badge/Design_Patterns-15+-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen?style=for-the-badge)

A comprehensive cheat sheet to **revise** Java Low Level Design (LLD) concepts in **less time**. Perfect for system design interviews, code reviews, and building production-ready applications.

> 🎯 Focus: Core Java concepts, best practices, and quick reference for LLD interviews.
> ⭐ Leave a star if you find this helpful (contributions welcome!)

## Table of Contents

1. [Java Core Concepts for LLD](#java-core-concepts-for-lld)
2. [Concurrency & Multithreading](#concurrency--multithreading)
3. [Exception Handling](#exception-handling)
4. [Best Practices](#best-practices)
5. [Java Tips & Gotchas](#java-tips--gotchas)
6. [Further Reading](#further-reading)

---

## Further Reading

- **[LLD Interview Problems Examples](LLD_Interview_Problems)**
- **[Design Principles (SOLID, OOP)](DESIGN_PRINCIPLES.md)**
- **[Design Patterns](DESIGN_PATTERNS.md)**
- **[Refactoring Guru](https://refactoring.guru/)**: A great resource for in-depth explanations of design patterns.
- **[Awesome Low-Level Design](https://github.com/ashishps1/awesome-low-level-design?tab=readme-ov-file)**: A curated list of LLD interview questions and resources.

---

## Java Core Concepts for LLD

This section covers the essential Java language features that are fundamental for building any application and are frequently used in LLD interviews.

### Basic Data Types

| Primitive Type | Wrapper Class | Size      | Description                   |
|----------------|---------------|-----------|-------------------------------|
| `byte`         | `Byte`        | 1 byte    | Signed integer                |
| `short`        | `Short`       | 2 bytes   | Signed integer                |
| `int`          | `Integer`     | 4 bytes   | Signed integer                |
| `long`         | `Long`        | 8 bytes   | Signed integer                |
| `float`        | `Float`       | 4 bytes   | Single-precision float        |
| `double`       | `Double`      | 8 bytes   | Double-precision float        |
| `char`         | `Character`   | 2 bytes   | Unicode character             |
| `boolean`      | `Boolean`     | 1 bit     | `true` or `false`             |

### String Operations

```java
String s = "hello world";

// Essential Methods
s.length();            // 11
s.substring(0, 5);     // "hello"
s.charAt(0);           // 'h'
s.indexOf("world");    // 6
s.startsWith("hello"); // true
s.endsWith("world");   // true
s.toUpperCase();       // "HELLO WORLD"
s.toLowerCase();       // "hello world"
s.trim();              // Removes leading/trailing whitespace
s.replace('l', 'p');   // "heppo worpd"
s.split(" ");          // ["hello", "world"]
String.join("-", "a", "b", "c"); // "a-b-c"

// StringBuilder for efficient concatenation
StringBuilder sb = new StringBuilder();
sb.append("Hello, ");
sb.append("world!");
String result = sb.toString(); // "Hello, world!"
```

### Collections Framework

#### Collection Hierarchy
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

#### Common Operations
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

// PriorityQueue operations (Min-Heap by default)
Queue<Integer> minHeap = new PriorityQueue<>();
minHeap.add(3);                      // O(log n)
minHeap.add(1);
minHeap.peek();                      // O(1) -> 1
minHeap.poll();                      // O(log n) -> 1

// Max-Heap implementation
Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
maxHeap.add(1);
maxHeap.add(3);
maxHeap.peek();                      // O(1) -> 3
maxHeap.poll();                      // O(log n) -> 3
```

### Custom Sorting

#### Using `Comparator`
For custom sorting logic, especially for classes you don't own or for multiple sorting strategies.

```java
class Player {
    String name;
    int score;
    // constructor, getters
}

// Sort by score descending, then by name ascending
Comparator<Player> scoreComparator = (p1, p2) -> {
    if (p1.getScore() != p2.getScore()) {
        return Integer.compare(p2.getScore(), p1.getScore()); // Descending score
    }
    return p1.getName().compareTo(p2.getName()); // Ascending name
};

List<Player> players = new ArrayList<>();
// ... add players
players.sort(scoreComparator);

// Or with a PriorityQueue
PriorityQueue<Player> playerQueue = new PriorityQueue<>(scoreComparator);
```

#### Using `Comparable`
For defining a natural ordering for a class.

```java
class Student implements Comparable<Student> {
    String name;
    int id;

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id); // Natural ordering by ID
    }
}

List<Student> students = new ArrayList<>();
// ... add students
Collections.sort(students); // Sorts by ID
```

### Math Essentials

```java
// Common Math functions
Math.max(10, 20);       // 20
Math.min(10, 20);       // 10
Math.abs(-5);           // 5
Math.pow(2, 3);         // 8.0
Math.sqrt(16);          // 4.0
Math.ceil(2.3);         // 3.0
Math.floor(2.3);        // 2.0
Math.round(2.7);        // 3
```

### Useful Utility Methods

```java
// Arrays utility
int[] arr = {3, 1, 4, 1, 5};
Arrays.sort(arr);                       // Sorts in-place
int index = Arrays.binarySearch(arr, 4); // 3 (index of 4)
int[] copy = Arrays.copyOf(arr, 3);      // [1, 1, 3]

// Collections utility
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4));
Collections.sort(list);                 // Sorts in-place
Collections.reverse(list);              // Reverses in-place
Collections.shuffle(list);              // Shuffles in-place
int max = Collections.max(list);        // 4
```

### Binary Search

```java
// For arrays (must be sorted)
int[] sortedArr = {1, 2, 4, 7, 9};
int index = Arrays.binarySearch(sortedArr, 4); // 2
int insertionPoint = Arrays.binarySearch(sortedArr, 3); // -3 (-(insertion point) - 1)

// For lists (must be sorted)
List<Integer> sortedList = Arrays.asList(1, 2, 4, 7, 9);
int listIndex = Collections.binarySearch(sortedList, 7); // 3
```

### Taking Multiple Inputs (Scanner)

```java
import java.util.Scanner;

public class InputReader {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read a single integer
        int n = sc.nextInt();

        // Read two integers
        int a = sc.nextInt();
        int b = sc.nextInt();

        // Read a string
        String s = sc.next();

        // Read a full line
        sc.nextLine(); // Consume the newline character
        String line = sc.nextLine();

        // Read an array of integers
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        
        sc.close();
    }
}
```

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

A brief overview of creating and managing threads in Java, which is crucial for designing applications that can handle multiple tasks simultaneously.

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

## Exception Handling

Understanding how to properly handle errors and exceptional cases is a key aspect of robust software design.

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

### Exception Handling Best Practices

- **Be specific**: Catch specific exceptions (`FileNotFoundException`) instead of generic `Exception`.
  ```java
  try {
      // Code that might throw exceptions
  } catch (FileNotFoundException e) {
      // Handle missing file
  } catch (IOException e) {
      // Handle other I/O errors
  }
  ```

- **Don't swallow exceptions**: Avoid empty `catch` blocks. At a minimum, log the exception.
  ```java
  // ❌ Bad: Swallowing exception
  try {
      // ...
  } catch (Exception e) {
      // This is bad practice!
  }

  // ✅ Good: Logging exception
  try {
      // ...
  } catch (Exception e) {
      log.error("An error occurred", e);
  }
  ```

- **Use `try-with-resources`**: For all resources that implement `AutoCloseable`.
  ```java
  try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
      // Work with the reader
  } catch (IOException e) {
      // Handle exception
  }
  ```

- **Throw exceptions with descriptive messages**:
  ```java
  if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive, but was: " + amount);
  }
  ```

- **Use custom exceptions**: For application-specific errors.
  ```java
  public class InsufficientFundsException extends Exception {
      public InsufficientFundsException(String message) {
          super(message);
      }
  }
  
  // Throwing the custom exception
  if (balance < withdrawalAmount) {
      throw new InsufficientFundsException("Insufficient funds for withdrawal.");
  }
  ```

---

## Best Practices

### 1. Immutability
**Create objects whose state cannot be changed after construction.** This simplifies reasoning about your code, especially in concurrent environments.

```java
// - Class is final to prevent subclassing
// - Fields are private and final
// - No setter methods
// - Defensive copies are used for mutable fields
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
**Override `hashCode()` whenever you override `equals()` to ensure that equal objects have the same hash code.** This is critical for the correct functioning of hash-based collections like `HashMap` and `HashSet`.

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
**Favor composition (HAS-A relationship) over inheritance (IS-A relationship) to build complex functionality.** It offers more flexibility and avoids the tight coupling and rigidity of deep inheritance hierarchies.

```java
// Instead of a Car "IS-A" an Engine, which is illogical
class Car extends Engine { }  // ❌

// A Car "HAS-A" an Engine, which is logical and flexible
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
**Validate inputs and check for nulls to make your code more robust and prevent unexpected errors.**

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
**Always use `try-with-resources` for any object that implements `AutoCloseable` (e.g., streams, connections).** This ensures that resources are automatically and safely closed, even if exceptions occur.

```java
// The FileInputStream and BufferedReader are automatically closed
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

---

## Java Tips & Gotchas

### 1. `==` vs `.equals()`
- **`==`**: Compares object references (memory addresses).
- **`.equals()`**: Compares object values. Must be properly overridden (e.g., with `hashCode()`).

```java
String a = new String("hello");
String b = new String("hello");
System.out.println(a == b);         // false
System.out.println(a.equals(b));    // true
```

### 2. String Concatenation
- Use `StringBuilder` for concatenating strings in loops for better performance.

```java
// ❌ Inefficient
String result = "";
for (String s : list) {
    result += s;
}

// ✅ Efficient
StringBuilder sb = new StringBuilder();
for (String s : list) {
    sb.append(s);
}
String result = sb.toString();
```

### 3. Programming to Interfaces
- Declare variables with the interface type for flexibility.

```java
// ❌ Less flexible
ArrayList<String> list = new ArrayList<>();

// ✅ More flexible
List<String> list = new ArrayList<>();
list = new LinkedList<>(); // Can be easily swapped
```

### 4. `equals()` and `hashCode()` Contract
- If you override `.equals()`, you **must** override `hashCode()`.
- Equal objects must have equal hash codes.

### 5. Integer Division
- Be mindful of integer division, which truncates decimals.

```java
int result = 5 / 2; // result is 2, not 2.5
double result = 5.0 / 2; // result is 2.5
```
