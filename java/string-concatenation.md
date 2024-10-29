### Concatenation in Java

In Java, when you concatenate strings using the `+` operator, the process involves allocating enough space for the new string and copying the contents from the old string to the new one. This means that for each concatenation, a new string object is created and the content is copied over, leading to inefficient performance.

For example:

```java
String result = "";
for (int i = 0; i < n; i++) {
    result += "a";
}
```

In this case, each concatenation creates a new string object, resulting in a time complexity of O(n²) for n iterations.

### Reducing Complexity

To improve performance and reduce the time complexity to O(n), you can use:

1. **Character Array**: Convert the string to a character array using `String.toCharArray()`.
  
2. **StringBuilder**: This is a more efficient option. `StringBuilder` maintains an underlying character array and only expands it when necessary, thus avoiding the creation of multiple new string objects.

Here's how you can use `StringBuilder`:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append("a");
}
String result = sb.toString();
```

Using `StringBuilder` significantly improves performance when concatenating strings in a loop, reducing the time complexity to O(n).