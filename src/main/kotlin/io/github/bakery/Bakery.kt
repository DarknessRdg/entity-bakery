package io.github.bakery

interface Bakery {
    /**
     * Make a new model instance filled with all data through it's
     * constructor
     */
    fun <T> make(model: Class<T>): T
}

inline fun <reified T> Bakery.make(): T {
    return make(T::class.java)
}
