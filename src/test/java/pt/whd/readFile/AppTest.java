package pt.whd.readFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void isReadingFileProperly() throws IOException {
        String ids = App.readFile("arquivo_teste.txt");

        assertEquals( ids, "38703,39134,39287");
    }
}
