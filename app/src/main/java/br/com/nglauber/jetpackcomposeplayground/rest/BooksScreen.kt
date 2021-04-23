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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.rest.model.Book
import com.google.accompanist.coil.rememberCoilPainter

@Stable
class BookScreenState {
    var selectedTab by mutableStateOf(0)
    var booksFavorites by mutableStateOf<MutableSet<Book>>(
        mutableSetOf()
    )
    var bookToDelete by mutableStateOf<Book?>(null)
}

@Composable
fun BooksScreen() {
    val viewModel: BooksViewModel = viewModel()
    LaunchedEffect(viewModel) {
        viewModel.loadBooks()
    }
    BooksScreenContent(viewModel.booksResult)
}

@Composable
fun BooksScreenContent(
    booksResultLiveData: LiveData<ListBookResult>
) {
    val context = LocalContext.current
    val resources = context.resources

    val listBookResult by booksResultLiveData.observeAsState()
    val screenState by remember { mutableStateOf(BookScreenState()) }
    val scrollState0 = rememberLazyListState()
    val scrollState1 = rememberLazyListState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Books") }) },
        content = {
            if (listBookResult == null || listBookResult?.loading == true) {
                Loading(resources)
            } else {
                BooksScreenTabs(
                    context = context,
                    screenState = screenState,
                    books = listBookResult?.books ?: emptyList(),
                    scrollStates = arrayOf(scrollState0, scrollState1)
                )
            }
        }
    )
    if (screenState.bookToDelete != null) {
        DeleteFavoriteBookDialog(resources, screenState)
    }
}

@Composable
fun Loading(resources: Resources) {
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

@Composable
fun BooksScreenTabs(
    context: Context,
    screenState: BookScreenState,
    books: List<Book>,
    vararg scrollStates: LazyListState
) {
    Column {
        BooksTabs(
            resources = context.resources,
            selectedTab = screenState.selectedTab,
            onSelected = { index ->
                screenState.selectedTab = index
            }
        )
        when (screenState.selectedTab) {
            0 -> BooksList(
                context.resources,
                books,
                action = { book: Book ->
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
                context.resources,
                screenState.booksFavorites,
                action = { book: Book ->
                    screenState.bookToDelete = book
                },
                scrollState = scrollStates[1]
            )
        }
    }
}

@Composable
private fun DeleteFavoriteBookDialog(
    resources: Resources,
    screenState: BookScreenState
) {
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

@Composable
fun BooksTabs(
    resources: Resources,
    selectedTab: Int,
    onSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTab,
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
    resources: Resources,
    books: Collection<Book>?,
    scrollState: LazyListState,
    action: (Book) -> Unit
) {
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
            painter = rememberCoilPainter(
                request = book.coverUrl,
                shouldRefetchOnSizeChange = { _, _ -> false },
            ),
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