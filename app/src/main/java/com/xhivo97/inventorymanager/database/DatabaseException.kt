package com.xhivo97.inventorymanager.database

class DBProductAlreadyExists(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

class DBProductDoesNotExist(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}