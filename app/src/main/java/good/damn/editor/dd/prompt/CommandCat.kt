package good.damn.editor.dd.prompt

import android.os.Environment
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.math.log

class CommandCat(
    private val builder: StringBuilder
) {

    private val buffer = ByteArray(8192)

    fun prompt(
        arg: List<String>,
        off: Int
    ) {
        val ignoresError = arg[off] == "-s"
        var offset = off
        if (ignoresError) {
            offset++
        }
        val catFile = FileOutputStream(
            File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
                ),
                "cat.txt"
            ).apply {
                if (!exists() && createNewFile()) {
                    builder.appendMessage(
                        "$name is created"
                    )
                }
            }
        )

        for (i in offset until arg.size) {
            val file = try {
                File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS
                    ),
                    arg[i]
                )
            } catch (e: Exception) {
                if (ignoresError) {
                    continue
                }
                builder.appendError(
                    4,
                    e.message.toString()
                )

                break
            }

            if (!file.exists()) {
                if (ignoresError) {
                    continue
                }
                builder.appendError(
                    5,
                    "${arg[i]} doesn't exist"
                )
                break
            }

            if (file.isDirectory) {
                builder.appendError(
                    6,
                    "${arg[i]} is directory"
                )

                if (ignoresError) {
                    continue
                }
                break
            }

            val fis = FileInputStream(
                file
            )
            var n: Int
            while (true) {
                n = fis.read(buffer)
                if (n == -1) {
                    break
                }
                catFile.write(
                    buffer,
                    0,
                    n
                )
            }

            fis.close()
        }

        catFile.close()
    }

}