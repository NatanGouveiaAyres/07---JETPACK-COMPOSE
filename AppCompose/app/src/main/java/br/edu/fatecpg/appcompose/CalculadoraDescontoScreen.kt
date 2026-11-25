package br.edu.fatecpg.appcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CalculadoraDescontoScreen() {

    // Estados dos campos
    var preco by remember { mutableStateOf("") }
    var percentual by remember { mutableStateOf("") }

    // Estado do resultado e da mensagem de erro
    var resultado by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Calculadora de Desconto",
                style = MaterialTheme.typography.headlineMedium
            )

            // Campo do preço
            OutlinedTextField(
                value = preco,
                onValueChange = {
                    preco = it
                    resultado = ""
                    erro = ""
                },
                label = { Text("Preço do produto") },
                placeholder = { Text("Ex: 500,00") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo do percentual de desconto
            OutlinedTextField(
                value = percentual,
                onValueChange = {
                    percentual = it
                    resultado = ""
                    erro = ""
                },
                label = { Text("Percentual de desconto") },
                placeholder = { Text("Ex: 15") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Botão de calcular
            Button(
                onClick = {

                    val valorPreco = preco
                        .trim()
                        .replace(",", ".")
                        .toDoubleOrNull()

                    val valorPercentual = percentual
                        .trim()
                        .replace(",", ".")
                        .toDoubleOrNull()

                    // Validação dos campos
                    if (valorPreco == null || valorPercentual == null) {
                        erro = "Preencha os dois campos com valores válidos."
                        resultado = ""

                    } else if (valorPreco < 0) {
                        erro = "O preço não pode ser negativo."
                        resultado = ""

                    } else if (valorPercentual !in 0.0..100.0) {
                        erro = "O desconto deve estar entre 0% e 100%."
                        resultado = ""

                    } else {

                        // Chama a função de cálculo
                        val precoFinal = calcularPrecoComDesconto(
                            valorPreco,
                            valorPercentual
                        )

                        // Formata o resultado como moeda brasileira
                        val formato = NumberFormat.getCurrencyInstance(
                            Locale("pt", "BR")
                        )

                        resultado = formato.format(precoFinal)
                        erro = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULAR")
            }

            // Exibição de erros
            if (erro.isNotEmpty()) {
                Text(
                    text = erro,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Exibição do resultado
            if (resultado.isNotEmpty()) {
                Text(
                    text = "Preço final: $resultado",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
