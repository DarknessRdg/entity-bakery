package io.github.bakery

interface Bakery {
    /**
     * Make a new model instance filled with all data through it's
     * constructor
     */
    fun <T> make(model: Class<T>): T

    /**
     * Return a Map with all known types and fake values
     */
    fun getFakeBuilder():  Map<Class<out Any>, Any>
}

inline fun <reified T> Bakery.make(): T {
    return make(T::class.java)
}
