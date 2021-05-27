package io.github.bakery

import io.github.bakery.fakers.*


val PrimitiveFakeBuilder = mapOf(
    String::class.java to FakeString,
    Char::class.java to FakeChar,
    Byte::class.java to FakeByte,

    Boolean::class.java to FakeBool,

    Short::class.java to FakeShort,
    Long::class.java to FakeLong,
    Int::class.java to FakeInteger,
    Float::class.java to FakeFloat,
    Double::class.java to FakeDouble
)
