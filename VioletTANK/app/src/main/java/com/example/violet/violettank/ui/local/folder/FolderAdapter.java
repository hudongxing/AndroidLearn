package com.example.violet.violettank.ui.local.folder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.violet.violettank.R;
import com.example.violet.violettank.data.model.Folder;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/3.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private TextView textViewSummary;
    private AddFolderCallback mAddFolderCallback;

    private List<Folder> data;
    private Context mContext;

    public FolderAdapter(Context context, List<Folder> data) {
        mContext = context;
        data = data;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
//                updateFooterView();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_added_folder, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Folder folder = data.get(position);
        holder.textViewName.setText(folder.getName());
        holder.textViewInfo.setText(mContext.getString(
                R.string.mp_local_files_folder_list_item_info_formatter,
                folder.getNumOfSongs(),
                folder.getPath()
        ));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mAddFolderCallback != null) {
                    mAddFolderCallback.onAction(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Folder> list){

        this.data = list;

    }

    public Folder getItem(int i){
        return data.get(i);
    }

    public void updateFooterView() {
        if (textViewSummary == null) return;

        int itemCount = getItemCount() - 1; // real data count
        if (itemCount > 1) {
            textViewSummary.setVisibility(View.VISIBLE);
            textViewSummary.setText(mContext.getString(R.string.mp_local_files_folder_list_end_summary_formatter, itemCount));
        } else {
            textViewSummary.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_folder)
        AppCompatImageView imageViewFolder;
        @BindView(R.id.text_view_name)
        TextView textViewName;
        @BindView(R.id.text_view_info)
        TextView textViewInfo;
        @BindView(R.id.image_button_action)
        AppCompatImageView imageButtonAction;
        @BindView(R.id.layout_action)
        FrameLayout layoutAction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Folder> getData() {
        return data;
    }


    public void setAddFolderCallback(AddFolderCallback callback) {
        mAddFolderCallback = callback;
    }

    interface AddFolderCallback {

        void onAction(View actionView, int position);

        void onAddFolder();
    }
}
