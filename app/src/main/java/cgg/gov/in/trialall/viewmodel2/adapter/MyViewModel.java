package cgg.gov.in.trialall.viewmodel2.adapter;


import android.arch.lifecycle.ViewModel;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;


public class MyViewModel extends ViewModel {

    ArrayList<File> pdfList = new ArrayList<File>();
    ArrayList<File> songsList = new ArrayList<File>();
    ArrayList<File> imagesList = new ArrayList<File>();
    ArrayList<File> videosList = new ArrayList<File>();
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    String extension;

    public ArrayList<File> getPdfList(String extension) {

        this.extension = extension;
        if (pdfList.isEmpty()) {
            getFile(dir);
        }
        return pdfList;
    }

    public ArrayList<File> getSongsList(String extension) {

        this.extension = extension;
        if (songsList.isEmpty()) {
            getFile(dir);
        }
        return songsList;
    }

    public ArrayList<File> getImagesList(String extension) {

        this.extension = extension;
        if (imagesList.isEmpty()) {
            getFile(dir);
        }
        return imagesList;
    }

    public ArrayList<File> getVideosList(String extension) {

        this.extension = extension;
        if (videosList.isEmpty()) {
            getFile(dir);
        }
        return videosList;
    }

    public void getFile(File dir) {

        File[] listFile = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {

                    if (listFile[i].getName().endsWith(extension)) {
                        if (extension.equals(".pdf")) {
                            for (int j = 0; j < pdfList.size(); j++) {
                                if (pdfList.get(j).getName().equals(listFile[i].getName())) {
                                }
                            }
                            pdfList.add(listFile[i]);
                        } else if (extension.equals(".mp3")) {
                            for (int j = 0; j < songsList.size(); j++) {
                                if (songsList.get(j).getName().equals(listFile[i].getName())) {
                                }
                            }
                            songsList.add(listFile[i]);

                        } else if (extension.equals(".jpg")) {
                            for (int j = 0; j < imagesList.size(); j++) {
                                if (imagesList.get(j).getName().equals(listFile[i].getName())) {
                                }
                            }
                            imagesList.add(listFile[i]);
                        } else if (extension.equals(".mp4")) {
                            for (int j = 0; j < videosList.size(); j++) {
                                if (videosList.get(j).getName().equals(listFile[i].getName())) {
                                }
                            }
                            videosList.add(listFile[i]);
                        }
                    }
                }
            }
        }
    }


}
