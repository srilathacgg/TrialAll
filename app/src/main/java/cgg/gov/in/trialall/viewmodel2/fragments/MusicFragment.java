package cgg.gov.in.trialall.viewmodel2.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.adapter.MyViewModel;
import cgg.gov.in.trialall.viewmodel2.adapter.RecyclerviewAdapter;

public class MusicFragment extends Fragment {


    public static ArrayList<File> fileList = new ArrayList<File>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swiperefresh;
    RecyclerviewAdapter recyclerAdapter;
    MyViewModel myViewModel;
    String extension = ".mp3";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        swiperefresh = view.findViewById(R.id.swiperefresh);

        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        new AsyncCaller().execute();

    }

    public void refresh() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerAdapter = new RecyclerviewAdapter(getActivity(), fileList, "music");
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        swiperefresh.setRefreshing(false);
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {

            fileList = myViewModel.getSongsList(extension);

            swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fileList.clear();
                    fileList = myViewModel.getSongsList(extension);
                    refresh();
                }
            });
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
            refresh();
        }
    }


}
