package part1.lesson09;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author KhafizovAR on 19.11.2019.
 * @project Stc20JavaMiddle
 */
public class SomeClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("SomeClass")) {
            String dest = "file:./files/SomeClass.class";
            try {
                URL url = new URL(dest);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int data = inputStream.read();
                while (data !=-1) {
                    baos.write(data);
                    data = inputStream.read();
                }

                inputStream.close();

                byte[] classData = baos.toByteArray();

                return defineClass(name, classData, 0, classData.length);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.loadClass(name);
    }
}