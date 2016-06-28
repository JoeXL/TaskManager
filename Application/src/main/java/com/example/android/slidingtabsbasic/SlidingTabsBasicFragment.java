/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.slidingtabsbasic;

import com.example.android.common.listclasses.Task;
import com.example.android.common.logger.Log;
import com.example.android.common.view.SlidingTabLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    private Context mContext;

    private List<String> tabTitles = new ArrayList<>(Arrays.asList(
            "Home",
            "Work",
            "University",
            "something",
            "something",
            "something",
            "something"
    ));

    private Map<String, List<Task>> taskMap = new HashMap<String, List<Task>>();

    public Map<String, List<Task>> getTaskMap() {
        return taskMap;
    }

    public void setTaskMap(String tabName, List<Task> taskList) {
        taskMap.put(tabName, taskList);
    }

    // if the map contains the category title,
    // then get the tasks list associated to that category from the map,
    // and add the new task to the list associated to that category.
    // else create a new map element for the selected tab,
    // create a new tasks list and add the new task to this list,
    // then add this to the new map element
    public void addNewTask(String newTask) {
        String selectedTab = getTabTitles().get(mViewPager.getCurrentItem());
        if(getTaskMap().containsKey(selectedTab)) {
            List<Task> taskList = taskMap.get(selectedTab);
            taskList.add(new Task(newTask));
        }
        else {
            List<Task> taskList = new ArrayList<Task>();
            taskList.add(new Task(newTask));

            setTaskMap(selectedTab, taskList);
        }
    }

    public List<String> getTabTitles() {
        return tabTitles;
    }

    public void addTabTitle(String tab) {
        tabTitles.add(tab);
    }

    public void deleteTabTile(int tab) {
        tabTitles.remove(tab);
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    private ListView mListView;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        mContext = container.getContext();

        return view;
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        displayTaskList(view);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    public void displayTaskList(View view) {
        /*LinearLayout tasksContainer = (LinearLayout) findViewById(R.id.tasks_container);
        LayoutInflater inflater = LayoutInflater.from(this); // context should be selected fragment
        View inflatedLayout = inflater.inflate(R.layout.task_item, null, false);
        tasksContainer.addView(inflatedLayout);*/
//        int currentTab = mViewPager.getCurrentItem();
//        LinearLayout taskContainer = (LinearLayout) view.findViewById(R.id.tasks_container);
//        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
//        View inflatedLayout = inflater.inflate(R.layout.task_item, null, false);

        mListView = (ListView) view.findViewById(R.id.tasks_listView);

        // populate array with tasks list elements
        String[] checklist = {"pizza", "burger", "chocolate", "ice-cream", "banana", "apple"}; // edit this array
//        List<Task> tasksListForSelectedTab = getTaskMap().get(getTabTitles().get(mViewPager.getCurrentItem()));
//        String[] checklist = tasksListForSelectedTab.toArray(new String[tasksListForSelectedTab.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.task_item, checklist);
        mListView.setAdapter(adapter);
        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return tabTitles.size();
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }
}
