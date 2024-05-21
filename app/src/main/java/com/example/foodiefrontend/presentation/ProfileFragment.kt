package com.example.foodiefrontend.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodiefrontend.R

class ProfileViewModel: ViewModel() {

}
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    @Composable
    fun ProfileContent(username: String) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Tu perfil", fontSize = 30.sp, textAlign = TextAlign.Left)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "GearIcon")
                }
            }
            Text(text = "ICONO FOTO DE PERFIL")
            Text(text = "$username", fontSize = 20.sp)
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Preferencias Nutricionales",
                    fontSize = 16.sp)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Configuración de familiares",
                    fontSize = 16.sp)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cerrar sesión",
                    fontSize = 16.sp)
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun HomeContentPreview() {
        ProfileContent("Usuario")
    }
}


