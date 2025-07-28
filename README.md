# 🛒 MercadoLibre App

A modern Android application that allows users to search and view product details using MercadoLibre's APIs. Built with Jetpack Compose following Clean Architecture principles and MVVM pattern.

## 📱 Screenshots

| Home Screen | Search Screen | Search Results | Product Details |
|-------------|---------------|----------------|-----------------|
| ![Home](screenshots/home.png) | ![Search](screenshots/search.png) | ![Results](screenshots/results.png) | ![Details](screenshots/details.png) |

## 🚀 Features

### Core Functionality
- **🔍 Product Search**: Real-time search with query suggestions and recent searches
- **📋 Product Listing**: Grid display with product images, prices, discounts, and shipping info
- **📱 Product Details**: Comprehensive product information with image gallery, specifications, and purchase options
- **🔄 Screen Rotation Support**: State preservation during device orientation changes
- **🌐 Multi-language Support**: Portuguese, Spanish, and English localization
- **💾 Search History**: Local storage of recent searches with Room database

### Technical Features
- **🎨 Modern UI**: Built entirely with Jetpack Compose
- **🏗️ Clean Architecture**: Domain, Data, and Presentation layers separation
- **🔄 MVVM Pattern**: Reactive UI with StateFlow and Compose integration
- **⚡ Reactive Programming**: Kotlin Coroutines and Flow for asynchronous operations
- **💉 Dependency Injection**: Hilt for clean dependency management
- **🧪 Comprehensive Testing**: Unit tests, integration tests, and UI tests

## 🛠️ Technical Stack

### Core Technologies
- **Kotlin** `2.1.21` - Programming language
- **Kotlin Serialization** `1.9.0` - JSON serialization
- **Jetpack Compose** `2025.06.01` - Declarative UI toolkit
- **Material Design 3** `1.3.2` - Design system

### Architecture & Navigation
- **Navigation Compose** `2.9.1` - Type-safe navigation
- **Hilt** `2.56.2` - Dependency injection
- **Room** `2.7.2` - Local database for search history
- **ViewModel** `2.9.1` - UI state management

### Networking & Data
- **Kotlinx Serialization Converter** `1.0.0` - JSON conversion
- **Coil** `2.7.0` - Image loading and caching

### Testing
- **JUnit** `4.13.2` - Unit testing framework
- **MockK** `1.13.13` - Mocking framework
- **Turbine** `1.2.1` - Flow testing utilities
- **Coroutines Test** `1.10.2` - Coroutine testing
- **Compose UI Test** - UI automation testing
- **Truth** `1.4.4` - Fluent assertions
- **Navigation Testing** `2.9.1` - Navigation testing

## 🏗️ Architecture

### Clean Architecture Layers

```
📱 Presentation Layer (UI)
├── 🎨 Compose Screens (Home, Search, Results, Details)
├── 🎭 ViewModels (State management with StateFlow)
├── 🧭 Navigation (Type-safe routing with Kotlin serialization)
└── 🎯 UI Components (Reusable Compose components)

💼 Domain Layer (Business Logic)
├── 📋 Models (Product, Category, SearchHistory)
├── 🔧 Use Cases (SearchProducts, GetProductDetails, SaveSearchHistory)
├── 📝 Repository Interfaces
└── 🛡️ Result wrapper for error handling

💾 Data Layer (Data Sources)
├── 🌐 Remote Data Source (Mock API service with JSON assets)
├── 💿 Local Data Source (Room database for search history)
├── 🔄 Repository Implementations
└── 📦 Data Models with JSON serialization
```

### Design Patterns
- **MVVM (Model-View-ViewModel)**: Reactive UI with unidirectional data flow
- **Repository Pattern**: Abstraction layer for data sources
- **Use Case Pattern**: Single responsibility business logic
- **Dependency Injection**: Constructor injection with Hilt
- **Observer Pattern**: StateFlow for reactive state management

## 📁 Project Structure

```
app/src/main/java/com/joao01sb/mercadolibreapp/
├── 📱 presentation/
│   ├── ui/
│   │   ├── screen/ (HomeScreen, SearchScreen, SearchResultsScreen, ProductDetailScreen)
│   │   ├── components/ (ProductItem, SearchBar, SearchResultsBar)
│   │   └── viewmodel/ (HomeViewModel, SearchViewModel, etc.)
│   ├── navigation/ (AppNavigation, Routes)
│   ├── theme/ (Colors, Typography, Theme)
│   └── util/ (UiState, Extensions)
├── 💼 domain/
│   ├── model/ (Product, ProductDetail, Category)
│   ├── repository/ (Repository interfaces)
│   └── usecase/ (Business logic use cases)
├── 💾 data/
│   ├── local/ (Room database, DAOs, Entities)
│   ├── remote/ (API service, Data models)
│   └── repository/ (Repository implementations)
├── 💉 di/ (Hilt modules)
└── 🛠️ util/ (Helper classes, Extensions)
```

## 🔧 Key Implementation Details

### State Management
- **UiState Sealed Class**: Loading, Success, Error states
- **StateFlow**: Reactive state management in ViewModels
- **Compose State**: Automatic UI recomposition

### Error Handling
- **Result Wrapper**: Consistent error handling across layers
- **Exception Mapping**: Domain-specific error messages
- **Graceful Degradation**: Fallback UI states for errors

### Database
- **Room Database**: Local storage for search history
- **Flow Integration**: Reactive database queries
- **Migration Support**: Database versioning strategy

### Testing Strategy
- **Unit Tests**: ViewModels, Use Cases, Repositories (90%+ coverage)
- **Integration Tests**: Database operations and API services
- **UI Tests**: Navigation flows and user interactions
- **Mock Data**: Comprehensive test fixtures and utilities

## 🎯 Key Features Demonstrated

### For Team Lead Evaluation
- **✅ Clean Architecture**: Proper separation of concerns with testable code
- **✅ SOLID Principles**: Single responsibility, dependency inversion, etc.
- **✅ Modern Android Development**: Latest Jetpack libraries and best practices
- **✅ Reactive Programming**: Coroutines, Flow, and StateFlow implementation
- **✅ Comprehensive Testing**: Unit, integration, and UI test coverage
- **✅ Type Safety**: Kotlin serialization for navigation and data parsing
- **✅ Error Handling**: Robust error management and user feedback
- **✅ Performance**: Efficient image loading, database queries, and UI rendering
- **✅ Scalability**: Modular architecture ready for feature expansion

## 📝 Development Notes

**Mock Data Implementation**: Currently using local JSON files to simulate API responses due to MercadoLibre API authentication limitations. The architecture is designed to easily switch to real API endpoints by simply updating the data source implementation.

**Testing Coverage**: Implemented comprehensive test suite including:
- Unit tests for ViewModels, Use Cases, and Repositories
- Integration tests for database operations
- UI tests for navigation and user interactions
- Mock data utilities for consistent testing

## 🚀 Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run the app on device/emulator
5. Run tests: `./gradlew test` and `./gradlew connectedAndroidTest`

## 📊 Code Quality

- **Kotlin Coding Standards**: Following official Kotlin conventions
- **Architecture Guidelines**: Google's recommended app architecture
- **Testing Best Practices**: AAA pattern (Arrange, Act, Assert)
- **Documentation**: Comprehensive inline documentation and README
