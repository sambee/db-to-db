package sam.bee.oa.zip;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;


public class UnZipTest {
	
	@Test
	public void test() throws IOException{
		JarFile jarFile = new JarFile("F:/meiluo/mlams_2.0_dev_trunk/server/applications/ROOT/desktop.zip");

		for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry entry = (JarEntry) entries.nextElement();
			
           // JarEntry entry = e.nextElement();
            System.out.println(entry.getName());
        }   
		
	}

}
