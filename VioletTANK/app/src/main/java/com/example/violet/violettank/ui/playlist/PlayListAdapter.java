package com.example.violet.violettank.ui.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.violet.violettank.R;
import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.utils.ViewUtils;

import java.lang.ref.PhantomReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class PlayListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    protected static final int VIEW_TYPE_ITEM = 1; // Normal list item
    protected static final int VIEW_TYPE_FOOTER = 2;  // Footer
    private Context mContext;
    private AddPlayListCallback mAddPlayListCallback;
    private OnItemClickListener onItemClickListener;

    private List<PlayList> data;
    public PlayListAdapter(Context context, List<PlayList> data) {

        mContext = context;
        this.data = data;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
//                updateFooterView();
            }
        });
    }

    public void setData(List<PlayList> data){
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_play_list, parent, false);
        View footView = LayoutInflater.from(mContext).inflate(R.layout.item_play_list_footer,parent,false);
        if (viewType == VIEW_TYPE_FOOTER) {
            return new FootViewHolder(footView);
        }
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){
                PlayList item = data.get(position);
            if (data.get(position).isFavorite()) {
                ((ViewHolder) holder).imageViewAlbum.setImageResource(R.drawable.ic_favorite_yes);
            } else {
                ((ViewHolder) holder).imageViewAlbum.setImageDrawable(ViewUtils.generateAlbumDrawable(mContext, item.getName()));
            }
            ((ViewHolder) holder).textViewName.setText(item.getName());
            ((ViewHolder) holder).textViewInfo.setText(mContext.getResources().getQuantityString(
                    R.plurals.mp_play_list_items_formatter, item.getItemCount(), item.getItemCount()));

            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mAddPlayListCallback != null) {
                        mAddPlayListCallback.onAction(v, position);
                    }
                }
            });

        }else {
            // TODO: 2017/11/8
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public int getItemViewType(int position) {
        if (isFooterEnabled() && position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }
    protected boolean isFooterEnabled() {
        return false;
    }
    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_view_album)
        ImageView imageViewAlbum;
        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_info)
        TextView textViewInfo;
        @BindView(R.id.layout_action)
        View buttonAction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public List<PlayList> getData(){
        return data;
    }

    // Callback

    public void setAddPlayListCallback(AddPlayListCallback callback) {
        mAddPlayListCallback = callback;
    }

    /* package */ interface AddPlayListCallback {

        void onAction(View actionView, int position);

        void onAddPlayList();
    }

    public void setOnItemClickListener(OnItemClickListener itemlistener){
        onItemClickListener =itemlistener;
    }

    interface OnItemClickListener{
        void click();
    }
}
