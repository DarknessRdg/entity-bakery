package io.github.bakery

import java.lang.reflect.Constructor


class BakeryImpl : Bakery {
    override fun <T> make(model: Class<T>): T {
        val constructor = getMoreCompleteConstructor(model)

        val params = buildConstructorParams(constructor)

        return constructor.newInstance(*params) as T
    }

    override fun getFakeBuilder(): Map<Class<out Any>, Any> {
        return mapOf(
            String::class.java to "",
            Int::class.java to 1
        )
    }

    private fun <T> getMoreCompleteConstructor(model: Class<T>) = model.constructors.sortedBy { it.parameterCount }
        .first()

    private fun buildConstructorParams(constructor: Constructor<*>): Array<Any> {
        val params = arrayListOf<Any>()

        val builders = getFakeBuilder()

        constructor.parameterTypes.forEach { type ->
            val possibleValue = builders[type]

            if (possibleValue == null) {
                params.add(make(type))
            } else {
                params.add(possibleValue)
            }
        }

        return params.toArray()
    }
}