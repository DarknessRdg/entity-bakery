package io.github.bakery

data class Pessoa(
    val nome: String,
    val idade: Int
)


val container = mapOf(
    String::class.java to "Maria Joakina",
    Int::class.java to 10
)


inline fun <reified T> make() : T {
    val c = T::class.java.constructors[0]

    val values = arrayListOf<Any>()

    for (p in c.parameterTypes) {
        if (container[p] != null) {
            values.add(container[p]!!)
        }
    }


    return c.newInstance(*values.toArray()) as T
}



fun main() {
    val p = make<Pessoa>()
    println(p.idade)
    println(p.nome)

}
