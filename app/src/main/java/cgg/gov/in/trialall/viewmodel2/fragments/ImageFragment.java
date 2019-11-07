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

import java.io.File;
import java.util.ArrayList;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.adapter.MyViewModel;
import cgg.gov.in.trialall.viewmodel2.adapter.RecyclerviewAdapter;

public class ImageFragment extends Fragment {


    public static ArrayList<File> fileList = new ArrayList<File>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swiperefresh;
    //boolean isLoading = false;
    RecyclerviewAdapter recyclerAdapter;
    MyViewModel myViewModel;
    String extension = ".jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        new AsyncCaller().execute();

    }

    /*private void populateData() {
        int i = 0;
        while (i < 12) {
            fileList.add(getFile(dir).get(i));
            i++;
        }
    }*/

    public void refresh() {

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerAdapter = new RecyclerviewAdapter(getActivity(), fileList, "images");
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        swiperefresh.setRefreshing(false);
    }

    /*private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                RecyclerView.LayoutManager gridLayoutManager = recyclerView.getLayoutManager();

                if (!isLoading) {
                    if ((gridLayoutManager != null) && ( gridLayoutManager.findViewByPosition(3)==recyclerView)) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }*/

    /*private void loadMore() {
        fileList.add(null);
        recyclerAdapter.notifyItemInserted(fileList.size() - 1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fileList.remove(fileList.size() - 1);
                int scrollPosition = fileList.size();
                recyclerAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    //fileList.add("Item " + currentSize);
                    fileList.add(getFile(dir).get(currentSize));
                    currentSize++;
                }

                recyclerAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }*/


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

            //populateData();

            fileList = myViewModel.getImagesList(extension);
            swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            fileList.clear();
                            fileList = myViewModel.getImagesList(extension);
                            refresh();
                        }
                    };
                    handler.postDelayed(runnable, 5000);

                    //populateData();
                    //refresh();
                    //initScrollListener();
                }
            });
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
            refresh();
            //   initScrollListener();
        }
    }
}
