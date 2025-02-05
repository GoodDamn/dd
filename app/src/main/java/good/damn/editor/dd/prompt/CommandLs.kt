package good.damn.editor.dd.prompt

import android.os.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

class CommandLs(
    private val builder: StringBuilder
) {

    fun prompt(
        arg: List<String>,
        off: Int
    ) {
        var offset = off

        val showAttrs = if (arg.size > 1) {
            arg[offset] == "-l"
        } else false

        val subPath = if (showAttrs) {
            ""
        } else if (arg.size > 1) {
            arg[offset]
        } else ""

        val file = File(
            "${Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ).path}$subPath"
        )

        var i = 0

        file.listFiles()?.forEach {
            builder.appendMessage(
                "$i: ${it.name}${if (it.isDirectory) "/" else ""}"
            )
            if (showAttrs) {
                builder.appendMessage(
                    it.getMimeType() ?: ""
                )
                builder.appendMessage(
                    "${it.length()} bytes; modified time: ${it.lastModified().toDate()}"
                )
            }
            builder.appendMessage("----")
            i++
        }

    }
}