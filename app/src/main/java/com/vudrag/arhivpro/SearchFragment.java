package com.vudrag.arhivpro;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vudrag.arhivpro.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    private RecyclerView recyclerView;
    private recSubjectAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.setContext(view.getContext().getApplicationContext());

        recyclerView = view.findViewById(R.id.rec);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        if(viewModel.lVocabulary.getValue() != null) {
            mAdapter = new recSubjectAdapter(viewModel.lVocabulary.getValue().getSubject());
            recyclerView.setAdapter(mAdapter);
        }

        viewModel.lVocabulary.observe(getViewLifecycleOwner(), o -> {
            binding.scroll.setVisibility(View.GONE);
            binding.rec.setVisibility(View.VISIBLE);
            if(viewModel.lVocabulary.getValue() != null) {
                mAdapter = new recSubjectAdapter(viewModel.lVocabulary.getValue().getSubject());
                recyclerView.setAdapter(mAdapter);
            }
        });

        viewModel.lSubjects.observe(getViewLifecycleOwner(), o -> {
            binding.scroll.setVisibility(View.GONE);
            binding.rec.setVisibility(View.VISIBLE);
            if(viewModel.lSubjects.getValue() != null) {
                mAdapter = new recSubjectAdapter(viewModel.lSubjects.getValue());
                recyclerView.setAdapter(mAdapter);
            }
        });

        binding.btnTerms.setOnClickListener(v -> {
            viewModel.loadTerms();
            binding.terms.setText(viewModel.terms);
            binding.scroll.setVisibility(View.VISIBLE);
            binding.rec.setVisibility(View.GONE);
        });


        return view;
    }
}