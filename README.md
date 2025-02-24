# Expense Tracker

Expense Tracker is a personal finance management application that helps users track their expenses efficiently.
The backend can be implemented using Google Script + Excel or Firebase.

## Key Features

✅ Add/Edit/Delete transactions  
✅ Categorize expenses (Food, Shopping, Transportation, etc.)  
✅ Display financial charts  
✅ Store data using Room Database  
✅ Sync data to the server using Retrofit  

## Technologies Used

The application follows Clean Architecture principles along with Unidirectional Data Flow (UDF) pattern using ViewModel + StateFlow.

### Architectural Layers

1. **Presentation Layer**
   - Composed of UI components (Jetpack Compose)
   - Uses ViewModel to manage UI-related data
   - Implements StateFlow for state management

2. **Domain Layer**
   - Contains business logic (Use Cases)
   - Uses Repository interfaces for dependency inversion

3. **Data Layer**
   - Manages data sources (Local: Room Database, Remote: Retrofit API)
   - Implements Repository pattern

### Dependency Injection

The app utilizes Dependency Injection for better modularity and testability:

✅ **Hilt** for Dependency Injection  
✅ **ViewModel Injection** for lifecycle-aware dependencies  
✅ **Repository Injection** for data handling abstraction  

## State Management

✅ Local state management (remember + MutableState)  
✅ Global state management (ViewModel + StateFlow/LiveData)  
✅ State Hoisting  
✅ State Saving  
✅ Navigation  
✅ Local Storage using Room Database (SQLite)  
✅ HTTP Requests using Retrofit  


## Design
Figma Link: [Income & Expense Tracker App](https://www.figma.com/design/x0B0LlOdy5tzRkHFVzeDva/Income-%26-Expense-Tracker-App-(Community)?node-id=0-1&p=f&t=fzUtpXniCU2gqr8k-0)

