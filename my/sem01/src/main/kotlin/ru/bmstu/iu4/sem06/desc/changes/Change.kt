package ru.bmstu.iu4.sem06.desc.changes


abstract class Change(val path: String) {
    companion object {
        fun fromString(string: String): Change {
            val filepath = string.substringBefore("|").trim()
            val changes = string.substringAfter("|").trim()
            val countOrType = changes.substringBefore(" ").trim()

            return if (countOrType == "Bin") {
                val size = if ("bytes" in changes)
                    changes.substringAfter("->")
                        .substringBefore("bytes")
                        .trim().toLong()
                else -1

                BinaryChange(filepath, size)
            } else {
                TextChange(filepath, countOrType.toInt())
            }
        }
    }

    abstract override fun toString(): String
}

