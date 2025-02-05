package good.damn.editor.dd

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.dd.prompt.Terminal
import good.damn.editor.dd.prompt.appendMessage
import good.damn.editor.dd.prompt.extractFileName
import java.io.File
import java.io.FileOutputStream

class MainActivity
: AppCompatActivity(),
ActivityResultCallback<List<Uri>?>{

    private val mBuilder = StringBuilder()
    private val mTerminal = Terminal(
        mBuilder
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcher = registerForActivityResult(
            ActivityResultContracts.GetMultipleContents(),
            this
        )

        val context = this

        val promptText: EditText
        lateinit var outText: TextView

        LinearLayout(
            context
        ).apply {
            orientation = LinearLayout.VERTICAL

            EditText(
                context
            ).apply {
                promptText = this
                isSingleLine = true
                addView(
                    this,
                    -1,
                    -2
                )
            }

            Button(
                context
            ).apply {
                text = "enter"

                setOnClickListener {
                    mTerminal.enter(
                        promptText.text.toString()
                    )

                    outText.text = mBuilder.toString()
                }
                addView(
                    this,
                    -1,
                    -2
                )
            }

            Button(
                context
            ).apply {
                text = "copy"

                setOnClickListener {
                    launcher.launch(
                        "*/*"
                    )
                }

                addView(
                    this,
                    -1,
                    -2
                )
            }

            TextView(
                context
            ).apply {
                outText = this
                movementMethod = ScrollingMovementMethod()
                addView(this)
            }

            setContentView(this)
        }

    }

    override fun onActivityResult(
        result: List<Uri>?
    ) {
        if (result == null) {
            return
        }

        val buffer = ByteArray(8192)
        result.forEach {
            val file = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
                ),
                it.extractFileName(
                    this
                ) ?: "temp"
            )

            if (!file.exists() && file.createNewFile()) {
                mBuilder.appendMessage(
                    "${file.name} is created"
                )
            }

            val fos = FileOutputStream(file)
            val fis = contentResolver.openInputStream(it)
                ?: return@forEach

            var n: Int
            while (true) {
                n = fis.read(buffer)
                if (n == -1) {
                    break
                }
                fos.write(
                    buffer,
                    0,
                    n
                )
            }
            fos.close()
            fis.close()

            mBuilder.appendMessage(
                "${file.name} is copied"
            )
        }


    }

}