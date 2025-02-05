package good.damn.editor.dd.prompt

import android.os.Environment
import java.io.File

class CommandRename(
    private val builder: StringBuilder
) {

    fun prompt(
        arg: List<String>,
        off: Int
    ) {
        var offset = off

        if (arg.size <= 2) {
            builder.appendError(
                1,
                "Need a file name"
            )
            return
        }

        val from = try {
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


        if (!from.exists()) {
            builder.appendError(
                10,
                "${from.name} doesn't exists"
            )
            return;
        }

        try {
            from.renameTo(
                File(
                    from.parentFile,
                    arg[offset+1]
                )
            )
        } catch (e: Exception) {
            builder.appendError(
                2,
                e.message.toString()
            )
        }
    }
}