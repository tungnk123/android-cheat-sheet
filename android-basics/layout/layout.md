### **Frame Layout**

- **FrameLayout** = a simple ViewGroup subclass in Android that hold a single child view or a stack of overlapping child views. It positions each child in the top-left corner by default and allows them to overlap on top of each other

⇒ which makes it useful for situations where you need to layer views on top of one another.

- You can, however, add multiple children to a FrameLayout and control their position within the FrameLayout by assigning gravity to each child, using the [`android:layout_gravity`](https://developer.android.com/reference/android/widget/FrameLayout.LayoutParams#attr_android:layout_gravity) attribute
- Key points about Frame Layouts:
    - **Single Child View**: `FrameLayout` is designed to hold a single child view. If more than one view is added, they are layered on top of each other in the order they are added.
    - **Overlaying Views**: It’s commonly used to overlay elements, such as placing a progress spinner on top of an image.
    - **Positioning**: `FrameLayout` doesn’t allow for direct child positioning. Use `Gravity` attributes to control the position of child views.
- `FrameLayout` is often used as a container for fragments:
- Holds a single view, but multiple views can be stacked.
- Use-case: Ideal for displaying one view at a time, like fragments.
- More:
    - https://developer.android.com/reference/android/widget/FrameLayout
    - https://www.tutorialspoint.com/android/android_frame_layout.htm

### **Linear Layout**

- **LinearLayout** is a view group that aligns all children in a single directioni, vertically or horizontally. You can specify the layout direction with the **`android:orientation`** attribute.
- **LinearLayout** was commonly used in earlier Android development, but with the introduction of ConstraintLayout, it’s less frequently used in modern apps.
- Usecase: Perfect for forms or any content that follows a linear progression
- Common attributes:
    - Orientation: Determines the arrangement of child views.
        - `android:orientation="horizontal"` - Aligns views side-by-side.
        - `android:orientation="vertical"` - Stacks views on top of each other
    - Weight: Distributes remaining space among child views based on assigned weights.
        - Set `android:layout_width` to `0dp` (for horizontal) or `android:layout_height` to `0dp` (for vertical).
        - Assign `android:layout_weight` with a numeric value to each child
    - Gravity:
        - Aligns child views within the LinearLayout.
        - Default alignment is top-left (start-top).
    - WeightSum: Space is allocated to child views in the order they are added, potentially causing later views to be hidden if space is insufficient.
    - ***layout_gravity vs gravity:***
        - `gravity` affects **content alignment** within a single view → align it content
        - `layout_gravity` affects **positioning** of a view within its parent → define how a child view is placed inside that container
- More:
    - https://developer.android.com/develop/ui/views/layout/linear
    - https://www.geeksforgeeks.org/linearlayout-and-its-important-attributes-with-examples-in-android/

### **Constraint Layout**

- Create large and complex layouts with a flat view hierarchy.
- Use-case: Versatile for most UI designs and reduces nesting, which improves performance.
- More:

### **Relative Layout**

- A **RelativeLayout** in Android is a type of ViewGroup that allows you to position child views relative to each other or relative to the parent layout. It’s a flexible layout where you can arrange the child views in relation to one another based on certain rules, making it suitable for creating complex UI designs.
- **RelativeLayout** was commonly used in earlier Android development, but with the introduction of **`ConstraintLayout`**, it’s less frequently used in modern apps.
- Every view is positioned relative to another view or the parent container.
- Use-case: Useful when you want a flexible UI without specifying exact positions.
- More: https://developer.android.com/develop/ui/views/layout/constraint-layout

### **Grid Layout**

### **Table Layout**

### **Coordinator Layout**

### **Motion Layout**

### Optimize Layout

- Why?
    - **Performance Improvement**: **reduce Measure/Layout Passes and Overdraw**
    - **Memory Usage**: less overhead for both the View and ViewGroup objects
    - **Ease of Maintenance**: easy to understand, debug, and maintain
    - **Improved Layout Performance**:  smoother and faster UI performance
    - **Reduces Overdraw**: Overdraw occurs when the same pixel is drawn multiple times within a single frame. A flatter hierarchy helps minimize overdraw by reducing overlapping views, which in turn conserves processing power and enhances performance.
- Use Constraint Layout and Frame Layout instead of other layout for more flat layout
- Utilize Merge Tags → prevent additional layers of ViewGroup that would bloat your hierarchy
- Use ViewStub: defer the inflation of views until they are needed
- Use **include** tags → reuse layouts without adding additional layers
- More: https://theadityatiwari.medium.com/boost-your-android-apps-performance-with-a-flat-view-hierarchy-21affcd29970

---

### Examples:

- Frame Layout
    
    ```jsx
    <FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
        <ImageView
            android:id="@+id/backgroundImage"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/sample_background"
            android:contentDescription="@string/sample_background_image"/>
    
        <TextView
            android:id="@+id/centerText"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Hello, World!"
            android:textSize="24sp"
            android:textColor="@android:color/white"/>
    
        <TextView
            android:id="@+id/badgeText"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="top|end"
            android:padding="8dp"
            android:background="@android:color/holo_red_light"
            android:text="New"
            android:textSize="14sp"
            android:textColor="@android:color/white"/>
    </FrameLayout>
    
    ```
    
- Linear Layout
    
    ```jsx
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">
        <Button
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="2"
            android:text="Button 1"
            android:gravity="center" />
        <Button
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:text="Button 2"
            android:gravity="center" />
        <Button
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:text="Button 3"
            android:gravity="center" />
    </LinearLayout>
    
    ```
    
- Merge
    
    ```jsx
    <!-- reusable_component.xml -->
    <merge xmlns:android="http://schemas.android.com/apk/res/android">
        <TextView
            android:id="@+id/textView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Hello World!" />
    </merge>
    ```
    

---

### Reference:

- https://roadmap.sh/android
- https://developer.android.com/develop/ui/views/layout/constraint-layout