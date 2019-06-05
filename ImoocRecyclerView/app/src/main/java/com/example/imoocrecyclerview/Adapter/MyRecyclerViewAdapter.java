package com.example.imoocrecyclerview.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imoocrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2019-05-29
 * Author: tainan
 * Description: This is ......
 */

/**
 * 1、继承RecyclerView.Adapter
 * 2、绑定ViewHolder
 * 3、实现Adapter的相关方法
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private OnItemClickListener onItemClickListener;
    private RecyclerView mRv;
    private List<String> dataSource;
    private Context mContent;
    private int addDataPosition = -1;

    public MyRecyclerViewAdapter(Context context,RecyclerView recyclerView){
        this.mContent = context;
        this.dataSource = new ArrayList<>();
        this.mRv = recyclerView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    /**
     * 创建并返回ViewHolder
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContent).inflate(R.layout.item_layout,viewGroup,false));
    }

    /**
     * ViewHolder 绑定数据
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.mIv.setImageResource(getIcon(i));
        myViewHolder.mTv.setText(dataSource.get(i));

        // 只在瀑布流布局中使用随机高度
        if (mRv.getLayoutManager().getClass() == StaggeredGridLayoutManager.class) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getRandomHeight());
            myViewHolder.mTv.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myViewHolder.mTv.setLayoutParams(params);
        }

        // 改变ItemView背景颜色
        if (addDataPosition == i) {
            myViewHolder.mItemView.setBackgroundColor(Color.RED);
        }

        myViewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用接口的回调方法
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(i);
                }
            }
        });
    }

    /**
     * 返回数据数量
     * @return
     */
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    private int getIcon(int position){
        switch (position % 5){
            case 0:
                return R.mipmap.a;
            case 1:
                return R.mipmap.b;
            case 2:
                return R.mipmap.c;
            case 3:
                return R.mipmap.d;
            case 4:
                return R.mipmap.e;
        }
        return 0;
    }

    /**
     * 返回不同的ItemView高度
     * @return
     */
    private int getRandomHeight(){
        return (int) (Math.random() * 1000);
    }

    /**
     * 添加一条数据
     * @param position
     */
    public void addData (int position) {
        addDataPosition = position;
        dataSource.add(position,"插入的数据");
        notifyItemInserted(position);

        // 刷新ItemView
        notifyItemRangeChanged(position,dataSource.size()-position);
    }

    /**
     * 删除一条数据
     * @param position
     */
    public void removeData (int position) {
        addDataPosition = -1;
        dataSource.remove(position);
        notifyItemRemoved(position);

        // 刷新ItemView
        notifyItemRangeChanged(position,dataSource.size()-position);
    }

    // 创建ViewHolder的内部类
    class MyViewHolder extends RecyclerView.ViewHolder{
        View mItemView;
        ImageView mIv;
        TextView mTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTv = itemView.findViewById(R.id.tv);
            mItemView = itemView;
        }
    }

    /**
     * ItemView点击事件回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
