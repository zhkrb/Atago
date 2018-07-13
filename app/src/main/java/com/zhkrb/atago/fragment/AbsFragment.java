package com.zhkrb.atago.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zhkrb.atago.R;

public abstract class AbsFragment extends android.support.v4.app.Fragment {

    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(getLayoutId(),container,false);
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void main();

    protected abstract void Search(String text);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
        setSearch();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void setSearch(){
        final EditText searchText = mRootView.findViewById(R.id.search_bar);
        if (searchText!=null){
            searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i==EditorInfo.IME_ACTION_SEND ||(keyEvent!=null&&keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                    {
                        Search(searchText.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
        }
    }



}
