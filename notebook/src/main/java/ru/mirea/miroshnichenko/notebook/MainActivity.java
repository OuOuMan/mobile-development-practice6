package ru.mirea.miroshnichenko.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences preferences;
    final String FILE = "saved_text";
    EditText editTextFilename;
    EditText editTextContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);

        String textName = preferences.getString(FILE, "Empty");


        editTextFilename = findViewById(R.id.fileName);
        editTextContent = findViewById(R.id.fileText);

        FileInputStream fin = null;
        try {
            if (textName != "Empty") {
                fin = openFileInput(textName);
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);
                String myText = new String(bytes);
                editTextFilename.setText(textName.split(".txt")[0]);
                editTextContent.setText(myText);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveText(View view) {
        String filename = editTextFilename.getText().toString();
        Log.i(TAG, "filename: " + filename);
        String text = editTextContent.getText().toString();
        FileOutputStream outputStream;

        try {
            /*File dir = new File("/"); //path указывает на директорию
            Log.i(TAG, "dirname: " + dir.getName());
            File[] arrFiles = dir.listFiles();
            List<File> lst = Arrays.asList(arrFiles);
            boolean existFile = false;*/

    /*        for (File myFile: lst) {
                if (myFile.getName() == filename) {
                    existFile = true;
                    break;
                }
            }

            if (!existFile) {
                File file1 = new File("/", filename + ".txt");
            }*/


            outputStream = openFileOutput(filename + ".txt", Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(FILE, filename + ".txt");
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}