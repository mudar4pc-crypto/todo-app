# Todo App - Android

A fully functional Android Todo/Task management application with local storage using SharedPreferences and Gson.

## Features

✅ **Add Todos** - Create new tasks with titles
✅ **Edit Todos** - Modify existing task titles
✅ **Delete Todos** - Remove individual tasks
✅ **Mark Complete** - Check off completed tasks with strikethrough effect
✅ **Clear Completed** - Remove all completed tasks at once
✅ **Local Storage** - All data saved locally using SharedPreferences
✅ **Persistent Data** - Todos survive app restarts
✅ **Task Statistics** - Shows completed vs total tasks
✅ **Clean UI** - Simple, intuitive interface

## Technical Details

### Local Storage Implementation
- **SharedPreferences** for persistent key-value storage
- **Gson** for JSON serialization/deserialization
- **TodoItem** data class with UUID for unique identification
- All operations are saved automatically to device storage

### Architecture
- **TodoItem** - Data model representing a single todo
- **TodoRepository** - Handles all CRUD operations and storage
- **TodoAdapter** - RecyclerView adapter for displaying todos
- **MainActivity** - UI logic and user interactions

### Project Structure
```
TodoApp/
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/com/mudar4pc/todoapp/
│       │   ├── TodoItem.kt          # Data model
│       │   ├── TodoRepository.kt    # Local storage logic
│       │   ├── TodoAdapter.kt       # RecyclerView adapter
│       │   └── MainActivity.kt      # Main UI Activity
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml
│           │   └── todo_item.xml
│           ├── drawable/
│           │   └── todo_item_background.xml
│           └── values/
│               ├── strings.xml
│               ├── colors.xml
│               └── themes.xml
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## How to Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/mudar4pc-crypto/todo-app.git
   cd todo-app
   ```

2. **Open in Android Studio**
   - Download [Android Studio](https://developer.android.com/studio)
   - File → Open → Select the cloned folder
   - Wait for Gradle to sync (5-10 minutes on first run)

3. **Run on Device/Emulator**
   - Enable USB debugging on your Vivo device
   - Connect via USB or use Android Emulator
   - Click the green **Run ▶** button
   - Select your device

4. **Generate APK**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - Find signed APK in `app/release/` or `app/debug/`
   - Transfer to your Android device and install

## Usage

1. **Add a Todo**: Click "+ Add Todo" button, enter title, click "Add"
2. **Mark Complete**: Check the checkbox next to a todo
3. **Edit a Todo**: Click "Edit" button on a todo to modify its title
4. **Delete a Todo**: Click "Delete" button to remove a specific todo
5. **Clear Completed**: Click "Clear Done" to delete all completed todos at once

## Dependencies

- `androidx.core:core-ktx` - Android Kotlin extensions
- `androidx.appcompat:appcompat` - Android compatibility library
- `com.google.android.material:material` - Material Design components
- `androidx.constraintlayout:constraintlayout` - Layout management
- `androidx.recyclerview:recyclerview` - List view component
- `com.google.code.gson:gson` - JSON serialization library

## Data Persistence

All todos are saved in the device's SharedPreferences storage:
- Location: `/data/data/com.mudar4pc.todoapp/shared_prefs/TodoApp.xml`
- Format: JSON array of TodoItem objects
- Automatic saving on every operation
- Survives app crashes and device reboots

## Future Enhancements

- Due dates and reminders
- Todo categories/tags
- Dark mode support
- Cloud backup
- Notifications
- Search functionality
- Sort by priority, date, etc.

## License

MIT License - Feel free to use this project for personal or commercial use.