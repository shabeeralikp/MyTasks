package com.nextwave.android.mytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    private String task;
    private int position;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        task = getIntent().getStringExtra("task");
        position = getIntent().getIntExtra("position", 0);
        editText = (EditText) findViewById(R.id.edItem);
        editText.setText(task, TextView.BufferType.EDITABLE);
        editText.setSelection(task.length());
        //setTitle("Edit Item");
        ActionBar actionbar = getSupportActionBar();
        Log.e("Shabeer", "" +actionbar);
        actionbar.setTitle("Edit Item");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSaveItem(View view) {
        String task = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("task", task);
        data.putExtra("position", position);
        data.putExtra("operation", "save");
        setResult(RESULT_OK, data);
        finish();
    }

    public void onDeleteItem(View view) {
        String task = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("task", task);
        data.putExtra("position", position);
        data.putExtra("operation", "delete");
        setResult(RESULT_OK, data);
        finish();
    }
}
