package good.damn.editor.dd.prompt

import android.webkit.MimeTypeMap
import java.io.File

fun File.getMimeType(): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(
        path
    ) ?: return null

    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(
        extension
    )
}