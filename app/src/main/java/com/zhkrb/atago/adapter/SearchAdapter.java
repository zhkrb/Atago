package com.zhkrb.atago.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhkrb.atago.bean.DmhyListBean;
import com.zhkrb.atago.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<DmhyListBean> mList;
    private LayoutInflater mInflater;
    private onItemClick mOnItemClick;

    public SearchAdapter(Context context, List<DmhyListBean> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_search_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setData(mList.get(position));
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void insertList(List<DmhyListBean> list) {
        int p = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(p, list.size());
        notifyItemRangeChanged(p, list.size());
    }

    public void setData(List<DmhyListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mList!=null){
            return mList.size();
        }
        return 0;
    }

    public void setOnItemClick(onItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView sort;
        TextView size;
        TextView time;
        DmhyListBean mBean;

         ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            sort = itemView.findViewById(R.id.group);
            size = itemView.findViewById(R.id.size);
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClick!=null){
                        mOnItemClick.onClick(mBean);
                    }

                }
            });
        }


         void setData(DmhyListBean bean){
            title.setText(bean.getTitle());
            sort.setText(bean.getSort());
            size.setText(bean.getSize());
             time.setText(bean.getTime());
            mBean=bean;
        }
    }

    public interface onItemClick{
        void onClick(DmhyListBean bean);
    }

}
