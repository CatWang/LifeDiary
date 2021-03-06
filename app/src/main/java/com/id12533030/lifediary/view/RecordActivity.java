package com.id12533030.lifediary.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.id12533030.lifediary.R;
import com.id12533030.lifediary.model.Day;
import com.id12533030.lifediary.model.Plan;
import com.id12533030.lifediary.util.Constants;
import com.id12533030.lifediary.util.MainMenu;

/**
 * Created by LENOVO on 2016/6/7.
 * This activity is used to provide interface for users to record diary, day and plan.
 */
public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private MainMenu mMainMenu;
    private FragmentManager mFragmentManager;
    private FloatingActionButton mDiaryFab;
    private FloatingActionButton mDayFab;
    private FloatingActionButton mPlanFab;
    private TextView mTitlePlan;
    private TextView mDetailPlan;
    private TextView mDatePlan;
    private TextView mLocationPlan;
    private RadioGroup mRadioGroup;
    private TextView mTitleDay;
    private TextView mDetailDay;
    private TextView mDateDay;
    private Spinner mSpinner;

    /**
     * Override the onCreate method and initial the toolbar and systembar
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_function_main);
        mFragmentManager = getSupportFragmentManager();
        mMainMenu = new MainMenu(this, mFragmentManager, true, true);
        mMainMenu.initSystemBar(this);
        init();
        setListener();
    }

    /**
     * Initial all the widgets
     */
    private void init() {
        mDiaryFab = (FloatingActionButton) findViewById(R.id.record_function_main_diary_fab);
        mDayFab = (FloatingActionButton) findViewById(R.id.record_function_main_day_fab);
        mPlanFab = (FloatingActionButton) findViewById(R.id.record_function_main_plan_fab);
    }

    /**
     * Set the listener for three fabs
     */
    private void setListener() {
        mDiaryFab.setOnClickListener(this);
        mDayFab.setOnClickListener(this);
        mPlanFab.setOnClickListener(this);
    }

    /**
     * Override the onCreateOptionsMenu method
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        mMainMenu.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Override the onOptionsItemSelected method
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mMainMenu.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Implement the click function
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        LayoutInflater inflater = getLayoutInflater();
        switch (v.getId()) {
            case R.id.record_function_main_diary_fab:
                intent = new Intent(RecordActivity.this, AddDiary.class);
                startActivityForResult(intent, Constants.REQUEST_ADD_HOMEPAGE);
                break;
            case R.id.record_function_main_day_fab:
                showDayDialog(inflater, v);
                break;
            case R.id.record_function_main_plan_fab:
                showPlanDialog(inflater, v);
                break;
            default:
                break;
        }
    }

    /**
     * Show the plan dialog for users to create a new plan
     *
     * @param inflater
     * @param view
     */
    private void showPlanDialog(LayoutInflater inflater, View view) {
        view = inflater.inflate(R.layout.add_plan, (ViewGroup) findViewById(R.id.add_plan_dialog));
        initPlan(view);
        new AlertDialog.Builder(this).setTitle(Constants.CREATE_PLAN_DIALOG_TITLE).setView(view)
                .setPositiveButton(Constants.CONFIRM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Set the type of plan
                        int type = mRadioGroup.getCheckedRadioButtonId() == R.id.add_plan_urgent_radiobutton ? 0 : 1;
                        Plan plan = new Plan(type,
                                mTitlePlan.getText().toString(),
                                mDatePlan.getText().toString(),
                                mLocationPlan.getText().toString(),
                                mDetailPlan.getText().toString());
                        plan.save();
                        Toast.makeText(getBaseContext(), Constants.CREATE_SUCCESSFUL, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Constants.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * Initial all the widgets of plan dialog
     *
     * @param view
     */
    private void initPlan(View view) {
        mTitlePlan = (TextView) view.findViewById(R.id.add_plan_title_textview);
        mDetailPlan = (TextView) view.findViewById(R.id.add_plan_text_textview);
        mDatePlan = (TextView) view.findViewById(R.id.add_plan_date_textview);
        mLocationPlan = (TextView) view.findViewById(R.id.add_plan_location_textview);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.add_plan_radiogroup);
        mRadioGroup.getChildAt(0).setSelected(true);
    }

    /**
     * Show the plan dialog for users to create a new day
     *
     * @param inflater
     * @param view
     */
    private void showDayDialog(LayoutInflater inflater, View view) {
        view = inflater.inflate(R.layout.add_day, (ViewGroup) findViewById(R.id.add_day_dialog));
        initDay(view);
        new AlertDialog.Builder(this).setTitle(Constants.CREATE_DAY_DIALOG_TITLE).setView(view)
                .setPositiveButton(Constants.CONFIRM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i = mSpinner.getSelectedItemPosition();
                        Day day = new Day(mSpinner.getSelectedItemPosition(),
                                mTitleDay.getText().toString(),
                                mDateDay.getText().toString(),
                                mDetailDay.getText().toString());
                        day.save();
                        Toast.makeText(getBaseContext(), Constants.CREATE_SUCCESSFUL, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Constants.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * Initial all the widgets of day dialog
     *
     * @param view
     */
    private void initDay(View view) {
        mTitleDay = (TextView) view.findViewById(R.id.add_day_title_textview);
        mDetailDay = (TextView) view.findViewById(R.id.add_day_text_textview);
        mDateDay = (TextView) view.findViewById(R.id.add_day_date_textview);
        mSpinner = (Spinner) view.findViewById(R.id.add_day_types_spinner);
    }
}
