package butchjgo.com.finalproject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class Login extends AppCompatActivity {


    private static final String server = "192.168.1.4";
    String user, password;
    boolean status;
    private AsyncTask asyncTask = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] objects) {
            boolean loginStatus = true;
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(String.valueOf(objects[2]), 21);
                int replyCode = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(replyCode)) {
                    loginStatus = false;
                    ftpClient.disconnect();
                } else {
                    loginStatus = ftpClient.login(String.valueOf(objects[0]), String.valueOf(objects[1]));
                }
            } catch (Exception e) {
                e.printStackTrace();
                loginStatus = false;
            } finally {
                status = loginStatus;
                return loginStatus;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View view) {
        status = false;

        String user = ((EditText) findViewById(R.id.txtUser)).getText().toString();

        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("butchjgo.com.finalproject", MODE_PRIVATE);

        asyncTask.execute(new Object[]{user, password, server});

        if (status) {
            sharedPreferences.edit().putString("user", user).putString("password", password).apply();
            //finish();
            Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Login fail", Toast.LENGTH_LONG).show();
        }
    }

    boolean testLogin(String user, String password, String server) {
        Object o = asyncTask.execute(new Object[]{user, password, server});
        return ((Boolean) o);
    }
}
