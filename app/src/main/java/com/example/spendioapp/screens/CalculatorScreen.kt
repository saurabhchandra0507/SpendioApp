package com.example.spendioapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    onBackClick: () -> Unit
) {

    var loanAmount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var loanTenure by remember { mutableStateOf("") }

    var emi by remember { mutableStateOf(0.0) }
    var totalInterest by remember { mutableStateOf(0.0) }
    var totalPayment by remember { mutableStateOf(0.0) }

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
                        text = "EMI Calculator",
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {

            OutlinedTextField(
                value = loanAmount,
                onValueChange = {
                    loanAmount = it
                },
                label = {
                    Text(
                        text = "Loan Amount",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            OutlinedTextField(
                value = interestRate,
                onValueChange = {
                    interestRate = it
                },
                label = {
                    Text(
                        text = "Interest Rate (%)",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            OutlinedTextField(
                value = loanTenure,
                onValueChange = {
                    loanTenure = it
                },
                label = {
                    Text(
                        text = "Loan Tenure (Years)",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            Button(
                onClick = {

                    val principal = loanAmount.toDoubleOrNull() ?: 0.0
                    val annualRate = interestRate.toDoubleOrNull() ?: 0.0
                    val years = loanTenure.toDoubleOrNull() ?: 0.0

                    val monthlyRate = annualRate / 12 / 100
                    val numberOfMonths = years * 12

                    if (principal > 0 && numberOfMonths > 0) {

                        val emiValue =
                            if (monthlyRate == 0.0) {
                                principal / numberOfMonths
                            } else {
                                principal *
                                        monthlyRate *
                                        Math.pow(
                                            1 + monthlyRate,
                                            numberOfMonths
                                        ) /
                                        (
                                                Math.pow(
                                                    1 + monthlyRate,
                                                    numberOfMonths
                                                ) - 1
                                                )
                            }

                        emi = emiValue
                        totalPayment = emi * numberOfMonths
                        totalInterest = totalPayment - principal
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(
                    text = "Calculate EMI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(
                modifier = Modifier.height(50.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        text = "Monthly EMI: ₹ %.0f".format(emi),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Text(
                        text = "Total Interest: ₹ %.0f".format(totalInterest),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Text(
                        text = "Total Payment: ₹ %.0f".format(totalPayment),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}