package ws1819.a2.donottouch.utils;

import java.io.File;
import java.io.IOException;

public class PathUtil {

    public static String getRelativePathToCompiledProjectResources() throws IOException {
        return getRelativePathToCompiledProjectResources(PathUtil.class);
    }

    public static String getRelativePathToCompiledProjectResources(Class<?> clazz) throws IOException {
        File dir = new File(".");

        String relativeClassPath = clazz.getName().replaceAll("\\.","/")+".class";

        ClassLoader loader = clazz.getClassLoader();
        String fullPathToClass = loader.getResource(relativeClassPath).toString();
        String relativePathToClass = fullPathToClass.split(new File(dir.getCanonicalPath()).toURI().toString())[1];
        String relativePathToProject = relativePathToClass.split(relativeClassPath)[0];
        return relativePathToProject;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getRelativePathToCompiledProjectResources(PathUtil.class));

    }
}
