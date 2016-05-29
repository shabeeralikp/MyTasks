package com.nextwave.android.mytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_EDIT = 1;
    private final String TAG = "MyTasks";
    private final String TASKS_FILE = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText)findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent =  new Intent(TasksActivity.this , EditItemActivity.class);
                editIntent.putExtra("task", todoItems.get(position).toString() );
                editIntent.putExtra("position", position);
                startActivityForResult(editIntent, REQUEST_EDIT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, TASKS_FILE);
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            Log.e(TAG,"Read Exception : " + e);
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, TASKS_FILE);
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            Log.e(TAG,"Write Exception : " + e);
        }
    }

    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void onAddItem(View view) {
        String task = etEditText.getText().toString();
        if(!task.isEmpty()) {
            aToDoAdapter.add(task);
            etEditText.setText("");
            writeItems();
        } else {
            Log.e("SHABEER", "Toast");
            Toast.makeText(this, "Please specify the task", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT) {
            String task = data.getExtras().getString("task");
            int position = data.getExtras().getInt("position", 0);
            String operation = data.getExtras().getString("operation");
            if(operation.equals("delete")) {
                todoItems.remove(position);
            } else {
                todoItems.set(position, task);
            }
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
