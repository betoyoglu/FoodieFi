# 🥗 FoodieFi - Modern Recipe Application

![Android Build](https://img.shields.io/badge/Build-Passing-success?style=flat&logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue?style=flat&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-green?style=flat&logo=android)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**FoodieFi** is a modern Android application designed to help users discover delicious recipes, filter them by categories, and save their favorites. 
Built using **TheMealDB API**, it adheres to **Clean Architecture** principles and utilizes the latest **Jetpack Compose** toolkit for a stunning UI.

<img width="1920" height="1080" alt="Made with Kotlin   Jetpack Compose" src="https://github.com/user-attachments/assets/a4c0846e-dbb0-41ff-b242-ccc1bb44f632" />


---

## 🛠️ Tech Stack & Libraries

The project follows **Clean Architecture** principles and **MVVM** pattern to ensure scalability and testability.

```text
com.example.foodiefi
├── 📂 data                  # Data Layer (Network & Database)
│   ├── 📂 api               # Retrofit API definitions
│   │   └── FoodApi.kt
│   ├── 📂 local             # Local Data Source (Room)
│   │   ├── 📂 entity        # Database Entities
│   │   │   └── MealEntity.kt
│   │   ├── FoodDatabase.kt  # Room Database instance
│   │   └── MealDao.kt       # Data Access Object
│   ├── 📂 model             # Data Transfer Objects (DTOs)
│   │   ├── CategoryResponse.kt
│   │   └── MealResponse.kt
│   └── 📂 repository        # Repository Implementation
│       └── MealRepository.kt
│
├── 📂 di                    # Dependency Injection
│   └── AppModule.kt         # Hilt Modules (Network, Database providers)
│
├── 📂 navigation            # Navigation Logic
│   ├── BottomNavItem.kt     # Bottom Bar definitions
│   └── Navigation.kt        # NavHost and Route definitions
│
├── 📂 ui                    # Presentation Layer (MVVM)
│   ├── 📂 components        # Reusable UI elements (Cards, Chips, Bars)
│   ├── 📂 detail            # Detail Screen & ViewModel
│   ├── 📂 favorite          # Favorite Screen & ViewModel
│   ├── 📂 home              # Home Screen & ViewModel
│   ├── 📂 theme             # Theme definitions (Color, Type, Shape)
│   ├── MealUIState.kt       # State holder for UI
│   ├── FoodieFiApp.kt       # Root Composable (Scaffold entry point)
│   └── MainActivity.kt      # Application entry point

### 🏗️ Architecture & Design Pattern
* **MVVM (Model-View-ViewModel):** Separation of concerns between UI and business logic.
* **Clean Architecture:** Distinct layers for Data, Domain, and UI.
* **Repository Pattern:** Abstraction of data sources.
* **Dependency Injection:** Powered by Hilt.

### 💻 Core & UI
* **Kotlin:** 100% Kotlin codebase.
* **Jetpack Compose:** Modern, declarative UI toolkit.
* **Material Design 3:** Latest design guidelines and components.
* **Navigation Compose:** Single-activity navigation with argument passing.

### 🌐 Network & Data
* **Retrofit2 & OkHttp3:** For REST API communication.
* **Room Database:** Local data persistence for favorites (Offline capability).
* **Coil:** Async image loading and caching.
* **Gson:** JSON serialization/deserialization.

### 💉 Dependency Injection
* **Hilt:** To improve the management and testability of dependencies.

### 🧵 Concurrency
* **Coroutines & Flow:** Asynchronous programming and reactive data streams.

---

## 🧪 Testing

Uygulama kalitesini ve sürdürülebilirliği sağlamak adına kapsamlı testler yazılmıştır.

| Test Türü | Kapsam | Kullanılan Araçlar |
|-----------|--------|-------------------|
| **Unit Tests** | ViewModels, Repositories | JUnit4, Mockk, Coroutines-Test |
| **Integration Tests** | Room Database (DAO) | AndroidX Test, Room-Testing |
| **UI Tests** | Navigation, UI Components | Compose UI Test, Espresso |


---

## ✨ Key Features

* **Global Search:** Instant search across the entire database with debounce optimization.
* **Recipe of the Week:** Randomized recipe recommendations generated weekly.
* **Dynamic Details:** Recipes display dynamic cooking time, difficulty, and ratings.
* **Favorites:** Save recipes to local storage for offline access.
* **Smart Filtering:** Filter recipes quickly by categories.
* **Navigation:** Seamless transitions using Bottom Navigation and standard routing.
