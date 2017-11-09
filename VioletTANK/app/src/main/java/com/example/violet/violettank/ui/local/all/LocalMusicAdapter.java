package com.example.violet.violettank.ui.local.all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.violet.violettank.R;
import com.example.violet.violettank.data.model.Song;
import com.example.violet.violettank.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/1.
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.ViewHolder> {

    private List<Song> data;
    private Context mContext;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public LocalMusicAdapter(Context context,List<Song> list) {
        mContext = context;
        data = list;
    }

    public void setData(List<Song> list){
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_local_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Song song = data.get(position);
        holder.textViewName.setText(song.getDisplayName());
        holder.textViewArtist.setText(song.getArtist());
        holder.textViewDuration.setText(TimeUtils.formatDuration(song.getDuration()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != data){
            return data.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_artist)
        TextView textViewArtist;
        @BindView(R.id.text_view_duration)
        TextView textViewDuration;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface OnItemClickListener{
        void onClick(int view);
    }

    public Song getItem(int i){
        if (null != data){
            return data.get(i);
        }else {
            return null;
        }
    }
}
