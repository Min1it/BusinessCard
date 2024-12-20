package com.example.businesscard

import android.content.res.Configuration
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(
                        stringResource(R.string.name),
                        stringResource(R.string.email),
                        stringResource(R.string.phone),
                        stringResource(R.string.linkedin),
                        painterResource(R.drawable.profile_pic),
                        background = colorResource(R.color.lilac),
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    )
                }
            }
        }
    }

    @Composable
    fun BusinessCard(name: String, email: String, phone: String, linkedin: String,
                               picture: Painter, background: Color, modifier: Modifier) {
        val configuration = LocalConfiguration.current
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                BusinessCardHorizontal(name, email, phone, linkedin, picture, background, modifier)
            }
            else -> {
                BusinessCardVertical(name, email, phone, linkedin, picture, background, modifier)
            }
        }
    }


    @Composable
    fun BusinessCardHorizontal(name: String, email: String, phone: String, linkedin: String,
                             picture: Painter, background: Color, modifier: Modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(background)
//                .padding(start = 60.dp, end = 90.dp)
        ){
            Profile(name, picture, Modifier.weight(1.5f))
            ContactInfoCard(phone, email, linkedin, Modifier.weight(1.0f))
        }
    }

    @Preview(showBackground = true, widthDp = 1000, heightDp = 400)
    @Composable
    fun BusinessCardHorizontalPreview() {
        BusinessCardTheme {
            BusinessCardHorizontal(
                "Alice",
                "Alice@gmail.com",
                "123-456-7890",
                "linkedin.com/Alice",
                painterResource(R.drawable.profile_pic),
                background = Color(0xFFEADDFF),
                Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun BusinessCardVertical(name: String, email: String, phone: String, linkedin: String,
                     picture: Painter, background: Color, modifier: Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(start = 60.dp, end = 60.dp, top = 100.dp, bottom = 100.dp)
        ) {
            Profile(name, picture, Modifier.padding(bottom = 32.dp))
            ContactInfoCard(phone, email, linkedin, Modifier)
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun BusinessCardVerticalPreview() {
        BusinessCardTheme {
            BusinessCardVertical(
                "Alice",
                "Alice@gmail.com",
                "123-456-7890",
                "linkedin.com/Alice",
                painterResource(R.drawable.profile_pic),
                background = Color(0xFFEADDFF),
                Modifier.fillMaxSize()
            )
        }
    }


        @Composable
    fun Profile(name: String, picture: Painter, modifier: Modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier,
        ) {
            Image(
                painter = picture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(208.dp)
                    .border(BorderStroke(1.dp, Color.Black))
            )
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                text = name,
                modifier = Modifier.padding(top = 248.dp)
            )
        }
    }

    @Composable
    fun ContactInfoCard(phone: String, email: String, linkedin: String, modifier: Modifier){
       Column(
           horizontalAlignment = Alignment.Start,
           verticalArrangement = Arrangement.Center,
           modifier = modifier,
       ) {
           ContactInfo(painterResource(R.drawable.icon_phone), phone) //phone
           ContactInfo(painterResource(R.drawable.icon_email), email) //email
           ContactInfo(painterResource(R.drawable.icon_linkedin), linkedin) //linkedin
       }

    }

    @Preview(showBackground = true)
    @Composable
    fun ContactInfoCardPreview() {
        BusinessCardTheme {
            ContactInfoCard(
                "123-456-7890",
                "Alice@gmail.com",
                "www.linkedin.com/Alice",
                Modifier
            )
        }
    }
    @Composable
    fun ContactInfo(icon: Painter, info: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(2.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                contentScale = ContentScale.Fit, //try Crop
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)
            )

            Text(
                text = info,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun ContactInfoPreview() {
        BusinessCardTheme {
            ContactInfo(painterResource(R.drawable.icon_phone),"123-456-7890")
        }
    }





}
