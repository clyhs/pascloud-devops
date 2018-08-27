create or replace and compile java source named runcmd as
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RunCmd {

  public static void exec(String cmd) {
    Process process = null;
    try {
      process = Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    printMessage(process.getInputStream());
    printMessage(process.getErrorStream());
    int value = 0;
    try {
      value = process.waitFor();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(value);
  }

  private static void printMessage(final InputStream input) {
    new Thread(new Runnable() {
      public void run() {
        Reader reader = new InputStreamReader(input);
        BufferedReader bf = new BufferedReader(reader);
        String line = null;
        try {
          while((line=bf.readLine())!=null) {
            System.out.println(line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
/

create or replace and compile java source named filetool as
import java.io.File;
public class FileTool{
    public static void deleteFile(String s){
            File file=new File(s);
            file.delete();
    }
}
/

create or replace and compile java source named existsfile as
import java.io.File;

public class ExistsFile {

  public static String[] existsFile(String fileName,String[] fileFlag) {
    String[] list = fileName.split(";");

    for (int i = 0; i <list.length; i++) {
      File f1=new File(list[i]);
      if (f1.isFile()) {
        fileFlag[0] = "1";
      } else{
        fileFlag[0] = "0";
                break;
            }
        }
        return fileFlag;
    }
}
/

quit;
