package org.artemzhuravlov.clarity.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.artemzhuravlov.clarity.navigation.main.IMainScreenComponent
import org.artemzhuravlov.clarity.navigation.main.MainScreenEvent
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.ic_map
import org.artemzhuravlov.clarity.resources.ic_phone_call
import org.artemzhuravlov.clarity.ui.composables.ContentDivider
import org.artemzhuravlov.clarity.ui.composables.MenuItem
import org.artemzhuravlov.clarity.ui.composables.PagerIndicator

@Composable
fun MainScreen(modifier: Modifier = Modifier, component: IMainScreenComponent) {
    val uiState by component.uiState.subscribeAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.notifications.isNotEmpty()) {
            val notificationsPagerState = rememberPagerState(pageCount = {
                uiState.notifications.size
            })

            ContentDivider(text = "Сповіщення")
            HorizontalPager(
                state = notificationsPagerState,
                beyondViewportPageCount = 2,
                pageSpacing = (-24).dp,
                contentPadding = PaddingValues(horizontal = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) { currentPage ->
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally).width(340.dp)
                        .height(80.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            uiState.notifications[currentPage].date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            maxLines = 1
                        )
                        Text(
                            uiState.notifications[currentPage].title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                PagerIndicator(pagerState = notificationsPagerState)
            }
        }

        if (uiState.offers.isNotEmpty()) {
            val offersPagerState = rememberPagerState(pageCount = {
                uiState.offers.size
            })

            ContentDivider(text = "Пропозиції")
            HorizontalPager(
                state = offersPagerState,
                beyondViewportPageCount = 2,
                pageSpacing = (-24).dp,
                contentPadding = PaddingValues(horizontal = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) { currentPage ->
                Column {
                    Card(
                        modifier = Modifier.align(Alignment.CenterHorizontally).width(340.dp)
                            .height(240.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        CoilImage(
                            imageModel = { uiState.offers[currentPage].imageUrl }, // loading a network image or local resource using an URL.
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center
                            ),
                            modifier = Modifier.fillMaxWidth().height(130.dp)
                        )

                        Column(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = uiState.offers[currentPage].title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.offers[currentPage].content,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = uiState.offers[currentPage].date,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                PagerIndicator(pagerState = offersPagerState)
            }
        }
        ContentDivider(text = "Швидкі дії")
        Card(
            modifier = Modifier.padding(horizontal = 14.dp),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            MenuItem(
                iconRes = Res.drawable.ic_phone_call,
                text = "Контакти"
            ) {
                component.handleEvent(MainScreenEvent.NavigateToContacts)
            }
            HorizontalDivider()
            MenuItem(
                iconRes = Res.drawable.ic_map,
                text = "Мапа медичних закладів"
            ) {
                component.handleEvent(MainScreenEvent.NavigateToClinicsMap)
            }
        }

    }
}
