package com.schoolshieldchild.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.schoolshieldchild.R;
import com.schoolshieldchild.view.custom_controls.TextView_Regular;
import com.schoolshieldchild.view.adapter.TutorialPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends AppCompatActivity {
    @BindView(R.id.viewPager_tutorials)
    ViewPager viewPager_tutorials;
    @BindView(R.id.textView_Next)
    TextView_Regular textView_Next;

    List<Integer> Tutorialimages = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setPager();
    }

    public void setPager() {
        Tutorialimages.add(R.drawable.tutorial_screen1);
        Tutorialimages.add(R.drawable.tutorial_screen2);
        Tutorialimages.add(R.drawable.tutorial_screen3);

        TutorialPagerAdapter tutorialPagerAdapter = new TutorialPagerAdapter(getApplicationContext(), Tutorialimages);
        viewPager_tutorials.setAdapter(tutorialPagerAdapter);
        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.viewPager_tutorialsIndicator);
        titleIndicator.setViewPager(viewPager_tutorials);
        if (TutorialPagerAdapter.pos == 2) {
            textView_Next.setText(R.string.done);
        }
        viewPager_tutorials.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    textView_Next.setText(R.string.done);
                } else textView_Next.setText(R.string.next);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.textView_Next)
    public void setNextClick() {
        if (textView_Next.getText().toString().equalsIgnoreCase(String.valueOf(R.string.done))) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (viewPager_tutorials.getCurrentItem() < 2) {
            viewPager_tutorials.setCurrentItem(viewPager_tutorials.getCurrentItem() + 1);
            textView_Next.setText(R.string.done);
        } else if (viewPager_tutorials.getCurrentItem()== 2) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
