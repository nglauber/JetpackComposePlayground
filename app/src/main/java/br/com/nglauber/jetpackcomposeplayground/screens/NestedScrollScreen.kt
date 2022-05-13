package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.rest.model.RequestState
import br.com.nglauber.jetpackcomposeplayground.rest2.DogsViewModel
import br.com.nglauber.jetpackcomposeplayground.rest2.model.Doggy
import coil.compose.rememberAsyncImagePainter

@Composable
fun NestedScrollScreen(viewModel: DogsViewModel) {
    val dogsRequest by viewModel.dogsList.collectAsState()
    when (val dogsState = dogsRequest) {
        is RequestState.Idle,
        is RequestState.Loading ->
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is RequestState.Error -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Oops! Something went wrong...")
                Button(onClick = { viewModel.loadDogs() }) {
                    Text(text = "Try again")
                }
            }
        }
        is RequestState.Success -> {
            NestedScrollScreenContent(dogsState.data)
        }
    }
}

@Composable
private fun NestedScrollScreenContent(dogs: List<Doggy>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        // My Books section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("My Dogs", style = MaterialTheme.typography.h4)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(dogs) { item ->
                        DogCardSmall(
                            item,
                            modifier = Modifier
                                .padding(4.dp)
                                .height(120.dp)
                                .width(90.dp)
                                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp)),
                        )
                    }
                }
            }

        }
        item {
            Text("List of Dogs", style = MaterialTheme.typography.h4)
        }
        // Turning the list in a list of lists of two elements each
        items(dogs.windowed(2, 2, true)) { sublist ->
            Row(Modifier.fillMaxWidth()) {
                sublist.forEach { item ->
                    DogCardBig(
                        item,
                        Modifier
                            .height(150.dp)
                            .fillParentMaxWidth(.5f)
                            .padding(4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun DogCardSmall(dog: Doggy, modifier: Modifier) {
    Box(modifier) {
        Image(
            painter = rememberAsyncImagePainter(model = dog.url),
            contentDescription = dog.name,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DogCardBig(dog: Doggy, modifier: Modifier) {
    Box(modifier) {
        Image(
            painter = rememberAsyncImagePainter(model = dog.url),
            contentDescription = dog.name,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .align(Alignment.BottomEnd)
                .background(Color.LightGray, shape = RoundedCornerShape(topStart = 16.dp))
                .padding(8.dp)
        ) {
            Text(text = dog.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = dog.breedGroup ?: "---", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}