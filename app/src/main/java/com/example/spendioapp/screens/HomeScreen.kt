package com.example.spendioapp.screens
import com.example.spendioapp.models.Expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spendioapp.R
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    expenses: List<Expense>,
    onAddExpenseClick: () -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    onCalculatorClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val income = 10000.00
    val totalExpense = expenses.sumOf {it.amount}
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }

    val balance = income - totalExpense

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Spendio",
                        modifier = Modifier.padding(30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        color = Color(0xFFE65100)
                    )
                },

                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "Spendio Logo",
                        modifier = Modifier.size(70.dp)
                    )
                }
            )
        },

        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",

                            )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onCalculatorClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = "EMI Calculator"
                        )
                    },
                    label = {
                        Text("EMI Calculator")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        onProfileClick()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddExpenseClick()
                },
                containerColor = Color(0xFFFF9800)
            ) {
                Text(
                    text = "+",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White
                )

            }
        }



    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Welcome to Spendio",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )

            Text(
                text = "Here's your expense summary",
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp
                )
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(17.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xD5E15023)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                )

                {

                    Text(
                        text = "Total balance",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "₹%.2f".format(balance),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Income",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(7.dp)
                            )

                            Text(
                                text = "₹%.2f".format(income),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Expense",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(7.dp)
                            )

                            Text(
                                text = "₹%.2f".format(totalExpense),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Text(
                text = "Recent Expenses",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp

                )
            )
            expenses.forEach { expenseItem ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 10.dp
                        )

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = expenseItem.category,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.width(12.dp)
                                )

                                Text(
                                    text = expenseItem.date,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(7.dp)
                            )

                            Text(
                                text = expenseItem.description,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF757575)

                            )}

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }
                        Text(
                            text = "₹ %.2f".format(expenseItem.amount),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                            IconButton(
                                onClick =
                             {
                                expenseToDelete = expenseItem
                            }
                        )
                            {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = "Delete expense",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            if (expenseToDelete != null){
                AlertDialog(
                    onDismissRequest = {
                        expenseToDelete = null
                    },
                    title = {
                        Text("Delete Expense")
                    },
                    text = {
                        Text("Are you sure you want to delete this expense ?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteExpense(expenseToDelete!!)
                                expenseToDelete = null
                        }
                        )
                        {
                            Text(
                                text = "Delete",
                                color = Color.Red
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                expenseToDelete = null
                            }
                        )
                        {
                            Text("Cancel")
                        }
                    }

                )
            }



        }
    }
}