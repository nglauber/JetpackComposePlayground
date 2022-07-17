package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.util.AttributeSet
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import br.com.nglauber.jetpackcomposeplayground.R
import java.io.File
import java.io.FileOutputStream

private var jetCaptureView: ProfileCardView? = null

/*
* Based on example found in:
* https://medium.com/@vipulthawre/how-to-share-composable-as-bitmap-e207c2f299d4
*/
@Composable
fun ExportComposableScreen() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                elevation = 10.dp,
                cutoutShape = RoundedCornerShape(18.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {}
        },
        floatingActionButton = {
            ExtendedFloatingActionButton({
                Text(text = "Capture ProfileView", color = Color.White)
            },
                onClick = { capture(jetCaptureView) },
                shape = RoundedCornerShape(18.dp),
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null
                    )
                }
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ProfileUI()
        }
    }
}

@Composable
private fun ProfileUI() {
    AndroidView(modifier = Modifier.wrapContentSize(),
        factory = {
            jetCaptureView = ProfileCardView(it)
            jetCaptureView!!
        }
    )
}

@Composable
fun ProfileCard() {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(MaterialTheme.colors.primary)
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.dog),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Text(text = "Doguinho", style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewExportComposableScreen() {
    MaterialTheme {
        ExportComposableScreen()
    }
}

class ProfileCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        ProfileCard()
    }
}

fun generateBitmap(view: View): Bitmap {
    val b = Bitmap.createBitmap(
        view.width,
        view.height,
        Bitmap.Config.ARGB_8888
    )
    val c = Canvas(b)
    view.layout(
        view.left,
        view.top,
        view.right,
        view.bottom
    )
    view.draw(c)
    return b
}

fun capture(view: ProfileCardView?) {
    view?.let {
        val bitmap = generateBitmap(view)
        shareProfileCard(it.context, "test", bitmap)
    }
}

fun saveBitmapAndGetUri(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "testImg.jpg")
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        FileProvider.getUriForFile(
            context, "br.com.nglauber.jetpackcomposeplayground.provider", file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun shareProfileCard(context: Context, text: String?, bitmap: Bitmap?) {
    if (bitmap == null) return
    try {
        val imageUri = saveBitmapAndGetUri(context, bitmap)
        context.startActivity(
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_TEXT, text)
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}