package good.damn.editor.dd.prompt

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

class CommandAtouAscii(
    private val builder: StringBuilder
) {

    fun prompt(
        arg: List<String>,
        off: Int = 0
    ) {
        val ignoresReplace = arg[off] == "-i"
        var offset = off
        if (ignoresReplace) {
            offset++
        }
        val inputFile = getFile(arg[offset])
            ?: return

        val outFile = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ),
            arg[offset+1]
        ).apply {
            if (!exists() && createNewFile()) {
                builder.appendMessage(
                    "${arg[offset+1]} is created"
                )
            }
        }

        val fos = FileOutputStream(outFile)

        inputFile.readLines().forEach {
            fos.write(
                (it + "\n").toByteArray(
                    StandardCharsets.US_ASCII
                )
            )
        }

        fos.close()
    }

    private inline fun getFile(
        arg: String
    ): File? {
        val file = try {
            File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
                ),
                arg
            )
        } catch (e: Exception) {
            builder.appendError(
                4,
                e.message.toString()
            )
            return null
        }

        if (!file.exists()) {
            builder.appendError(
                5,
                "${arg} doesn't exist"
            )
            return null
        }

        if (file.isDirectory) {
            builder.appendError(
                6,
                "${arg} is directory"
            )
            return null
        }

        return file
    }

}