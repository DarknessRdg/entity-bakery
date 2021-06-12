package io.github.bakery

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.*


val fakeString: () -> String = { "Test" }
val fakeInt: () -> Int = { 100 }


internal class BakeryImplTest {
    private val bakery: Bakery = BakeryImpl(
        additionalFakeBuilder = mapOf(
            String::class.java to fakeString,
            Int::class.java to fakeInt
        )
    )

    @Test
    fun `When make a new model, Then return new instance with fake data`() {
        data class FakeModel(
            val stringField: String,
            val intField: Int
        )

        val model = bakery.make<FakeModel>()

        assertThat(model.stringField).isEqualTo("Test")
        assertThat(model.intField).isEqualTo(100)
    }

    @Test
    fun `When add a new class type builder as additional builder, Then use that build to create the new class`() {
        class NewTypeClass

        class FakeModel(
            val newTypeField: NewTypeClass
        )

        val typeInstance = NewTypeClass()

        val builder = mapOf(NewTypeClass::class.java to { typeInstance })

        val bakery = BakeryImpl(additionalFakeBuilder = builder)
        val model = bakery.make<FakeModel>()

        assertThat(model.newTypeField === typeInstance).isTrue
    }

    @Test
    fun `When builders cannot find a primitive type to build, Then raise an exception`() {
        class UnknownType

        class FakeModel(
            field: UnknownType
        )

        assertThatThrownBy {
            bakery.make<FakeModel>()
        }.isInstanceOf(UnknownTypeToBuildException::class.java)
    }

    @Test
    fun `When build a class with multiple constructors, Then always build with construct that has more arguments`() {
        class MultipleConstructorClass {
            var firstField: Long? = null
                private set

            var secondField: Int? = null
                private set

            constructor() {}

            constructor(firstField: Long?) {
                this.firstField = firstField
            }

            constructor(firstField: Long?, secondField: Int?) {
                this.firstField = firstField
                this.secondField = secondField
            }
        }

        val model = bakery.make<MultipleConstructorClass>()

        assertThat(model.firstField).isNotNull
        assertThat(model.secondField).isNotNull
    }
}
