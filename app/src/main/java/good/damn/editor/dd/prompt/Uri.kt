package good.damn.editor.dd.prompt

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun Uri.extractFileName(
    context: Context
): String? {
    val p = path ?: return null
    val t = "primary:"
    val filePath = p.substring(
        p.indexOf(t) + t.length
    )
    val nameIndex = filePath.lastIndexOf(
        "/"
    )

    return if (nameIndex == -1)
        filePath
    else filePath.substring(
        nameIndex + 1
    )
}