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
- Constraint Layout is a new ViewGroup for android. The main motive of constraint layout is to design layouts which are flexible and look the same in all screen sizes.
- Use-case: Versatile for most UI designs and reduces nesting, which improves performance.
- The layout editor uses constraints to identify the position of a view on the screen.
- The attributes like `android:layout_constraintTop_toTopOf`, `android:layout_constraintTop_toBottomOf`and other similar attributes are used to set constraints to views w.r.t. other views.
- It supports **chaining** of views, using which the views can be easily positioned over a space, and works much better than using a combo of weights and gravity to achieve a configuration. The attributes `android:layout_constraintHorizontal_chainStyle` and `android:layout_constraintVertical_chainStyle` can be supplied various values to produce the following effects:

!https://miro.medium.com/v2/resize:fit:936/0*R14qaoRPci9I9Enq.png

- Supports horizontal and vertical bias,by which we can tweak the positioning to favor one side over another using the bias attributes.
- It also provides a view called `Guideline` using which you can have an imaginary line (which is not a view) anywhere on the screen and position views according to it. You can position this guideline by providing it either a fixed value or a percentage value (w.r.t. the screen).
- More:
    - https://developer.android.com/develop/ui/views/layout/constraint-layout

### **Relative Layout**

- A **RelativeLayout** in Android is a type of ViewGroup that allows you to position child views relative to each other or relative to the parent layout. It’s a flexible layout where you can arrange the child views in relation to one another based on certain rules, making it suitable for creating complex UI designs.
- **RelativeLayout** was commonly used in earlier Android development, but with the introduction of **`ConstraintLayout`**, it’s less frequently used in modern apps.
- Every view is positioned relative to another view or the parent container.
- Use-case: Useful when you want a flexible UI without specifying exact positions.
- More: https://developer.android.com/develop/ui/views/layout/constraint-layout

### **Grid Layout**

- `GridLayout` is a type of layout in Android that arranges child views in a grid-like structure, where you can define the number of rows and columns. It is useful for organizing items in a structured, grid format.
- Core Attributes
    - **`rowCount`**: Specifies the number of rows in the grid.
    - **`columnCount`**: Specifies the number of columns in the grid.
    - **`layout_row`**: Defines the row position of a child within the grid.
    - **`layout_column`**: Defines the column position of a child within the grid.
    - **`layout_rowSpan`**: Allows a child view to span multiple rows.
    - **`layout_columnSpan`**: Allows a child view to span multiple columns.
    - **`layout_gravity`**: Controls how the child view aligns within its cell (e.g., `fill`, `center`).
    - **`layout_columnWeight`**: Specifies how much extra space a column should take compared to others. This works similarly to `layout_weight` in `LinearLayout`.
- Best Practices
    - **Use `layout_columnWeight`** if you want the columns to expand proportionally with the screen width.
    - **Use spans wisely**: Spanning across columns or rows can help create visually engaging layouts but may complicate alignment if overused.
    - **Set `layout_gravity` carefully** to ensure that child views align well within their cells.
- More:
    - [GridLayout](https://developer.android.com/reference/android/widget/GridLayout)
    - [Medium/android-grid-layout](https://medium.com/google-developer-experts/android-grid-layout-1faf0df8d6f2)

### **Table Layout**

- [`TableLayout`](https://developer.android.com/reference/android/widget/TableLayout) is a [`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup) that displays child [`View`](https://developer.android.com/reference/android/view/View) elements in rows and columns.
- [`TableLayout`](https://developer.android.com/reference/android/widget/TableLayout) positions its children into rows and columns. TableLayout containers do not display border lines for their rows, columns, or cells. The table will have as many columns as the row with the most cells. A table can leave cells empty. Cells can span multiple columns, as they can in HTML. You can span columns by using the `span` field in the [`TableRow.LayoutParams`](https://developer.android.com/reference/android/widget/TableRow.LayoutParams) class.
- More:
    - https://developer.android.com/guide/topics/ui/layout/grid

### **Coordinator Layout**

- CoordinatorLayout is a super-powered FrameLayout.
- CoordinatorLayout is intended for two primary use cases:
- As a top-level application decor or chrome layout
- As a container for a specific interaction with one or more child views
- By specifying Behaviors for child views of a CoordinatorLayout you can provide many different interactions within a single parent and those views can also interact with one another. View classes can specify a default behavior when used as a child of a CoordinatorLayout using the DefaultBehavior annotation.
- More:
    - https://developer.android.com/reference/androidx/coordinatorlayout/widget/CoordinatorLayoutConstriant Layou
    - https://www.digitalocean.com/community/tutorials/android-coordinatorlayout

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
    
- Constraint Layout
    
    ```jsx
    <android.support.constraint.ConstraintLayout ...>
          <Button android:id="@+id/button" ...
            app:layout_constraintHorizontal_bias="0.3"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent/>
    ```
    
- Coordinator Layout
    
    ```jsx
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
        <!-- AppBarLayout for scrollable toolbar behavior -->
        <com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">
    
            <!-- CollapsingToolbarLayout for collapsing effect -->
            <com.google.android.material.appbar.CollapsingToolbarLayout
                android:layout_width="match_parent"
                android:layout_height="200dp"
                app:layout_scrollFlags="scroll|exitUntilCollapsed"
                app:contentScrim="?attr/colorPrimary">
    
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:src="@drawable/header_image"
                    android:scaleType="centerCrop"
                    app:layout_collapseMode="parallax" />
    
                <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?attr/actionBarSize"
                    app:layout_collapseMode="pin"
                    app:popupTheme="@style/ThemeOverlay.AppCompat.Light" />
            </com.google.android.material.appbar.CollapsingToolbarLayout>
        </com.google.android.material.appbar.AppBarLayout>
    
        <!-- RecyclerView with scroll behavior -->
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior" />
    
        <!-- FloatingActionButton with hide-on-scroll behavior -->
        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/fab"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="16dp"
            android:src="@drawable/ic_add"
            app:layout_anchor="@id/recycler_view"
            app:layout_anchorGravity="bottom|end"
            app:layout_behavior="com.google.android.material.behavior.HideBottomViewOnScrollBehavior" />
    
    </androidx.coordinatorlayout.widget.CoordinatorLayout>
    
    ```
    
- Table Layout
    
    ```jsx
    <?xml version="1.0" encoding="utf-8"?>
    <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:stretchColumns="1">
        <TableRow>
            <TextView
                android:text="@string/table_layout_4_open"
                android:padding="3dip" />
            <TextView
                android:text="@string/table_layout_4_open_shortcut"
                android:gravity="right"
                android:padding="3dip" />
        </TableRow>
    
        <TableRow>
            <TextView
                android:text="@string/table_layout_4_save"
                android:padding="3dip" />
            <TextView
                android:text="@string/table_layout_4_save_shortcut"
                android:gravity="right"
                android:padding="3dip" />
        </TableRow>
    </TableLayout>
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