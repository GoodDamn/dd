package good.damn.editor.dd.prompt

import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

class CommandTouch(
    private val builder: StringBuilder
) {

    fun prompt(
        arg: List<String>,
        off: Int
    ) {
        var offset = off

        if (arg.size <= 1) {
            builder.appendError(
                1,
                "Need a file name"
            )
            return
        }

        val file = try {
            File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
                ),
                arg[offset]
            )
        } catch (e: Exception) {
            builder.appendError(
                4,
                e.message.toString()
            )
            return
        }

        if (file.exists()) {
            builder.appendError(
                10,
                "${file.name} exists"
            )
            return;
        }

        try {
            file.createNewFile()
        } catch (e: Exception) {
            builder.appendError(
                2,
                e.message.toString()
            )
        }
    }
}