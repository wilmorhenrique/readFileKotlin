package pt.whd.readFile

import org.junit.Assert
import org.junit.Test
import pt.whd.readFile.App.readFile
import kotlin.Throws
import java.io.IOException
import pt.whd.readFile.App

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