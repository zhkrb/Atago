package com.zhkrb.atago.fragment;

import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhkrb.atago.adapter.SearchAdapter;
import com.zhkrb.atago.bean.DmhyListBean;
import com.zhkrb.atago.jsoup.JsoupUtils;
import com.zhkrb.atago.R;
import com.zhkrb.atago.utils.KeyboardUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import android.os.Handler;

public class SearchFragment extends AbsFragment {

    private static final int SUCCESS = 0xa1;
    private static final int ERROR = 0xa2;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private final Handler mHandler = new searchHandler(SearchFragment.this);
    private View mErrorView;
    private List<DmhyListBean> mList;
    private String mErrorMsg;

    private String name="";
    private int p=1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void main() {
        mRefreshLayout = mRootView.findViewById(R.id.refresh_view);
        mRefreshLayout.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3,
                R.color.refresh_color4
        );
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        mErrorView = mRootView.findViewById(R.id.error_view);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRefreshLayout.setOnRefreshListener(mRefreshListener);
        initData();
    }


    private void initData() {
        mRefreshLayout.setRefreshing(true);
        JsoupUtils.getsInstance().SearchDmhy(name, String.valueOf(p),mJoupCallback);
    }

    @Override
    protected void Search(String text) {
        if (text!=null){
            name = text;
            mRefreshLayout.setRefreshing(true);
            mAdapter.clear();
            KeyboardUtils.hideKeyboard(mContext);
            JsoupUtils.getsInstance().SearchDmhy(name, String.valueOf(p),mJoupCallback);
        }

    }

    public void onSearchBtnClick(){

    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            p=1;
            initData();
        }
    };

    private JsoupUtils.JoupCallback mJoupCallback = new JsoupUtils.JoupCallback() {
        @Override
        public void onResult(List<DmhyListBean> list) {
            Message ResMsg = new Message();
            ResMsg.what = SUCCESS;
            mList = list;
            mHandler.sendMessage(ResMsg);
        }

        @Override
        public void onError(String msg) {
            Message errMsg = new Message();
            errMsg.what = ERROR;
            mErrorMsg = msg;
            mHandler.sendMessage(errMsg);
        }
    };

    public void onResult(){
        mRefreshLayout.setRefreshing(false);
        if (mList==null){
            showError(getString(R.string.search_empty));
            return;
        }
        if (mList.size()<=0){
            if (mAdapter!=null){
                mAdapter.clear();
            }
            showError(getString(R.string.search_empty));
            return;
        }
        hideError();
        if (mAdapter == null) {
            mAdapter = new SearchAdapter(mContext, mList);
            mAdapter.setOnItemClick(mOnItemClick);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mList);
        }
    }

    public void onError() {
        mRefreshLayout.setRefreshing(false);
        if (mAdapter!=null){
            mAdapter.clear();
        }
        showError(mErrorMsg);
    }

    private void showError(String msg){
        if (mErrorView!=null){
            mErrorView.setVisibility(View.VISIBLE);
            ((TextView)mRootView.findViewById(R.id.error_text)).setText(msg);
        }
    }

    private void hideError(){
        if (mErrorView!=null){
            mErrorView.setVisibility(View.GONE);
        }
    }

    private SearchAdapter.onItemClick mOnItemClick = new SearchAdapter.onItemClick() {
        @Override
        public void onClick(DmhyListBean bean) {

        }
    };


    private static class searchHandler extends Handler{
        private final WeakReference<SearchFragment> mReference;

        private searchHandler(SearchFragment reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    mReference.get().onResult();
                    break;
                case ERROR:
                    mReference.get().onError();
                    break;
            }
        }
    }

}
