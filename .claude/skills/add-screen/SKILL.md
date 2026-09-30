---
name: add-screen
description: Use when adding a new screen/composable to the Spendio app, so it follows the existing navigation and data-flow pattern.
---

When adding a new screen to Spendio:

1. Create the screen under `app/src/main/java/com/example/spendioapp/screens/`, as a top-level `@Composable` function (see `HomeScreen.kt`, `AddExpenseScreen.kt`, `CalculatorScreen.kt`, `ProfileScreen.kt` for the existing style).

2. Follow the existing lambda-callback pattern — no ViewModel. The screen takes plain data and callback lambdas as parameters (e.g. `onSomethingClick: () -> Unit`, `onItemSaved: (Item) -> Unit`). It does not hold app data, fetch data, or navigate on its own.

3. Give it a `Scaffold` + `TopAppBar` with a back arrow, unless it's a root destination like `HomeScreen`:
   ```kotlin
   topBar = {
       TopAppBar(
           navigationIcon = {
               IconButton(onClick = { onBackClick() }) {
                   Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
               }
           },
           title = {
               Text(text = "Screen Title", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
           }
       )
   }
   ```
   This matches `CalculatorScreen.kt` and `ProfileScreen.kt`.

4. Wire a new route into `MainActivity`'s `NavHost`:
   - Add the import for the new screen.
   - Add a `composable("route_name") { ... }` block, passing in data/callbacks.
   - Wire `onBackClick = { navController.popBackStack() }`.

5. Wire the entry point that reaches the new screen — whatever button, FAB, or nav item should lead there. If it's triggered from another screen (e.g. a `NavigationBarItem` in `HomeScreen`), add an `on<Screen>Click: () -> Unit` parameter to that screen, call it from the UI element's `onClick`, and wire it in `MainActivity` to `navController.navigate("route_name")` — mirroring how `onCalculatorClick`/`onProfileClick` work today.

6. No local DB access in the screen itself. Any `expenseDao` calls (or other DB reads/writes) stay in `MainActivity`, inside the lambda passed to the screen — mirroring `onExpenseAdded`/`onDeleteExpense`.

7. Build to confirm it compiles: `./gradlew assembleDebug`.

If you're only asked to change the *content/body* of an already-wired screen (no new navigation involved), skip steps 4–5 and just apply steps 1–3 and 6.
