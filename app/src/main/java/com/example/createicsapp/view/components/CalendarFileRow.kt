package com.example.createicsapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.createicsapp.data.Calendar

@Composable
fun CalendarFileRow(
    calendar: Calendar,
    onClickRow: (Calendar) -> Unit,
    onClickDelete: (Calendar) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            ,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { onClickRow(calendar) }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = calendar.title)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onClickDelete(calendar) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")

            }
        }
    }

}

@Preview
@Composable
fun CalendarRowPreview() {
    CalendarFileRow(
        calendar = Calendar(
            title = "タイトル",
            startTime = "202010100101",
            endTime = "203001011010",
            place = "場所",
            description = "詳細"
        ),
        onClickRow = {},
        onClickDelete = {}
    )
}