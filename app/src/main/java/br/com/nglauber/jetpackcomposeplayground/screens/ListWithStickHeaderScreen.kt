package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListWithStickHeaderScreen() {
    LazyColumn(Modifier.fillMaxSize()) {
        val grouped = names.groupBy { it.first() }
        grouped.entries.forEach { entry ->
            stickyHeader {
                Text(
                    text = entry.key.toString(),
                    color = Color(117, 137, 199),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xff8eb5f5)
                        )
                        .padding(8.dp)
                )
            }
            items(entry.value) { name ->
                Text(
                    text = name,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

val names = listOf(
    "Donny Defelice",
    "Floy Folson",
    "Christen Colson",
    "Eloise Ewert",
    "Carmella Cusack",
    "Eun Engles",
    "Anthony Ahmad",
    "Julius Jhonson",
    "Luisa Litchfield",
    "Garfield Gunnels",
    "Nathalie Nguyen",
    "Nita Null",
    "Enoch Everest",
    "Stanley Stanko",
    "Neda Natoli",
    "Johanna Jost",
    "Christel Cree",
    "Evelina Eis",
    "Jessika Joachim",
    "Emmitt Evers",
    "Gino Goff",
    "Celesta Callejas",
    "Leticia Larue",
    "Arnulfo Alaimo",
    "Cheri Clear",
    "Lemuel Lucchesi",
    "Merrilee Muldowney",
    "Kristel Kita",
    "Guy Greenwood",
    "Mana Marrufo",
    "Margarite Martello",
    "Kassie Kleven",
    "Chandra Cruze",
    "Lore Longwell",
    "Eugenie Ellinger",
    "Ettie Eames",
    "Tawna Tuley",
    "Bud Brien",
    "Donnell Damewood",
    "Janis Jerrell",
    "Toshia Tunstall",
    "Zachariah Zarrella",
    "Charity Cesario",
    "Odelia Ohler",
    "Sueann Seymore",
    "Andree Andrew",
    "Stephenie Spillane",
    "Trang Tacy",
    "Zenia Zaragosa",
    "Fran Foreman",
).sorted()
