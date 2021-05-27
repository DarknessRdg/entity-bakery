package io.github.bakery

import java.lang.RuntimeException

open class BakeryException(message: String) : RuntimeException(message)

class UnknownTypeToBuildException(type: Class<*>) :
    BakeryException(
        message = "Didn't find any primitive type to build the class: ${type.simpleName}`"
    )
