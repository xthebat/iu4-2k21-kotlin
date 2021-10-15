package ru.bmstu.iu4.sem06.desc.changes

class TextChange(path: String, val count: Int) : Change(path) {
    override fun toString() = "Change($path, count=$count, type=text)"
}