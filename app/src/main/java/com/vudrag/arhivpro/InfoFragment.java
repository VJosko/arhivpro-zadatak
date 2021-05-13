package com.vudrag.arhivpro;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.vudrag.arhivpro.databinding.FragmentInfoBinding;
import com.vudrag.arhivpro.databinding.FragmentSearchBinding;

public class InfoFragment extends Fragment {

    private InfoViewModelFactory factory;
    private InfoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        View view = binding.getRoot();

        String subjectId = InfoFragmentArgs.fromBundle(getArguments()).getSubjectId();
        factory = new InfoViewModelFactory(subjectId);
        viewModel = new ViewModelProvider(this, factory).get(InfoViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.search();

        viewModel.mSubjectInfo.observe(getViewLifecycleOwner(), o -> {
            Log.d("TAG", "onCreateView: ____________" + viewModel.mSubjectInfo.getValue().Subject.Terms.Preferred_Term.Term_Text);
            binding.invalidateAll();
        });

        viewModel.thumbnailUrl.observe(getViewLifecycleOwner(), o -> Picasso.get().load(viewModel.thumbnailUrl.getValue()).into(binding.image));



        return view;
    }
}