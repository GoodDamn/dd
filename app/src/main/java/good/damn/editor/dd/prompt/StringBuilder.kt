package good.damn.editor.dd.prompt

inline fun StringBuilder.appendError(
    code: Int,
    msg: String
) {
    append(
        "ERROR: $code "
    )

    append(
        msg
    )

    append("\n")
}

inline fun StringBuilder.appendMessage(
    msg: String
) {
    append(
        msg
    )
    append("\n")
}