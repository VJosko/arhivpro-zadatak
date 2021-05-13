package com.vudrag.arhivpro;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vudrag.arhivpro.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment implements recSubjectAdapter.OnSelectListener {

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
        if (viewModel.lVocabulary.getValue() != null) {
            mAdapter = new recSubjectAdapter(viewModel.lVocabulary.getValue().getSubject(), this, 0);
            recyclerView.setAdapter(mAdapter);
        }

        viewModel.lVocabulary.observe(getViewLifecycleOwner(), o -> {
            binding.scroll.setVisibility(View.GONE);
            binding.rec.setVisibility(View.VISIBLE);
            if (viewModel.lVocabulary.getValue() != null) {
                mAdapter = new recSubjectAdapter(viewModel.lVocabulary.getValue().getSubject(), this, 0);
                recyclerView.setAdapter(mAdapter);
            }
        });

        viewModel.lSubjects.observe(getViewLifecycleOwner(), o -> {
            binding.scroll.setVisibility(View.GONE);
            binding.rec.setVisibility(View.VISIBLE);
            if (viewModel.lSubjects.getValue() != null) {
                mAdapter = new recSubjectAdapter(viewModel.lSubjects.getValue(), this, 1);
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

    @Override
    public void onSelect(int position, int list) {
        SearchFragmentDirections.ActionSearchFragmentToInfoFragment action = SearchFragmentDirections.actionSearchFragmentToInfoFragment();
        String subjectId;
        if (list == 0) {
            subjectId = viewModel.lVocabulary.getValue().getSubject().get(position).getSubjectID();
        } else {
            subjectId = viewModel.lSubjects.getValue().get(position).getSubjectID();
        }
        action.setSubjectId(subjectId);
        Navigation.findNavController(getView()).navigate(action);
    }
}