# Expenses Tracker

## 📌 Overview
Expenses Tracker is a mobile application built using **Kotlin & Jetpack Compose**, designed to help users manage their finances by tracking income and expenses efficiently. The app provides a user-friendly interface with features like statistics, transaction history, and multi-language support.

## 🎯 Features
- **Splash Screen**: Displays logo and app name.
- **Onboarding Screen**: Introduction and 'Get Started' option.
- **Homepage**: Shows current time, user name, total balance, income, expenses, and transaction history.
- **Statistics**: Displays income/expense analytics with charts (daily, weekly, monthly, yearly), top spending, and sorting options.
- **Add Expense**: Users can add transactions with details like name, amount, date, and invoice.
- **Transaction Details**: Provides detailed information on expenses.
- **Profile Management**: Allows users to update profile details like name, email, and profile picture.
- **Light/Dark Mode** and **Multi-Language Support**.

## 🏗 Architecture & Tech Stack
The app follows **Clean Architecture** and **MVVM** to ensure modularity and maintainability.

### 📌 Tech Stack:
- **UI**: Jetpack Compose
- **State Management**:
  - Local: `remember + MutableState`
  - Global: `ViewModel + StateFlow/LiveData`
  - State Hoisting & State Saving
- **Navigation**: Jetpack Navigation
- **Dependency Injection**: Hilt
- **Local Storage**: Room Database (SQLite)
- **Network Requests**: Retrofit

## 🛠 Setup & Installation
### Prerequisites
- Android Studio Flamingo or later
- JDK 17+
- Gradle 8+

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/jayce-vu/Expense-Tracker.git
   cd expenses-tracker
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and install dependencies.
4. Run the app on an emulator or physical device.

## ✅ Testing & CI/CD
- **Unit Testing**: Implemented using JUnit + Mockk.
- **Code Quality**: Enforced with `ktlint`.
- **GitHub Actions**: Automated build and test execution.

## 🚀 Future Improvements
- Implement **biometric authentication** for security.
- Add **export/import transactions** feature.
- Integrate **cloud sync** for cross-device access.

## Design
Figma Link: [Income & Expense Tracker App](https://www.figma.com/design/x0B0LlOdy5tzRkHFVzeDva/Income-%26-Expense-Tracker-App-(Community)?node-id=0-1&p=f&t=fzUtpXniCU2gqr8k-0)

