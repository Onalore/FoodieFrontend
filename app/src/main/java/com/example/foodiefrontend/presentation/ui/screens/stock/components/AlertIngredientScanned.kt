package com.example.foodiefrontend.presentation.ui.screens.stock.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.viewmodel.StockViewModel

@Composable
fun AlertIngredientScanned(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    codeEan: String,
) {
    val context = LocalContext.current
    val viewModel: StockViewModel = viewModel()

    LaunchedEffect(codeEan) {
        if (codeEan.isNotEmpty()) {
            viewModel.findProductByEan(codeEan)
        }
    }

    val productType by viewModel.productType.observeAsState()
    val error by viewModel.error.observeAsState()
    val addProductResult by viewModel.addProductResult.observeAsState()

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = stringResource(R.string.is_this_product),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = codeEan,
                    textAlign = TextAlign.Center
                )
                if (productType != null) {
                    Log.d("Product", "Detected Product: $productType")
                    Text(
                        text = productType?.description ?: "Producto desconocido",
                        textAlign = TextAlign.Center
                    )
                    productType?.unit?.let {
                        Text(
                            text = "Unidad: $it",
                            textAlign = TextAlign.Center
                        )
                    }
                    productType?.imageUrl?.let {
                        Image(
                            painter = rememberImagePainter(it),
                            contentDescription = null,
                            modifier = Modifier.size(128.dp)
                        )
                    }
                } else if (error != null) {
                    Text(
                        text = error ?: "Error desconocido",
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                } else {
                    Text(
                        text = "Cargando...",
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        dismissButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    onClick = {
                        navController.navigate("camera_screen")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = R.drawable.ic_retry,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                        if (productType != null) {
                            viewModel.addProductByEan(codeEan, 1, context) // Pasa el contexto aquí
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    icon = R.drawable.ic_check,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    containerColor = Color(0xFFE8BB66),
                    text = stringResource(R.string.enter_manually),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )

    LaunchedEffect(addProductResult) {
        addProductResult?.let {
            if (it) {
                Log.d("AddProduct", "Product successfully added to stock.")
                navController.navigate("stock_screen")
            } else {
                Log.d("AddProduct", "Failed to add product to stock.")
            }
        }
    }
}
