package com.zhenghuiyan.todaything.fragment;

import android.app.Fragment;
import android.content.Intent;

import com.zhenghuiyan.todaything.R;

/**
 * Created by zhenghuiyan on 2015/3/20.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().overridePendingTransition(R.anim.keep_status, R.anim.one_scale_zero);
    }
}
