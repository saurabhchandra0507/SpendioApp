# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

This is a standard Gradle-wrapped Android project; run everything through `./gradlew` from the repo root.

- Build debug APK: `./gradlew assembleDebug`
- Run local unit tests (JVM, in `app/src/test`): `./gradlew testDebugUnitTest`
- Run a single unit test: `./gradlew testDebugUnitTest --tests "com.example.spendioapp.ExampleUnitTest.addition_isCorrect"`
- Run instrumented tests (require a connected device/emulator, in `app/src/androidTest`): `./gradlew connectedDebugAndroidTest`
- Install debug build to a connected device: `./gradlew installDebug`

There is no configured lint/ktlint/detekt task beyond the default `./gradlew lint`.

## Architecture

Single-module app (`:app`), package `com.example.spendioapp`, Jetpack Compose UI throughout, min SDK 24 / target & compile SDK 37, Kotlin with KSP (for Room).

**Navigation**: `MainActivity` hosts a single `NavHost` (Compose Navigation) with five string routes: `login` → `home` → `add_expense` / `calculator` / `profile`. There is no ViewModel layer — `MainActivity` itself owns the `SpendioDatabase` instance and `expenseDao`, collects `expenseDao.getAllExpenses()` as Compose state, and passes data/callbacks (lambdas that call `expenseDao.insertExpense`/`deleteExpense` inside `rememberCoroutineScope().launch { }`) directly into screen composables. When adding a new screen, follow this pattern (also captured in `.claude/skills/add-screen/SKILL.md`): create it under `screens/` with a `Scaffold` + `TopAppBar` back-arrow (unless it's a root destination like `HomeScreen`), add a route + `composable("...")` block in `MainActivity` wired to `onBackClick = { navController.popBackStack() }`, wire whatever UI element triggers navigation to it (e.g. a `HomeScreen` nav-bar item's `onClick` via an `on<Screen>Click` callback), and thread any DB access back through callback lambdas rather than reaching into the DAO from the screen itself.

**Data layer** (`data/`): Room only, no repository abstraction.
- `SpendioDatabase` — single-entity Room DB (`Expense`), exposed via a classic double-checked-locking singleton (`getDatabase(context)`).
- `ExpenseDao` — `insertExpense`/`deleteExpense` (suspend) and `getAllExpenses()` (returns `Flow<List<Expense>>`, ordered by `id DESC`).
- `models/Expense.kt` — the sole `@Entity` (table `expenses`): `id`, `amount`, `category`, `date` (stored as a `dd/MM/yyyy` string, not a real date type), `description`.

**Screens** (`screens/`), each a top-level `@Composable` taking primitive/lambda params (no shared state holder):
- `LoginScreen` — email/password fields are local-only; `onLoginClick` just navigates to `home` with no auth check.
- `HomeScreen` — computes balance from a hardcoded `income = 50000.00` minus the sum of `expenses`; renders the expense list (light-gray `Card`s) with a delete confirmation `AlertDialog`; bottom `NavigationBar` drives `onCalculatorClick`/`onProfileClick`.
- `AddExpenseScreen` — `Scaffold` + `TopAppBar` with a functional back arrow (`onBackClick`); category picker (fixed in-code list: Food/Transport/Bills/Shopping/Entertainment) + Material3 `DatePickerDialog`; builds an `Expense` and calls `onExpenseAdded`.
- `CalculatorScreen` — self-contained EMI calculator (standard reducing-balance formula), no persistence.
- `ProfileScreen` — `Scaffold` + `TopAppBar` with back arrow; entirely hardcoded/static content (name, email, "member since", a local-only notifications `Switch`, a no-op Settings row, a no-op Logout button) — not wired to any real user data or auth.

**Theme** (`ui/theme/`): standard Compose Material3 theme scaffolding (`Color.kt`, `Theme.kt`, `Type.kt`) from the Android Studio Compose template; screens mostly hardcode colors (e.g. `Color(0xFFFF9800)`) directly rather than using the theme's `MaterialTheme.colorScheme`.

## Notes

- Test sources (`ExampleUnitTest.kt`, `ExampleInstrumentedTest.kt`) are still the unmodified Android Studio template stubs — there is no real test coverage yet.
- `HomeScreen`'s income figure is hardcoded; there is no income-entry flow.
