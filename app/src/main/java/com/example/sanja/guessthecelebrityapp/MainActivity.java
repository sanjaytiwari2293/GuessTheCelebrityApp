package com.example.sanja.guessthecelebrityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3;
    ImageView imageView;
    ArrayList<Celebrity> list = new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();
    int locOfCorrectAnswer;
    int rIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

        imageView = (ImageView) findViewById(R.id.imageView);

        GetDataAsyncTask task = new GetDataAsyncTask();
        try {
            list = task.execute("http://www.posh24.se/kandisar").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        generateQuestion();
    }

    public void onClick(View view){
        if(view.getTag().equals(String.valueOf(locOfCorrectAnswer))){
            Log.d("ANSWER ","CORRECT!!");
            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("ANSWER ","WRONG!!");
            Toast.makeText(this, "Wrong Answer! It was "+list.get(rIndex).getName(), Toast.LENGTH_SHORT).show();
        }
        generateQuestion();
    }

    private void generateQuestion() {

        //Log.d("main","size "+list.toString());
        Random random = new Random();
        rIndex = random.nextInt(list.size());
        Celebrity rCeleb = list.get(rIndex);

        Picasso.get().load(rCeleb.getImgUrl()).into(imageView);

        locOfCorrectAnswer = random.nextInt(4);

        for(int i=0;i<4;i++){
            if(i==locOfCorrectAnswer){
                Button btn = buttons.get(i);
                btn.setText(rCeleb.getName());
            }
            else{
                int n = random.nextInt(list.size());
                while(list.get(n).getName().equals(rCeleb.getName()) || n == rIndex){
                    n = random.nextInt(list.size());
                }
                buttons.get(i).setText(list.get(n).getName());
            }
        }

    }
}
