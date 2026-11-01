package ar.edu.unicen.seminarioandroid2026.ui.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.net.Uri
import ar.edu.unicen.seminarioandroid2026.R

object IntentUtils {
     fun shareMovie(context: Context, title: String, overview: String) {
        val shareText = context.getString(R.string.share_message_format, title, overview)
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        val chooserIntent =
            Intent.createChooser(sendIntent, context.getString(R.string.share_chooser_title))
        context.startActivity(chooserIntent)
    }

     fun openHomepage(context: Context, homepageUrl: String){
        runCatching{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homepageUrl))
            context.startActivity(intent)
        }.onFailure {
            Toast.makeText(
                context,
                context.getString(R.string.error_no_browser),
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}