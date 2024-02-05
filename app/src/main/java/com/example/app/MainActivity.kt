package com.example.app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private lateinit var mService: CalculadoraService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CalculadoraService.CalculadoraBinder
            mService = binder.getService()
            mBound = true
            Toast.makeText(this@MainActivity.baseContext, "Bind feito com sucesso", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            var conteudoVisor by remember {
                mutableStateOf("")

            }
            AppTheme {
                Column {
                    Visor(conteudoVisor )
                    Teclado(/*mService,*/{mBound}) { novoTexto ->
                        conteudoVisor = novoTexto
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

//        Intent(this,CalculadoraService::class.java).also{
//                intent -> bindService(intent,connection, Context.BIND_AUTO_CREATE)
//        }
        var teste = bindService(Intent(this,CalculadoraService::class.java),connection, Context.BIND_AUTO_CREATE)
        if(teste)
            Toast.makeText(this@MainActivity.baseContext, "Bind feito com sucesso", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this@MainActivity.baseContext, "Bind não foi concluido", Toast.LENGTH_SHORT).show()

//        if(mBound)
//            mService.digitos("2+5-9/7");
    }
}

@Composable
fun Visor(conteudoVisor: String){
    Card (modifier = Modifier
        .fillMaxHeight(0.3f)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(20.dp)
    )
    {
        Box (modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ){
            Text(text = conteudoVisor, fontSize = 50.sp, maxLines = 1,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        rememberScrollState()

                    ))
        }
    }
}

@Composable
fun Teclado(/*mService: CalculadoraService,*/checkBind : ()->Boolean ,conteudoVisor:(String)->Unit){
    var textoTemporario by remember { mutableStateOf("") }
    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(top = 50.dp)

    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)){
            val context = LocalContext.current
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Botao("AC") {
                        textoTemporario = ""
                        conteudoVisor(textoTemporario)
                    }
                    Botao("( )") {}
                    Botao("%") {
                        textoTemporario += "%"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("/") {
                        textoTemporario += "/"
                        conteudoVisor(textoTemporario)
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Botao("7") {
                        textoTemporario += "7"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("8") {
                        textoTemporario += "8"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("9") {
                        textoTemporario += "9"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("x") {
                        textoTemporario += "x"
                        conteudoVisor(textoTemporario)
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Botao("4") {
                        textoTemporario += "4"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("5") {
                        textoTemporario += "5"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("6") {
                        textoTemporario += "6"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("-") {
                        textoTemporario += "-"
                        conteudoVisor(textoTemporario)
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Botao("1") {
                        textoTemporario += "1"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("2") {
                        textoTemporario += "2"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("3") {
                        textoTemporario += "3"
                        conteudoVisor(textoTemporario)
                    }
                    Botao("+") {
                        textoTemporario += "+"
                        conteudoVisor(textoTemporario)
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Botao("0") {
                        textoTemporario += "0"
                        conteudoVisor(textoTemporario)
                    }
                    Botao(",") {
                        textoTemporario += ","
                        conteudoVisor(textoTemporario)
                    }
                    Botao("<-") {
                        textoTemporario = textoTemporario.dropLast(1)
                        conteudoVisor(textoTemporario)
                    }
                    Botao("=") {
//                        if(checkBind()) {
//                            textoTemporario = mService.digitos(textoTemporario).toString()

                        Toast.makeText(context, "Bind foi feito?" + checkBind(), Toast.LENGTH_SHORT).show()
                            conteudoVisor(textoTemporario)
//                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Botao(
    tecla: String,
    modifier: Modifier = Modifier,
    callback: () -> Unit
){
    Card (modifier = modifier
        .clickable { callback() }
        .height(90.dp)
        .width(90.dp),
        shape = RoundedCornerShape(100)
    ){
        Box(modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxSize(),
            contentAlignment = Alignment.Center){
                Text(text = tecla, color = Color.White,
                    fontSize = 50.sp, textAlign = TextAlign.Center)
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Green,
                    fontSize = 50.sp
                )
            ){
                append("H")
            }
            append("ello $name!")
        },
        modifier = modifier,
        fontSize = 30.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Botao(tecla = "2") { }
}