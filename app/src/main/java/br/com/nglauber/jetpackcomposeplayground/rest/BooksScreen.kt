package br.com.nglauber.jetpackcomposeplayground.rest

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.rest.model.Book
import br.com.nglauber.jetpackcomposeplayground.rest.model.RequestState
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Stable
class BookScreenState {
    var selectedTab by mutableStateOf(0)
    var booksFavorites = mutableStateListOf<Book>()
    var bookToDelete by mutableStateOf<Book?>(null)
}

@ExperimentalPagerApi
@Composable
fun BooksScreen() {
    val viewModel: BooksViewModel = viewModel()
    LaunchedEffect(viewModel) {
        viewModel.loadBooks()
    }
    BooksScreenContent(viewModel.booksList)

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }

}

@ExperimentalPagerApi
@Composable
fun BooksScreenContent(
    booksResultFlow: StateFlow<RequestState<List<Book>>>
) {
    val context = LocalContext.current
    val listBookResult = booksResultFlow.collectAsState()
    val screenState by remember { mutableStateOf(BookScreenState()) }
    val scrollState0 = rememberLazyListState()
    val scrollState1 = rememberLazyListState()
    val pagerState = rememberPagerState(initialPage = 0)

    Scaffold(
        topBar = {
            BookTopBar(pagerState, screenState.selectedTab)
        },
        content = {
            when (val result = listBookResult.value) {
                is RequestState.Loading -> {
                    Loading()
                }
                is RequestState.Error -> {
                    ErrorMessage()
                }
                is RequestState.Success -> {
                    BooksScreenTabs(
                        context = context,
                        screenState = screenState,
                        books = result.data,
                        pagerState = pagerState,
                        scrollStates = arrayOf(scrollState0, scrollState1)
                    )
                }
            }
        }
    )
    if (screenState.bookToDelete != null) {
        DeleteFavoriteBookDialog(screenState)
    }
}

@ExperimentalPagerApi
@Composable
fun BookTopBar(pagerState: PagerState, selectedTab: Int) {
    val coroutineScope = rememberCoroutineScope()

    Surface(elevation = AppBarDefaults.TopAppBarElevation) {
        Column {
            TopAppBar(
                title = { Text("Books") },
                elevation = 0.dp
            )
            BooksTabs(
                selectedTab = selectedTab,
                onSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                pagerState = pagerState
            )
        }
    }
}

@Composable
fun ErrorMessage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Error!", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Loading() {
    val resources = LocalContext.current.resources

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                resources.getString(R.string.msg_loading),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BooksScreenTabs(
    context: Context,
    screenState: BookScreenState,
    books: List<Book>,
    pagerState: PagerState,
    vararg scrollStates: LazyListState
) {
    Column {
        HorizontalPager(state = pagerState, count = 2) { page ->
            when (page) {
                0 -> BooksList(
                    books,
                    action = { book: Book ->
                        if (screenState.booksFavorites.contains(book)) return@BooksList
                        screenState.booksFavorites.add(book)
                        Toast.makeText(
                            context,
                            context.resources.getString(
                                R.string.msg_added_favorites,
                                book.title
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    scrollState = scrollStates[0]
                )
                1 -> BooksList(
                    screenState.booksFavorites,
                    action = { book: Book ->
                        screenState.bookToDelete = book
                    },
                    scrollState = scrollStates[1]
                )
            }
        }
    }
}

@Composable
private fun DeleteFavoriteBookDialog(
    screenState: BookScreenState
) {
    val resources = LocalContext.current.resources
    screenState.bookToDelete?.let { book ->
        DeleteFavBookDialog(
            resources,
            book,
            onConfirm = { bookToDelete: Book ->
                screenState.booksFavorites.remove(bookToDelete)
                screenState.bookToDelete = null
            },
            onDismiss = {
                screenState.bookToDelete = null
            }
        )
    }
}

@ExperimentalPagerApi
@Composable
fun BooksTabs(
    selectedTab: Int,
    onSelected: (Int) -> Unit,
    pagerState: PagerState
) {
    val resources = LocalContext.current.resources
    TabRow(
        selectedTabIndex = selectedTab,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        tabs = {
            listOf(
                resources.getString(R.string.tab_books),
                resources.getString(R.string.tab_favorites)
            ).mapIndexed { index, string ->
                Tab(
                    content = { Text(string, modifier = Modifier.padding(16.dp)) },
                    selected = selectedTab == index,
                    onClick = {
                        onSelected(index)
                    }
                )
            }
        }
    )
}

@Composable
fun BooksList(
    books: Collection<Book>?,
    scrollState: LazyListState,
    action: (Book) -> Unit
) {
    val resources = LocalContext.current.resources
    if (books == null || books.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                resources.getString(R.string.msg_book_list_empty),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }
    LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
        items(books.toList(), key = { book -> book.title }) {
            BookItem(it, action)
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    action: (Book) -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                action(book)
            }
    ) {
        BookItemContent(book)
    }
}

@Composable
fun BookItemContent(
    book: Book
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(book.coverUrl),
            contentDescription = "",
            modifier = Modifier.size(96.dp, 144.dp),
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = LocalContext.current.resources.getString(
                    R.string.book_info_year_pages,
                    book.year,
                    book.pages
                ),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun DeleteFavBookDialog(
    resources: Resources,
    book: Book,
    onConfirm: (Book) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = resources.getString(
                    R.string.msg_fav_delete_title
                ),
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Text(
                text = resources.getString(
                    R.string.msg_fav_delete_message, book.title
                ),
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(book)
                    onDismiss()
                }
            ) {
                Text(
                    resources.getString(
                        R.string.msg_fav_delete_confirm
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    resources.getString(
                        R.string.msg_fav_delete_cancel
                    )
                )
            }
        }
    )
}