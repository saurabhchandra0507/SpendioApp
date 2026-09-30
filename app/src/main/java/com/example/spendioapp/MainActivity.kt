package com.example.spendioapp

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.spendioapp.data.SpendioDatabase
import com.example.spendioapp.screens.AddExpenseScreen
import com.example.spendioapp.screens.CalculatorScreen
import com.example.spendioapp.screens.HomeScreen
import com.example.spendioapp.screens.LoginScreen
import com.example.spendioapp.screens.ProfileScreen

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()

            val database = remember {
                SpendioDatabase.getDatabase(applicationContext)
            }

            val expenseDao = database.expenseDao()

            val expenses by expenseDao
                .getAllExpenses()
                .collectAsState(initial = emptyList())

            val scope = rememberCoroutineScope()

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {

                composable("login") {

                    LoginScreen(
                        onLoginClick = {
                            navController.navigate("home")
                        }
                    )
                }

                composable("home") {

                    HomeScreen(
                        expenses = expenses,

                        onAddExpenseClick = {
                            navController.navigate("add_expense")
                        },

                        onDeleteExpense = { expense ->

                            scope.launch {
                                expenseDao.deleteExpense(expense)
                            }
                        },

                        onCalculatorClick = {
                            navController.navigate("calculator")
                        },

                        onProfileClick = {
                            navController.navigate("profile")
                        }
                    )
                }

                composable("add_expense") {

                    AddExpenseScreen(
                        onExpenseAdded = { expense ->

                            scope.launch {

                                expenseDao.insertExpense(expense)

                                navController.popBackStack()
                            }
                        },

                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("calculator") {

                    CalculatorScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("profile") {

                    ProfileScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}