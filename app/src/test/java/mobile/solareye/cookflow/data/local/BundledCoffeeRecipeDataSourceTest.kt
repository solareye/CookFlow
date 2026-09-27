package mobile.solareye.cookflow.data.local

import java.io.ByteArrayInputStream
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import mobile.solareye.cookflow.data.model.RecipeCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BundledCoffeeRecipeDataSourceTest {
    @Test
    fun bundledSnapshotLoadsEveryRecipeIntoCoffeeCategory() = runBlocking {
        val source = BundledCoffeeRecipeDataSource {
            checkNotNull(javaClass.classLoader!!.getResourceAsStream(
                BundledCoffeeRecipeDataSource.ASSET_NAME
            ))
        }

        val recipes = source.getRecipeList()

        assertEquals(1_683, recipes.size)
        assertEquals(recipes.size, recipes.map { it.id }.distinct().size)
        assertTrue(recipes.all { it.category == RecipeCategory.COFFEE })
        assertTrue(recipes.all { it.name.isNotBlank() && !it.canOpenDetails })
        assertEquals(
            recipes.map { it.name }.sortedBy { it.lowercase() },
            recipes.map { it.name },
        )
        assertEquals(
            "Бразилия Элинир в Капельной Кофеварке",
            recipes.single { it.id == "0014a538-d629-d456-f9da-ce292bcd1dcb" }.name,
        )
    }

    @Test
    fun ignoresBrewingFieldsAndClosesSnapshotStream() = runBlocking {
        var closed = false
        val snapshot = """
            {"status":"OK","value":[{
                "id":"coffee-1","name":" Кофе в воронке ",
                "water":255,"temperature":96,"notes":"Предсмачивание"
            }]}
        """.trimIndent()
        val source = BundledCoffeeRecipeDataSource {
            object : ByteArrayInputStream(snapshot.toByteArray(Charsets.UTF_8)) {
                override fun close() {
                    closed = true
                    super.close()
                }
            }
        }

        val recipe = source.getRecipeList().single()

        assertEquals("Кофе в воронке", recipe.name)
        assertFalse(recipe.canOpenDetails)
        assertTrue(closed)
    }

    @Test(expected = IllegalStateException::class)
    fun rejectsUnsuccessfulSnapshot() = runBlocking<Unit> {
        BundledCoffeeRecipeDataSource {
            """{"status":"ERROR","value":[]}""".byteInputStream()
        }.getRecipeList()
    }

    @Test(expected = SerializationException::class)
    fun rejectsRecipeWithoutAName() = runBlocking<Unit> {
        BundledCoffeeRecipeDataSource {
            """{"status":"OK","value":[{"id":"coffee-1"}]}""".byteInputStream()
        }.getRecipeList()
    }
}
