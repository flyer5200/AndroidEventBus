/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.simple.eventbus.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;
import org.simple.eventbus.demo.R;
import org.simple.eventbus.demo.bean.Person;

import java.util.Random;

/**
 * @author mrsimple
 */
public class MenuFragment extends Fragment {

    public static final String CLICK_TAG = "click_user";
    
    TextView mUserNameTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_fragment, container, false);

        mUserNameTv = (TextView) rootView.findViewById(R.id.click_tv);

        // 发布事件
        rootView.findViewById(R.id.my_post_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Person("Mr.Simple" + new Random().nextInt()));
            }
        });

        // 发布移除事件的按钮
        rootView.findViewById(R.id.my_remove_button).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 移除用户
                        EventBus.getDefault().post(new Person("User - 1"),
                                "remove");
                    }
                });

        // 发布异步事件的按钮
        rootView.findViewById(R.id.my_post_async_event_button).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 将目标函数执行在异步线程中
                        EventBus.getDefault().post(new Person("mr.simple-3"), "async");
                    }
                });

        EventBus.getDefault().register(this);
        return rootView;
    }
    
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subcriber(tag = CLICK_TAG)
    private void updateClickUserName(Person clickPerson) {
        mUserNameTv.setText(clickPerson.name);
    }
}