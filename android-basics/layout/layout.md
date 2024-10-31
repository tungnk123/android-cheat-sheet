### **Frame Layout**

- **FrameLayout** = a simple ViewGroup subclass in Android that hold a single child view or a stack of overlapping child views. It positions each child in the top-left corner by default and allows them to overlap on top of each other

⇒ which makes it useful for situations where you need to layer views on top of one another.

- You can, however, add multiple children to a FrameLayout and control their position within the FrameLayout by assigning gravity to each child, using the [`android:layout_gravity`](https://developer.android.com/reference/android/widget/FrameLayout.LayoutParams#attr_android:layout_gravity) attribute
- Key points about Frame Layouts:
    - **Single Child View**: `FrameLayout` is designed to hold a single child view. If more than one view is added, they are layered on top of each other in the order they are added.
    - **Overlaying Views**: It’s commonly used to overlay elements, such as placing a progress spinner on top of an image.
    - **Positioning**: `FrameLayout` doesn’t allow for direct child positioning. Use `Gravity` attributes to control the position of child views.
- `FrameLayout` is often used as a container for fragments:
- More:
    - https://developer.android.com/reference/android/widget/FrameLayout
    - https://www.tutorialspoint.com/android/android_frame_layout.htm

### **Linear Layout**

- **LinearLayout** is a view group that aligns all children in a single directioni, vertically or horizontally. You can specify the layout direction with the **`android:orientation`** attribute.
- **LinearLayout** was commonly used in earlier Android development, but with the introduction of ConstraintLayout, it’s less frequently used in modern apps.

### **Constraint Layout**

### **Relative Layout**

- A **RelativeLayout** in Android is a type of ViewGroup that allows you to position child views relative to each other or relative to the parent layout. It’s a flexible layout where you can arrange the child views in relation to one another based on certain rules, making it suitable for creating complex UI designs.
- **RelativeLayout** was commonly used in earlier Android development, but with the introduction of **`ConstraintLayout`**, it’s less frequently used in modern apps.
- More: https://developer.android.com/develop/ui/views/layout/constraint-layout

### **Grid Layout**

### **Table Layout**

### **Coordinator Layout**

### **Motion Layout**

### **Build a flatten Layout**

- 

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
    
    ```
    

---

### Reference:

- https://roadmap.sh/android
- https://developer.android.com/develop/ui/views/layout/constraint-layout