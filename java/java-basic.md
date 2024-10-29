### 1. **Data Types**

- **Primitive Types**:
    - `int`: Integer values (e.g., `int number = 10;`)
    - `double`: Decimal values (e.g., `double price = 19.99;`)
    - `char`: Single characters (e.g., `char letter = 'A';`)
    - `boolean`: True/false values (e.g., `boolean isTrue = true;`)
- **Reference Types**:
    - `String`: A sequence of characters (e.g., `String name = "John";`)
    - Arrays: A collection of elements (e.g., `int[] numbers = {1, 2, 3};`)

### 2. **Control Structures**

- **Conditional Statements**:
    
    ```java
    if (condition) {
        // code block
    } else if (anotherCondition) {
        // code block
    } else {
        // code block
    }
    ```
    
- **Switch Statement**:
    
    ```java
    switch (variable) {
        case value1:
            // code block
            break;
        case value2:
            // code block
            break;
        default:
            // code block
    }
    ```
    
- **Loops**:
    - **For Loop**:
        
        ```java
        for (int i = 0; i < 10; i++) {
            // code block
        }
        ```
        
    - **While Loop**:
        
        ```java
        while (condition) {
            // code block
        }
        ```
        
    - **Do-While Loop**:
        
        ```java
        do {
            // code block
        } while (condition);
        ```
        

### 3. **Methods**

- Defining and calling methods:
    
    ```java
    public returnType methodName(parameters) {
        // code block
    }
    ```
    
- Example:
    
    ```java
    public int add(int a, int b) {
        return a + b;
    }
    ```
    

### 4. **Object-Oriented Programming (OOP)**

- **Classes and Objects**:
    
    ```java
    public class Car {
        String color;
        int year;
    
        public Car(String color, int year) {
            this.color = color;
            this.year = year;
        }
    }
    
    Car myCar = new Car("Red", 2020);
    
    ```
    
- **Inheritance**:
    
    ```java
    public class Vehicle {
        // base class
    }
    
    public class Bike extends Vehicle {
        // derived class
    ```
    
- **Polymorphism**: Method overriding allows for different implementations in subclasses.
- **Encapsulation**: Use of private variables and public getters/setters.

### 5. **Error Handling**

- **Try-Catch Block**:
    
    ```java
    try {
        // code that may throw an exception
    } catch (ExceptionType e) {
        // code to handle the exception
    
    ```
    

### 6. **Collections Framework**

- **List**:
    
    ```java
    List<String> names = new ArrayList<>();
    names.add("John");
    ```
    
- **Map**:
    
    ```java
    Map<String, Integer> ageMap = new HashMap<>();
    ageMap.put("John", 25);
    ```
    

### 7. **Basic Input/Output**

- **Scanner for Input**:
    
    ```java
    Scanner scanner = new Scanner(System.in);
    int number = scanner.nextInt();
    ```
    
- **Print Output**:
    
    ```java
    System.out.println("Hello, World!");
    ```