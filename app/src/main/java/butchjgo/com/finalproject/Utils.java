package butchjgo.com.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by auto on 05/11/2017.
 */

public class Utils {
    public static void copyFile(String srcPath, String desPath) {
        File src = new File(srcPath);
        File des = new File(desPath);

        try {
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            FileChannel desChannel = new FileInputStream(des).getChannel();

            try {
                desChannel.transferFrom(srcChannel, 0, srcChannel.size());

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (srcChannel != null) {
                        srcChannel.close();
                    }
                    if (desChannel != null) {
                        desChannel.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static String copyFileFromDataToSDCard(String des, String src) {
        String result = "Copied files: \n";
        File sdCard = Environment.getExternalStorageDirectory();
        String sdPath = sdCard.getAbsolutePath();

        File data = Environment.getDataDirectory();

        File dir = new File(sdPath + "/" + des);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //access DB folder
        File db = new File(data, src);
        File[] listFile = db.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                File f = listFile[i];
                result += f.getName() + "\n";
                copyFile(f.getAbsolutePath(), dir + "/" + f.getName());
            }
        } else {
            result += "None.\n";
        }
        return result;
    }

    public static boolean checkDB(Context context, String dbName) {
        boolean result = false;

        try {
            String path = "/data/" + context.getPackageName() + "/databases/" + dbName;
            File f = new File(path);
            result = f.exists();
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static void copyDBFromAssetToData(String filename, String srcPath, Context context) throws IOException {
        InputStream is = context.getAssets().open(filename);
        OutputStream os = new FileOutputStream(srcPath + "/" + filename);
        copyDB(is, os);
    }

    private static void copyDB(InputStream is, OutputStream os) throws IOException {

        byte[] buffer = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }

    public void copySd2System(String des, String src) {
        File sdCard = Environment.getExternalStorageDirectory();
        String sdPath = sdCard.getAbsolutePath();
    }
}
