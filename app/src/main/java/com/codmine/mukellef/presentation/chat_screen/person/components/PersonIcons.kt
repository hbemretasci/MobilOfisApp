package com.codmine.mukellef.presentation.chat_screen.person.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MarkChatRead
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnReadMessages(messageCount: String) {
    BadgedBox(
        badge = {
            Badge { Text(messageCount) }
        }
    ) {
        Icon (
            modifier = Modifier
                .scale(1.2f,1.2f),
            imageVector = Icons.Default.Chat,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = UiText.StringResources(R.string.unread_icon_content_description).asString()
        )
    }
}

@Composable
fun ReadMessages() {
    Icon (
        modifier = Modifier
            .scale(1.2f,1.2f),
        imageVector = Icons.Default.MarkChatRead,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = UiText.StringResources(R.string.read_icon_content_description).asString()
    )
}