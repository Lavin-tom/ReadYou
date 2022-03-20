package me.ash.reader.ui.page.home.feeds

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.ash.reader.data.feed.Feed

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    text: String,
    feeds: List<Feed>,
    isExpanded: Boolean = true,
    groupOnClick: () -> Unit = {},
    feedOnClick: (feed: Feed) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(isExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.14f))
            .clickable { groupOnClick() }
            .padding(vertical = 22.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 28.dp),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Row(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.24f))
                    .clickable {
                        expanded = !expanded
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = if (expanded) "Expand Less" else "Expand More",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column {
                if (feeds.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                feeds.forEach { feed ->
                    FeedItem(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        name = feed.name,
                        important = feed.important ?: 0,
                    ) {
                        feedOnClick(feed)
                    }
                }
            }
        }
    }
}