package io.github.bakery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class BakeryImplTest {
    private val bakery = BakeryImpl()

    @Test
    fun `When make a new model, Then return new instance with fake data`() {
        data class Person(
            val name: String,
            val age: Int
        )

        val model = bakery.make(Person::class.java)

        assertThat(model.age).isEqualTo(1)
        assertThat(model.name).isEqualTo("")
    }
}
