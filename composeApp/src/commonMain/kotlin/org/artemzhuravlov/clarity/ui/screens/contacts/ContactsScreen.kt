package org.artemzhuravlov.clarity.ui.screens.contacts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.artemzhuravlov.clarity.navigation.contacts.IContactsScreenComponent
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.ic_phone_call
import org.artemzhuravlov.clarity.ui.composables.ContentDivider
import org.artemzhuravlov.clarity.ui.utils.makePhoneCall
import org.artemzhuravlov.clarity.ui.utils.openTelegramChat
import org.artemzhuravlov.clarity.ui.utils.openViberChat
import org.artemzhuravlov.clarity.ui.utils.sendEmail
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ContactsScreen(component: IContactsScreenComponent) {
    val scrollState = rememberScrollState()

    val mainPhoneNumber = "+00 000 000 00 00 0"
    val medicalPhoneNumberOne = "+00 000 000 00 00 1"
    val medicalPhoneNumberTwo = "+00 000 000 00 00 2"
    val touristPhoneNumber = "+00 000 000 00 00 3"
    var makePhoneCallTo by remember {
        mutableStateOf("")
    }

    var sendEmailTo by remember {
        mutableStateOf("")
    }

    var isOpenTelegram by remember {
        mutableStateOf(false)
    }

    var isOpenViber by remember {
        mutableStateOf(false)
    }

    if (makePhoneCallTo.isNotBlank()) {
        makePhoneCall(makePhoneCallTo)
        makePhoneCallTo = ""
    }

    if (sendEmailTo.isNotBlank()) {
        sendEmail(sendEmailTo)
        sendEmailTo = ""
    }

    if (isOpenTelegram) {
        openTelegramChat()
        isOpenTelegram = false
    }

    if (isOpenViber) {
        openViberChat()
        isOpenViber = false
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactsTile(title = mainPhoneNumber, modifier = Modifier.padding(16.dp)) {
            makePhoneCallTo = mainPhoneNumber
        }
        ContentDivider("Додаткові контакти для отримання страхової допомоги")
        ExpandableListWithAnimation(
            modifier = Modifier.padding(16.dp),
            text = "Медичне страхування"
        ) {
            HorizontalDivider()
            Text(
                text = "Доступне тільки на території України. Для стархової допомоги зателофонуйте " +
                        "на один із запропонованих номерів:",
                style = TextStyle(color = Color.Black, fontSize = 14.sp),
                modifier = Modifier.padding(top = 6.dp)
            )
            ContactsTileDescription(
                title = medicalPhoneNumberOne,
                iconRes = Res.drawable.ic_phone_call,
                description = "Цілодобово. Вартість дзвінка згідно з тарифами вашого мобільного оператора"
            ) {
                makePhoneCallTo = medicalPhoneNumberOne
            }
            ContactsTileDescription(
                title = medicalPhoneNumberTwo,
                iconRes = Res.drawable.ic_phone_call,
                description = "Запит, щоб перетелефонував оператор. Безкоштовно зі стаціонарних і " +
                        "мобільних телефонів"
            ) {
                makePhoneCallTo = medicalPhoneNumberTwo
            }
        }

        ExpandableListWithAnimation(
            modifier = Modifier.padding(16.dp),
            text = "Туристичне страхування"
        ) {
            HorizontalDivider()
            Text(
                text = "Для страхової допомоги почніть чат в одному з чат-ботів за посиланням нижче або телефонуйте:",
                style = TextStyle(color = Color.Black, fontSize = 14.sp),
                modifier = Modifier.padding(top = 6.dp)
            )
            ContactsTileDescription(
                title = touristPhoneNumber,
                iconRes = Res.drawable.ic_phone_call,
                description = "Цілодобово. Вартість дзвінка згідно з тарифами вашого мобільного оператора"
            ) {
                makePhoneCallTo = touristPhoneNumber
            }
            ContactsTileDescription(
                title = "Чат Telegram",
                iconRes = Res.drawable.ic_phone_call,
            ) {
                isOpenTelegram = true
            }
            ContactsTileDescription(
                title = "Чат Viber",
                iconRes = Res.drawable.ic_phone_call,
            ) {
                isOpenViber = true
            }
            ContactsTileDescription(
                title = "test@test.test",
                iconRes = Res.drawable.ic_phone_call,
            ) {
                sendEmailTo = mainPhoneNumber
            }
        }
    }
}

@Composable
fun ContactsTile(title: String, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier.then(modifier),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        )
    ) {
        ContactsTileDescription(
            title = title,
            iconRes = Res.drawable.ic_phone_call,
            description = "Загальна гаряча лінія. Цілодобово, безкоштовно зі стаціонарних і " +
                    "мобільних телефонів в Україні"
        ) {
            onClick?.invoke()
        }
    }
}

@Composable
private fun ContactsTileDescription(title: String, iconRes: DrawableResource, description: String = "", onClick: (() -> Unit)? = null) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
        onClick?.invoke()
    }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
        Column(modifier = Modifier.padding(end = 8.dp)) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = ""
            )
        }
        Column {
            Text(text = title, style = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp))
            if (description.isNotBlank()) {
                Text(text = description, style = TextStyle(color = Color.Gray, fontSize = 12.sp))
            }
        }
    }
}

@Composable
fun ExpandableListWithAnimation(
    modifier: Modifier = Modifier,
    text: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var degrees by remember { mutableFloatStateOf(0f) }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isExpanded = !isExpanded
                    degrees = if (isExpanded) 180f else 0f
                }
        ) {
            Row(Modifier.padding(10.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(10f),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700))
                )

                Image(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "",
                    modifier = Modifier.weight(1f).size(34.dp).rotate(degrees)
                )
            }

            AnimatedVisibility(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                visible = isExpanded
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    content()
                }
            }
        }
    }

}
