package good.damn.editor.dd.prompt

import android.os.Process
import android.widget.TextView
import java.nio.charset.StandardCharsets

class Terminal(
    private val builder: StringBuilder
) {

    private val cmdCat = CommandCat(
        builder
    )

    private val cmdAtouAscii = CommandAtouAscii(
        builder
    )

    private val cmdAtouUnicode = CommandAtouUnicode(
        builder
    )

    private val cmdLs = CommandLs(
        builder
    )

    private val cmdRm = CommandRm(
        builder
    )

    private val cmdTail = CommandTail(
        builder
    )

    private val cmdMkdir = CommandMkdir(
        builder
    )

    private val cmdTouch = CommandTouch(
        builder
    )

    private val cmdRename = CommandRename(
        builder
    )

    fun enter(
        prompt: String
    ) {
        if (prompt.isBlank()) {
            return
        }

        val split = prompt.split(
            "\\s+".toRegex()
        )

        when (split[0]) {
            "rename" -> {
                cmdRename.prompt(
                    split,
                    1
                )
            }
            "touch" -> {
                cmdTouch.prompt(
                    split,
                    1
                )
            }
            "mkdir" -> {
                cmdMkdir.prompt(
                    split,
                    1
                )
            }
            "rm" -> {
                cmdRm.prompt(
                    split,
                    1
                )
            }
            "tail" -> {
                cmdTail.prompt(
                    split,
                    1
                )
            }
            "clear" -> {
                builder.clear()
            }
            "ls" -> {
                cmdLs.prompt(
                    split,
                    1
                )
            }
            "atouAsc" -> {
                cmdAtouAscii.prompt(
                    split,
                    1
                )
            }

            "atouUni" -> {
                cmdAtouUnicode.prompt(
                    split,
                    1
                )
            }

            "cat" -> {
                cmdCat.prompt(
                    split,
                    1
                )
            }
            else -> {
                runtimePrompt(
                    split.toTypedArray()
                )
            }
        }

    }

    private inline fun runtimePrompt(
        prompt: Array<String>
    ) {
        val process = try {
            Runtime.getRuntime().exec(
                prompt
            )
        } catch (e: Exception) {
            builder.append(
                "Invalid operation \n"
            )
            return
        }

        val inpStream = process.inputStream
        val errStream = process.errorStream

        inpStream.readBytes().apply {
            if (isEmpty()) {
                return@apply
            }

            builder.append(
                String(
                    this,
                    StandardCharsets.UTF_8
                )
            )
        }

        errStream.readBytes().apply {
            if (isEmpty()) {
                return@apply
            }

            builder.append(
                "ERROR: 2 "
            )
            builder.append(
                String(
                    this,
                    StandardCharsets.UTF_8
                )
            )

            builder.append("\n")
        }

    }

}