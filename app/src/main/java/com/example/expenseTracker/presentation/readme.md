# README for `presentation` Folder - Clean Architecture

## Overview
This `presentation` folder is part of an Android application, adhering to Clean Architecture principles. It serves as the UI layer, responsible for handling user interfaces, interactions, and presentation logic, typically using Jetpack Compose for building declarative and reactive UIs. The folder ensures a clean separation between the UI and the business logic (handled in the `domain` layer) and data management (handled in the `data` layer), promoting maintainability, testability, and scalability.

## Folder Structure
Below is an explanation of the key components and files in the `presentation` folder:

### 1. `contracts`
- **Purpose**: Contains interfaces or contracts that define the interaction between the presentation layer (e.g., ViewModels, UI components) and the business logic or data layers.
- **Key Files**:
    - Includes base contracts for general UI behavior and specific contracts for different features or categories, ensuring a clear API for communication with use cases, repositories, or other layers.

### 2. `ui`
- **Purpose**: Houses the UI-related code, including composable functions, screens, and components, built using Jetpack Compose for a modern, declarative UI.
- **Subfolders**:
    - **components**: Contains reusable UI components or composables that can be used across multiple screens or features, such as custom layouts, buttons, or indicators (e.g., an empty view).
    - **features**: Organizes UI code by specific application features or screens, grouping related composables, ViewModels, and navigation logic together for better modularity.
        - Includes subfolders for individual features, each containing UI logic, state management, and interactions for that feature.

### 3. `theme`
- **Purpose**: Manages the visual styling and theming of the application, ensuring a consistent look and feel across all screens and components.
- **Key Files**:
    - Includes files for defining colors, shapes, typography, and overall themes, typically using Jetpack Compose’s Material Design components or custom styles.

## Key Principles
- **Clean Architecture**: The `presentation` layer depends on the `domain` layer for business logic and the `data` layer for data, ensuring unidirectional data flow and separation of concerns.
- **MVVM (Model-View-ViewModel)**: Works with ViewModels to manage UI state and expose data or actions from the domain layer, using Jetpack Compose for rendering and handling user interactions.
- **Jetpack Compose**: Leverages Jetpack Compose for building reactive, declarative UIs, ensuring efficient updates and a modern user experience.

## Dependencies
This layer typically relies on:
- **Jetpack Compose**: For building UI components, screens, and themes.
- **Jetpack ViewModel**: For managing UI-related state and logic, acting as the bridge between the UI and the `domain` layer.
- **Kotlin Coroutines**: For asynchronous operations and state management (e.g., `State`, `StateFlow`, or `LiveData`).
- **Hilt or Dagger**: For dependency injection, providing instances of ViewModels, use cases, or repositories.
- **Navigation Component**: For handling navigation between screens or composables in a type-safe manner.

## How to Use
1. **Define Contracts**: Use contracts to specify how the presentation layer interacts with the `domain` layer, ensuring clear boundaries and testability.
2. **Build UI Components**: Create reusable composables in the `components` subfolder for common UI elements, and feature-specific screens in the `features` subfolder, integrating with ViewModels for state and actions.
3. **Manage State**: Use ViewModels to handle UI state, exposing data or events from use cases (e.g., via `StateFlow` or `LiveData`) and updating the Jetpack Compose UI reactively.
4. **Apply Theming**: Use the `theme` files to define and apply consistent colors, shapes, typography, and themes across the application, enhancing visual consistency.
5. **Handle Navigation**: Implement navigation logic within `features` or using Jetpack’s Navigation Component to move between screens or features seamlessly.

## Best Practices
- Keep UI components small and reusable, focusing on single responsibilities (e.g., one composable per visual element or feature).
- Use ViewModels to manage state and avoid putting business logic directly in composables, maintaining separation of concerns.
- Write unit tests for ViewModels and UI tests for composables to verify behavior and UI rendering.
- Use Jetpack Compose’s preview annotations to design and test UI components in isolation.
- Ensure themes are flexible and customizable, supporting dark/light modes and Material Design guidelines.

## Example Workflow
1. Define a contract in the `contracts` folder to specify how a feature interacts with a use case from the `domain` layer.
2. Create a composable screen in the `features` subfolder, connecting it to a ViewModel that exposes data or actions from the use case.
3. Build reusable UI components (e.g., an empty view) in the `components` subfolder for use across multiple screens.
4. Apply a consistent theme from the `theme` folder to style the UI, ensuring a unified look across the application.
5. Handle user interactions (e.g., button clicks) in the composable, triggering ViewModel actions that update the UI or call domain logic.

## Notes
- This folder assumes a multi-module project structure, with `presentation` working alongside `data` (for data access) and `domain` (for business logic) to create a fully decoupled architecture.
- For network or data-related concerns, refer to the `network` and `data` layers, respectively, while this layer focuses solely on UI and presentation logic.

If you need more detailed guidance or specific code examples (e.g., for a composable, ViewModel, or theme setup), feel free to ask!