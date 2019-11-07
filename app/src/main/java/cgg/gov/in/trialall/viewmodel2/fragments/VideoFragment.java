package cgg.gov.in.trialall.viewmodel2.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.pedrovgs.DraggablePanel;

import java.io.File;
import java.util.ArrayList;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.VideoInterface;
import cgg.gov.in.trialall.viewmodel2.adapter.MyViewModel;
import cgg.gov.in.trialall.viewmodel2.adapter.RecyclerviewAdapter;

public class VideoFragment extends Fragment implements VideoInterface {

    public static ArrayList<File> fileList = new ArrayList<File>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swiperefresh;
    RecyclerviewAdapter recyclerAdapter;
    MyViewModel myViewModel;
    String extension = ".mp4";
    DraggablePanel draggablePanel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        draggablePanel = view.findViewById(R.id.draggable_panel);
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        new AsyncCaller().execute();

        draggablePanel.setFragmentManager(getActivity().getSupportFragmentManager());
        draggablePanel.setTopFragment(new TopFragment());
        draggablePanel.setBottomFragment(new BottomFragment());
        draggablePanel.setTopViewHeight(400);
        draggablePanel.initializeView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                draggablePanel.maximize();
            }
        }, 100);
    }


    public void refresh() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerAdapter = new RecyclerviewAdapter(getActivity(), fileList, "videos");
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        swiperefresh.setRefreshing(false);
    }

    @Override
    public void sendVideoPath(String position) {


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

            fileList = myViewModel.getVideosList(extension);
            swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fileList.clear();
                    fileList = myViewModel.getVideosList(extension);
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
