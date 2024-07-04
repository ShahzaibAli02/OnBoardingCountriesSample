import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.Country
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import onboardingcountriessample.composeapp.generated.resources.Res
import onboardingcountriessample.composeapp.generated.resources.germany
import onboardingcountriessample.composeapp.generated.resources.islamabad
import onboardingcountriessample.composeapp.generated.resources.italy
import onboardingcountriessample.composeapp.generated.resources.london
import onboardingcountriessample.composeapp.generated.resources.rubic_bold
import onboardingcountriessample.composeapp.generated.resources.star
import onboardingcountriessample.composeapp.generated.resources.star_blank
import kotlin.random.Random


val dummyCountries = listOf(
        Country(
                image = Res.drawable.germany,
                rating = Random.nextInt(1, 6),
                countryName = "Germany",
                aboutCountry = "Germany is known for its rich cultural history and engineering prowess."
        ),
        Country(
                image =  Res.drawable.italy,
                rating = Random.nextInt(1, 6),
                countryName = "Italy",
                aboutCountry = "Italy is famous for its art, architecture, and delicious cuisine."
        ),
        Country(
                image =  Res.drawable.london,
                rating = Random.nextInt(1, 6),
                countryName = "London",
                aboutCountry = "London is the capital city of the United Kingdom, known for its diversity and iconic landmarks."
        ),
        Country(
                image =  Res.drawable.islamabad,
                rating = Random.nextInt(1, 6),
                countryName = "Pakistan",
                aboutCountry = "Pakistan is a country in South Asia known for its rich cultural heritage and beautiful landscapes."
        )
)
@OptIn(ExperimentalFoundationApi::class) @Composable @Preview fun App()
{
    MaterialTheme {


        val pagerState = rememberPagerState(pageCount = {
            dummyCountries.size
        }) // Define the number of pages

        val typography = MaterialTheme.typography
        val mFont = FontFamily(Font(Res.font.rubic_bold))
        HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
        ) { index ->
            Box(modifier = Modifier.fillMaxSize()) {
                ImageWithEffect(index)
                Bottom(
                        index,
                        mFont,
                        typography
                )
            }
        }
    }

}

@Composable private fun BoxScope.Bottom(
    index: Int,
    mFont: FontFamily,
    typography: Typography,
)
{

    val offsetY = remember { androidx.compose.animation.core.Animatable(500f) }

    LaunchedEffect(Unit) {
        launch {
            offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                    )
            )
        }
    }
    Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(start= 20.dp, bottom = 60.dp,end=50.dp).offset {
                IntOffset(
                        0,
                        offsetY.value.toInt()
                )
            }) {



        Text(
                text = dummyCountries[index].countryName,
                color = Color.White,
                fontFamily = mFont,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 35.sp,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            repeat(dummyCountries[index].rating)
            {
                Image(
                        painter = painterResource(Res.drawable.star),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                )
            }
            repeat(5-dummyCountries[index].rating)
            {
                Image(
                        painter = painterResource(Res.drawable.star_blank),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                )
            }

            Spacer(modifier = Modifier.width(5.dp))
            Text(
                    textAlign = TextAlign.Center,
                    text =  dummyCountries[index].rating.toFloat().toString(),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
            )

        }

        Spacer(modifier = Modifier.height(5.dp))
        Text(
                modifier = Modifier.alpha(0.7f),
                text =  dummyCountries[index].aboutCountry,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
        )


    }
}

@Composable fun ImageWithEffect(index: Int)
{
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for zooming in and out
    val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 3000,
                            easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            )
    )

    // Animation for moving left and right
    val offset by infiniteTransition.animateFloat(
            initialValue = -2f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 10000,
                            easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
            )
    )

    Box(
            modifier = Modifier.fillMaxSize().scale(scale)
    ) {
        Image(
                painter = painterResource(dummyCountries[index].image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
        )
    }
}