package com.example.spendioapp.screens
import com.example.spendioapp.models.Expense
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.ActivityNavigator
import androidx.compose.foundation.clickable
import androidx.compose.material3.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onExpenseAdded: (Expense) -> Unit,
    onBackClick: () -> Unit
){
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Add Expense",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )
                }
            )
        }
    ) { innerPadding ->

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = {
                Text(
                    text = "Amount",
                    fontWeight = FontWeight.Bold
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = "Category",
                        fontWeight = FontWeight.Bold
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                val categories = listOf(
                    "Food",
                    "Transport",
                    "Bills",
                    "Shopping",
                    "Entertainment"

                )
                categories.forEach { item->
                    DropdownMenuItem(
                        text = {
                            Text(item)
                        },
                        onClick = {
                            category = item
                            expanded = false
                        }
                    )
                }

            }
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                )
            },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePicker = true
                }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePicker = true
                    }
            ) {
                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    label = {
                        Text(
                            text = "Date",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Text("📅")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledLabelColor = Color.Black,
                        disabledBorderColor = Color.Black,
                        disabledTrailingIconColor = Color.Black
                    )
                )
            }
        if (showDatePicker) {

            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                date = java.text.SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    java.util.Locale.getDefault()
                                ).format(java.util.Date(it))
                            }

                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {

                DatePicker(
                    state = datePickerState
                )
            }
        }


    }
        Spacer(
            modifier = Modifier.height(50.dp)
        )
        Button(
            onClick = {
                if (amount.isNotEmpty() &&
                    category.isNotEmpty() &&
                    date.isNotEmpty() &&
                    description.isNotEmpty()
                    )
                {
                    val newExpense = Expense(
                        amount = amount.toDouble(),
                        category = category,
                        date = date,
                        description = description
                    )
                    onExpenseAdded(newExpense)

                }

            },
            modifier = Modifier
                .width(360.dp)
                .height(60.dp)
            .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800)
            )
        ) {
            Text(
                text = "Add Expense",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

    }
    }
}