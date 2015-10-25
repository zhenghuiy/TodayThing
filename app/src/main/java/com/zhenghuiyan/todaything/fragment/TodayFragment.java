package com.zhenghuiyan.todaything.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.zhenghuiyan.todaything.R;
import com.zhenghuiyan.todaything.activity.EditActivity;
import com.zhenghuiyan.todaything.adapter.ThingListViewAdapter;
import com.zhenghuiyan.todaything.bean.Thing;
import com.zhenghuiyan.todaything.data.DataManager;
import com.zhenghuiyan.todaything.listener.SwipeDismissListViewTouchListener;
import com.zhenghuiyan.todaything.task.ProgressDialogAsyncTask;
import com.zhenghuiyan.todaything.task.SimpleNoCallbackTask;
import com.zhenghuiyan.todaything.util.DateUtil;
import com.zhenghuiyan.todaything.util.MiscUtil;
import com.zhenghuiyan.todaything.util.UIUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhenghuiyan on 2015/2/13.
 */
public class TodayFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private ButtonFloatSmall mBtnAdd;

    private ListView mThingListView;

    private ThingListViewAdapter mAdapter;

    private ArrayList<Thing> mThings;

    private DataManager mDataManager;

    private TextView mTvCurrentThing;

    private String mTvCurrentThingContent = "";

    private int mScreenHeight;

    private int mScreenWidth;

    private Status mStatus;

    private int currentThinglargeHeight;

    private int currentThingSmallHeight;

    private int orginAddBtnX = 0;

    private int orginAddBtnY = 0;

    private int newAddBtnX = 0;

    private int newAddBtnY = 0;

    public static enum Status {
        SHOW_ONE,
        CHANGE,
        SHOW_MULTI,
        IS_CHANGING
    }
    public static enum Action {
        ADD,
        EDIT
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);
        mDataManager = DataManager.getInstance();
        init(rootView);
        showStatus(Status.SHOW_ONE);
        return rootView;
    }

    private void init(View rootView) {
        mScreenWidth = UIUtil.getScreenWidth(getActivity());
        mScreenHeight = UIUtil.getScreenHeight(getActivity());

        mTvCurrentThing = (TextView) rootView.findViewById(R.id.current_thing);
        mBtnAdd = (ButtonFloatSmall) rootView.findViewById(R.id.add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEdit(Action.ADD, null, -1);
            }
        });

        mThingListView = (ListView) rootView.findViewById(R.id.list);
        mThingListView.setOnItemClickListener(this);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        mThingListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    final Thing t = mThings.remove(position);
                                    new SimpleNoCallbackTask() {
                                        @Override
                                        protected void doInBack() {
                                            /**
                                             * complete one thing
                                             * */
                                            mDataManager.completeThing(t);
                                            t.setComplete_date(DateUtil.dateToString(new Date()));

                                        }
                                    }.execute();
                                }
                                mAdapter.notifyDataSetChanged();
                                updateCurrentThing();
                            }
                        });
        mThingListView.setOnTouchListener(touchListener);
        mThingListView.setOnScrollListener(touchListener.makeScrollListener());

        new ProgressDialogAsyncTask(getActivity(), false){
            @Override
            protected void doInBack() throws Exception {
                mDataManager.updateThings();
            }

            @Override
            protected void onPost() {
                mThings = mDataManager.getThings();
                if (mThings.size() >= 1) {
                    updateCurrentThing();
                    mAdapter = new ThingListViewAdapter(mThings);
                    mThingListView.setAdapter(mAdapter);
                }

                mTvCurrentThing.setText(mTvCurrentThingContent);
            }
        }.execute();

        mTvCurrentThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatus != Status.IS_CHANGING) {
                    showStatus(Status.CHANGE);
                }

            }
        });


    }

    private void updateCurrentThing() {
        if (mThings == null) {
            return;
        }
        if (mThings.size() > 0) {
            mTvCurrentThingContent = mThings.get(0).getContent();
        } else {
            mTvCurrentThingContent = "";
        }
    }

    private void goToEdit(final Action action, Thing t, int position) {
        final Bundle bundle = new Bundle();
        if ((action == Action.EDIT) && (t != null)) {
            bundle.putSerializable("thing", t);
            bundle.putInt("position", position);
            bundle.putString("action", Action.EDIT.name());
        } else {
            bundle.putString("action", Action.ADD.name());
        }

        orginAddBtnX = (int)mBtnAdd.getX();
        orginAddBtnY = (int) mBtnAdd.getY();
        newAddBtnX = mScreenWidth/2 - orginAddBtnX - mBtnAdd.getWidth();
        newAddBtnY = mScreenHeight/2 - orginAddBtnY - mBtnAdd.getHeight();
        Animation a = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, newAddBtnX, Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, newAddBtnY);
        a.setDuration(300);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MiscUtil.goToActivity(getActivity(), TodayFragment.this, EditActivity.class, bundle, true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        a.setFillAfter(true);
        mBtnAdd.startAnimation(a);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mThingListView) {
            Thing t = mThings.get(position);
            goToEdit(Action.EDIT, t, position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateCurrentThing();

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            mThings = mDataManager.getThings();
            mAdapter = new ThingListViewAdapter(mThings);
            mThingListView.setAdapter(mAdapter);
        }
        Animation a = new TranslateAnimation(Animation.ABSOLUTE, newAddBtnX, Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, newAddBtnY, Animation.RELATIVE_TO_SELF, 0);
        a.setDuration(500);
        a.setFillAfter(true);
        mBtnAdd.startAnimation(a);
    }

    //show current thing status
    private void showStatus(Status s) {
        currentThinglargeHeight = mScreenHeight - UIUtil.dip2px(MiscUtil.getAppContext(), 72) * 2 - UIUtil.dip2px(MiscUtil.getAppContext(), 36);
        currentThingSmallHeight = UIUtil.dip2px(MiscUtil.getAppContext(), 72);

        switch (s) {
            case SHOW_ONE:
                mStatus = Status.SHOW_ONE;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, currentThinglargeHeight);
                mTvCurrentThing.setLayoutParams(params);
                mThingListView.setVisibility(View.INVISIBLE);
                break;
            case CHANGE:
                if (mStatus == Status.SHOW_ONE) {
                    mStatus = Status.IS_CHANGING;
                    oneToMul();

                } else {
                    mStatus = Status.IS_CHANGING;
                    mulToOne();
                }

                break;
            case SHOW_MULTI:
                mStatus = Status.SHOW_MULTI;
                break;
        }


    }

    private void oneToMul() {
        Animation changeAnimation = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    mTvCurrentThing.setText("");
                    mThingListView.setVisibility(View.VISIBLE);
                    mStatus = Status.SHOW_MULTI;
                } else {
                    mTvCurrentThing.getLayoutParams().height = currentThinglargeHeight - (int) (Math.abs(currentThinglargeHeight - currentThingSmallHeight) * interpolatedTime);
                    mTvCurrentThing.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }

        };
        changeAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        changeAnimation.setDuration(500);
        mTvCurrentThing.startAnimation(changeAnimation);
    }

    private void mulToOne() {
        Animation changeAnimation = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 0) {
                    mTvCurrentThing.setText(mTvCurrentThingContent);
                    mThingListView.setVisibility(View.INVISIBLE);
                } else {
                    mTvCurrentThing.getLayoutParams().height = currentThingSmallHeight + (int) (Math.abs(currentThinglargeHeight - currentThingSmallHeight) * interpolatedTime);
                    mTvCurrentThing.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }

        };
        changeAnimation.setInterpolator(new BounceInterpolator());
        changeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mStatus = Status.SHOW_ONE;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mTvCurrentThing.getLayoutParams().height = currentThingSmallHeight;
            }
        });
        changeAnimation.setInterpolator(new BounceInterpolator());
        changeAnimation.setDuration(500);
        mTvCurrentThing.startAnimation(changeAnimation);
    }
}
