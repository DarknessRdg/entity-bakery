package io.github.bakery

import io.github.bakery.fakers.*


val PrimitiveFakeBuilder = mapOf(
    String::class.java to FakeString,

    Char::class.java to FakeChar,

    Byte::class.java to FakeByte,
    java.lang.Byte::class.java to FakeByte,

    Boolean::class.java to FakeBool,
    java.lang.Boolean::class.java to FakeBool,

    Short::class.java to FakeShort,
    java.lang.Short::class.java to FakeShort,

    Long::class.java to FakeLong,
    java.lang.Long::class.java to FakeLong,

    Int::class.java to FakeInteger,
    java.lang.Integer::class.java to FakeInteger,

    Float::class.java to FakeFloat,
    java.lang.Float::class to FakeFloat,

    Double::class.java to FakeDouble,
    java.lang.Double::class.java to FakeDouble
)
