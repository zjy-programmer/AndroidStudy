package com.example.androidstudy.hencoder;


import android.os.Bundle;

import com.example.androidstudy.R;
import com.example.androidstudy.databinding.ActivityHenCoderTestBinding;
import com.example.baselibrary.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HenCoderTestActivity extends BaseActivity {

    private ActivityHenCoderTestBinding binding;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHenCoderTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        String practice = bundle.getString("practice");
        switch (practice) {
            case "PracticeDraw":
                PracticeDraw();
                break;
        }
    }

    /**
     * HenCoder PracticeDraw1练习
     */
    private void PracticeDraw() {
        String[] types = new String[]{"sample_arc", "sample_circle", "sample_histogram",
                "sample_line", "sample_oval", "sample_path", "sample_pie_chart"};
        for (String type : types) {
            fragments.add(new HenCoderFragment(type));
        }
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(this, fragments);
        binding.viewPager.setAdapter(adapter);
    }


    static class ViewPagerFragmentAdapter extends FragmentStateAdapter {
        private List<Fragment> fragments;

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}