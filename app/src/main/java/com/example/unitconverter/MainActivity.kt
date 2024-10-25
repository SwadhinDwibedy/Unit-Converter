package com.example.unitconverter

import android.os.Bundle
import androidx.compose.ui.tooling.preview.Preview
import android.view.Surface
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextForegroundStyle.Unspecified.color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt
import androidx.compose.ui.tooling.preview.Preview as Preview1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    UnitConverter()
                }
            }
        }
    }
}
@Composable
fun UnitConverter() {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Select") }
    var outputUnit by remember { mutableStateOf("Select") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(1.0) }
    val oconversionFactor = remember { mutableStateOf(1.0) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colorScheme.onBackground
    )

    fun convertUnits() {
        // Ensure input is a valid number
        val inputValueDouble = inputValue.toDoubleOrNull() ?: return

        // Only convert if both input and output units are selected (not "Select")
        if (inputUnit != "Select" && outputUnit != "Select") {
            val result =
                (inputValueDouble * conversionFactor.value * 100.0 / oconversionFactor.value).roundToInt() / 100.0
            outputValue = "$result $outputUnit"
        } else {
            outputValue = "Invalid Selection"
        }
    }



    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Unit Converter", style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE),
                fontFamily = FontFamily.Serif
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter Value", style = TextStyle(color = Color.Gray)) },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
//            input box
            Box {
//              input button
                Button(onClick = { iExpanded = true }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))) {
                    Text(text = inputUnit, color = Color.White)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow down")
                }
                    DropdownMenu(expanded = iExpanded,
                        onDismissRequest = { iExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("CentiMeters") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "CentiMeters"
                                conversionFactor.value = 0.01
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("Meters") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Meters"
                                conversionFactor.value = 1.00
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Feet"
                                conversionFactor.value = 0.3048
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("MiliMeters") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "MiliMeters"
                                conversionFactor.value = 0.001
                                convertUnits()
                            })
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
//            output box
                Box {
//                output button
                    Button(
                        onClick = { oExpanded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                    ) {
                        Text(text = outputUnit, color = Color.White)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow down")
                    }
                    DropdownMenu(expanded = oExpanded,
                        onDismissRequest = { oExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("CentiMeters") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "CentiMeters"
                                oconversionFactor.value = 0.01
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("Meters") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Meters"
                                oconversionFactor.value = 1.00
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Feet"
                                oconversionFactor.value = 0.3048
                                convertUnits()
                            })
                        DropdownMenuItem(
                            text = { Text("MiliMeters") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "MiliMeters"
                                oconversionFactor.value = 0.001
                                convertUnits()
                            })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Result: $outputValue",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF03DAC5),
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun UnitConverterPreview() {
        UnitConverter()
    }

