package pt.whd.readFile

import org.junit.Assert
import org.junit.Test

/**
 * Unit test for simple App.
 */
class AppTest {


    @Test
    fun isReadingFileProperly() {
        val ids = readFile("arquivo_teste.txt")
        Assert.assertEquals(ids, "38703,39134,39287")

    }
}