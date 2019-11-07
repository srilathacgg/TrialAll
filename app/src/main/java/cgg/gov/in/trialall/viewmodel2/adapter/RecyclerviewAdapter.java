package cgg.gov.in.trialall.viewmodel2.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.activity.MusicActivity;
import cgg.gov.in.trialall.viewmodel2.activity.PdfviewActivity;

/**
 * Created by user on 05-11-2018.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {

    ArrayList<File> list;
    Activity context;
    String flag;

    public RecyclerviewAdapter(Activity context, ArrayList<File> list, String flag) {
        this.list = list;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = null;
        if (flag.equals("pdf") || flag.equals("music")) {
            v = inflater.inflate(R.layout.activity_recycler_pdf_music, parent, false);
        } else if (flag.equals("images") || flag.equals("videos")) {
            v = inflater.inflate(R.layout.activity_recycler_images_videos, parent, false);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final File path = new File(list.get(position).getPath());

        if (flag.equals("pdf") || flag.equals("music")) {

            holder.name.setText(list.get(position).getName());

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (flag.equals("pdf")) {
                        intent = new Intent(context, PdfviewActivity.class);
                    } else {
                        intent = new Intent(context, MusicActivity.class);
                    }
                    intent.putExtra("POSITION", position);
                    context.startActivity(intent);
                }
            });

        } else if (flag.equals("images") || flag.equals("videos")) {

            if (flag.equals("images")) {
                Glide.with(context)
                        .load(path)
                        .into(holder.img);
            } else {
                holder.video_name.setVisibility(View.VISIBLE);

                Glide.with(context)
                        .load(R.drawable.video)
                        .into(holder.img);

                holder.video_name.setText(list.get(position).getName());

            }

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag.equals("images")) {

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom_dialog);
                        PhotoView imgview = dialog.findViewById(R.id.imgview);
                        Button share_btn = dialog.findViewById(R.id.share_btn);

                        if (path.exists()) {
                            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            //imgview.setImageBitmap(myBitmap);
                            Glide.with(context)
                                    .load(path)
                                    //.resize(400, 400)
                                    .into(imgview);
                            Log.d("path", String.valueOf(path));

                        }

                        imgview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        share_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Uri bmpUri = Uri.parse(String.valueOf(path));
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                shareIntent.setType("image/jpg");
                                context.startActivity(Intent.createChooser(shareIntent, "Share with"));

                            }
                        });

                        dialog.show();

                    } else {

//                        Intent intent = new Intent(context, VideoActivity.class);
//                        intent.putExtra("POSITION", position);
//                        context.startActivity(intent);


                        //holder.img_video.setImageBitmap(bitmap);

                       /* String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = context.getContentResolver().query(Uri.fromFile(videopath), filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        Cursor cursor = context.getContentResolver().query(Uri.fromFile(videopath), null, null, null, null);
                        int column_index_data = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                        int thum = cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
                        while (cursor.moveToNext()) {
                            String absolutePathOfImage = cursor.getString(thum);
                            holder.img_video.setImageURI(Uri.parse(absolutePathOfImage));
                        }*/


                    }


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, video_name;
        ImageView img;

        public MyViewHolder(View v) {
            super(v);
            if (flag.equals("pdf") || flag.equals("music")) {
                name = v.findViewById(R.id.tv);
            } else if (flag.equals("images") || flag.equals("videos")) {
                img = v.findViewById(R.id.img);
                video_name = v.findViewById(R.id.tv_name);
            }

        }
    }


}
