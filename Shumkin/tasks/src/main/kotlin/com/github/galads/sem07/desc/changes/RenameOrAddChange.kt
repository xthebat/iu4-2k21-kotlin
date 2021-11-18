package com.github.galads.sem07.desc.changes

class RenameOrAddChange(path: String, val count: Int, val latest: String, val now: String, val dirWhereAdd: String = "") :
    Change(path) {
    override fun toString() = if (latest.isNotEmpty())
        "Change($path, count=$count, latest=${this.latest}, now=${this.now}, type=rename)"
    else
        "Change($path, count=$count, addInDir=${this.dirWhereAdd}, now=${this.now}, type=add)"
}