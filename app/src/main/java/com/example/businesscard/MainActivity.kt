package com.example.businesscard

import android.content.res.Configuration
import android.graphics.Paint.Align
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                        painterResource(R.drawable.qr_code),
                        background = colorResource(R.color.lilac),
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    )
                }
            }
        }
    }

    @Composable
    fun BusinessCard(
        name: String,
        email: String,
        phone: String,
        linkedin: String,
        primaryPicture: Painter,
        secondaryPicture: Painter,
        background: Color,
        modifier: Modifier
    ) {
        val configuration = LocalConfiguration.current
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                BusinessCardHorizontal(name, email, phone, linkedin,
                    primaryPicture, secondaryPicture, background, modifier)
            }
            else -> {
                BusinessCardVertical(name, email, phone, linkedin,
                    primaryPicture, secondaryPicture, background, modifier)
            }
        }
    }


    @Composable
    fun BusinessCardHorizontal(
        name: String,
        email: String,
        phone: String,
        linkedin: String,
        primaryPicture: Painter,
        secondaryPicture: Painter,
        background: Color,
        modifier: Modifier
    ) {
        var profileIsToggled: Int by rememberSaveable { mutableStateOf(0)}
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .horizontalScroll(rememberScrollState())
//                .padding(start = 60.dp, end = 90.dp)
        ){
            if(profileIsToggled == 0) {
                Profile(
                    name,
                    primaryPicture,
                    { profileIsToggled = (profileIsToggled + 1) % 2 },
                    Modifier.weight(1.5f)
                )
            } else {
                Profile(
                    name,
                    secondaryPicture,
                    { profileIsToggled = (profileIsToggled + 1) % 2 },
                    Modifier.weight(1.5f)
                )
            }
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
                painterResource(R.drawable.qr_code),
                background = Color(0xFFEADDFF),
                Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun BusinessCardVertical(
        name: String,
        email: String,
        phone: String,
        linkedin: String,
        primaryPicture: Painter,
        secondaryPicture: Painter,
        background: Color,
        modifier: Modifier
    ) {
        //TODO hoist profileIsToggled to a single variable in BusinessCard
        var profileIsToggled: Int by rememberSaveable { mutableStateOf(0)}
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(start = 60.dp, end = 60.dp, top = 100.dp, bottom = 100.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if(profileIsToggled == 0) {
                Profile(
                    name,
                    primaryPicture,
                    { profileIsToggled = (profileIsToggled + 1) % 2 },
                    Modifier.padding(bottom = 32.dp)
                )
            } else {
                Profile(
                    name,
                    secondaryPicture,
                    { profileIsToggled = (profileIsToggled + 1) % 2 },
                    Modifier.padding(bottom = 32.dp)
                )
            }
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
                painterResource(R.drawable.qr_code),
                background = Color(0xFFEADDFF),
                Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun Profile(
        name: String,
        picture: Painter,
        onClick: () -> Unit,
        modifier: Modifier
    ) {
//        var nameField: String by remember { mutableStateOf(name)}
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(4.dp),
//                shape = CircleShape,
                modifier = Modifier
                        .size(208.dp)
            ){
                Image(
                    painter = picture,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .requiredSize(208.dp)
                )
            }
//            TextField(
//                value = nameField,
//                singleLine = true,
//                textStyle = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
//                onValueChange = { nameField = it },
//                label = {},
//                modifier = Modifier.padding(top = 248.dp)
//            )
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
