package com.example.androidstudy.hencoder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidstudy.R;
import com.example.androidstudy.databinding.FragmentHenCoderBinding;
import com.example.androidstudy.hencoder.draw.draw1.SampleArcView;
import com.example.androidstudy.hencoder.draw.draw1.SampleCircleView;
import com.example.androidstudy.hencoder.draw.draw1.SampleHistogramView;
import com.example.androidstudy.hencoder.draw.draw1.SampleLineView;
import com.example.androidstudy.hencoder.draw.draw1.SampleOvalView;
import com.example.androidstudy.hencoder.draw.draw1.SamplePathView;
import com.example.androidstudy.hencoder.draw.draw1.SamplePieChartView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoder
 * ClassName: HenCoderFragment
 * CreateDate: 2021/6/29 6:39 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class HenCoderFragment extends Fragment {
    private String type;
    private FragmentHenCoderBinding binding;

    public HenCoderFragment(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHenCoderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadView();
    }

    private void loadView() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = null;
        switch (type) {
            case "sample_arc":
                view = new SampleArcView(getActivity());
                break;
            case "sample_circle":
                view = new SampleCircleView(getActivity());
                break;
            case "sample_histogram":
                view = new SampleHistogramView(getActivity());
                break;
            case "sample_line":
                view = new SampleLineView(getActivity());
                break;
            case "sample_oval":
                view = new SampleOvalView(getActivity());
                break;
            case "sample_path":
                view = new SamplePathView(getActivity());
                break;
            case "sample_pie_chart":
                view = new SamplePieChartView(getActivity());
                break;
        }
        if (view != null) {
            binding.flContainer.addView(view, layoutParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
