package com.example.cloudyapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dropbox.core.android.Auth
import com.example.cloudyapp.ui.theme.CloudyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val credential = Auth.getDbxCredential()
        if (credential != null) {
            // credential содержит: accessToken, refreshToken, expiresAt и т.д.
            // ВАЖНО: Сохраните эти данные (например, в EncryptedSharedPreferences)
            tokenViewModel.saveDropboxAuthToken(credential)
        }
    }

}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val fileState by viewModel.filesFlow.collectAsStateWithLifecycle()
    Column() {
        val context = LocalContext.current
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(modifier = modifier, onClick = {
            startDropboxAuth(context)
        }) {
            Text("Нажми")
        }
        Button(modifier = modifier, onClick = {
            Log.d("TEST_FILES", fileState.toString())
        }) {
            Text("Получить данные")
        }
    }
}
private fun startDropboxAuth(context: Context) {
    Auth.startOAuth2Authentication(context, BuildConfig.DROPBOX_APP_KEY)
}

//private fun saveDropboxCredential(credential: DbxCredential) {
//    val prefs = getSharedPreferences("dropbox_prefs", MODE_PRIVATE)
//    // Сериализуем объект в JSON (SDK умеет это из коробки)
//    val json = DbxCredential.Writer.writeToString(credential)
//    prefs.edit().putString("credential_json", json).apply()
//}
//
//private fun loadDropboxCredential(): DbxCredential? {
//    val prefs = getSharedPreferences("dropbox_prefs", MODE_PRIVATE)
//    val json = prefs.getString("credential_json", null) ?: return null
//    // Десериализуем обратно
//    return DbxCredential.Reader.readField(json)
//}
//
//private fun initDropboxClient(credential: DbxCredential): DbxClientV2 {
//    val config = DbxRequestConfig.newBuilder("my-cool-app").build()
//
//    // Создаем клиент, передавая весь объект credential
//    // Теперь при каждом вызове API SDK проверит, не протух ли токен
//    return DbxClientV2(config, credential)
//}