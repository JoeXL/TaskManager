/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.slidingtabsbasic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.common.activities.SampleActivityBase;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    private String newTabTitle;
    private String newTask;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_tab:
                addTab();
                break;
            case R.id.add_task:
                addTask();
                break;
            case R.id.add_tab:
                addTab();
                break;
            case R.id.delete_tab:
                deleteTab();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTask() {
        /*LinearLayout tasksContainer = (LinearLayout) findViewById(R.id.tasks_container);
        LayoutInflater inflater = LayoutInflater.from(this); // context should be selected fragment
        View inflatedLayout = inflater.inflate(R.layout.task_item, null, false);
        tasksContainer.addView(inflatedLayout);*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new task name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newTask = input.getText().toString();

                SlidingTabsBasicFragment fragment = (SlidingTabsBasicFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.sample_content_fragment);
                fragment.addNewTask(newTask);
                int currentTab = fragment.getmViewPager().getCurrentItem();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(fragment);
                fragmentTransaction.attach(fragment);
                fragmentTransaction.commit();

                fragment.getmViewPager().setCurrentItem(currentTab);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void addTab() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new tab name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newTabTitle = input.getText().toString();

                SlidingTabsBasicFragment fragment = (SlidingTabsBasicFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.sample_content_fragment);
                fragment.addTabTitle(newTabTitle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(fragment);
                fragmentTransaction.attach(fragment);
                fragmentTransaction.commit();

                fragment.getmViewPager().getAdapter().notifyDataSetChanged();
                fragment.getmViewPager().setCurrentItem(fragment.getTabTitles().size() - 1);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    public void deleteTab() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SlidingTabsBasicFragment fragment = (SlidingTabsBasicFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.sample_content_fragment);
                int currentTab = fragment.getmViewPager().getCurrentItem();
                fragment.deleteTabTile(currentTab);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(fragment);
                fragmentTransaction.attach(fragment);
                fragmentTransaction.commit();

                fragment.getmViewPager().getAdapter().notifyDataSetChanged();
                if(currentTab == 0) {
                    fragment.getmViewPager().setCurrentItem(0);
                }
                else {
                    fragment.getmViewPager().setCurrentItem(currentTab - 1);
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
