package io.github.bakery

import java.lang.reflect.Constructor


class BakeryImpl(
    additionalFakeBuilder: Map<out Class<out Any>, () -> Any> = emptyMap()
) : Bakery {

    private val fakeBuilder = PrimitiveFakeBuilder.toMutableMap()

    init {
        additionalFakeBuilder.entries.forEach { (k, v) ->
            fakeBuilder[k] = v
        }
    }

    override fun <T> make(model: Class<T>): T {
        val constructor = getMoreCompleteConstructor(model)

        checkCanConstructOrElseThrow(constructor, model)
        val params = buildConstructorParams(constructor)

        return constructor.newInstance(*params) as T
    }

    private fun <T> getMoreCompleteConstructor(model: Class<T>) = model.constructors.sortedBy { it.parameterCount }
        .first()

    private fun buildConstructorParams(constructor: Constructor<*>): Array<Any> {
        val params = arrayListOf<Any>()

        constructor.parameterTypes.forEach { type ->
            val possibleValue = fakeBuilder[type]

            if (possibleValue == null) {
                params.add(make(type))
            } else {
                params.add(possibleValue())
            }
        }

        return params.toArray()
    }

    private fun checkCanConstructOrElseThrow(constructor: Constructor<*>, model: Class<*>) {
        if (constructor.parameterTypes.isEmpty() && !fakeBuilder.containsKey(model)) {
            throw UnknownTypeToBuildException(model)
        }
    }
}
