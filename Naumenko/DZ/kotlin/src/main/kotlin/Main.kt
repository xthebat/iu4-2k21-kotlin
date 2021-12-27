import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.photos
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.files.PhotoSize
import io.jhdf.HdfFile
import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.api.inference.keras.loadWeights
import org.jetbrains.kotlinx.dl.dataset.image.ImageConverter
import java.io.File




val labelsMap = mapOf(
    0 to "airplane",
    1 to "automobile",
    2 to "bird",
    3 to "cat",
    4 to "deer",
    5 to "dog",
    6 to "frog",
    7 to "horse",
    8 to "ship",
    9 to "truck"
)



fun main() {
    opredilenie()
    val bot = bot {
        token = "5086980007:AAEKMqgseyfpWmq1cnvU4vn0G-7Uxagrfqk"

        dispatch {
            text {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
            }
            photos {
                val img = media.last()
                val bytes = bot.downloadFileBytes(img.fileId)!!

                File("/home/aleksandr/Загрузки/1234.jpg").writeBytes(bytes)
                // поменяйте путь, решил делать без относительного с ним не получалось(

                val msg = opredilenie()
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = msg
                )
            }
        }
    }
    bot.startPolling()
}

fun opredilenie(): String {
    val imageArray = ImageConverter.toNormalizedFloatArray(File("/home/aleksandr/Загрузки/1234.jpg")) //и тут
    val modelConfig = File("/home/aleksandr/PycharmProjects/raspozn/keras-cifar-10/model.json")
    val weights = File("/home/aleksandr/PycharmProjects/raspozn/keras-cifar-10/weights")

    val model = Sequential.loadModelConfiguration(modelConfig)

    model.use {
        it.compile(Adam(), Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS, Metrics.ACCURACY)

        it.loadWeights(HdfFile(weights))

        val prediction = it.predict(imageArray)
        println("Predicted label is: $prediction. This corresponds to class ${labelsMap[prediction]}.")
        return "Predicted label is: $prediction. This corresponds to class ${labelsMap[prediction]}."
    }
}