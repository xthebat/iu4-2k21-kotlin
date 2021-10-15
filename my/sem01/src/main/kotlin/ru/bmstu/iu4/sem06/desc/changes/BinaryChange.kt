package ru.bmstu.iu4.sem06.desc.changes

class BinaryChange(path: String, val size: Long) : Change(path) {
    override fun toString() = "Change($path, size=$size, type=bin)"
}