package com.rdc.feng.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.feng.adapter.RecordAdapter;
import com.rdc.feng.contract.IHistoryRecordContract;
import com.rdc.feng.customsearchview.R;
import com.rdc.feng.db.DaoManager;
import com.rdc.feng.entity.Record;
import com.rdc.feng.presenter.RecordPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IHistoryRecordContract.View{

    private RecordPresenter mPresenter;

    //UI控件
    private ImageView mSearchImageView;
    private EditText mSearchEditText;
    private ImageView mDeleteContentImageView;
    private TextView mCancelTextView;
    private RecyclerView mRecordRecyclerView;
    private TextView mClearRecordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new RecordPresenter();  //初始化Presenter
        mPresenter.attachView(this);    //给Presenter传入活动实例

        initView();

        //监听文本变化
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    mDeleteContentImageView.setVisibility(View.GONE);
                } else {
                    mDeleteContentImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //输入完成时
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!mSearchEditText.getText().toString().equals("")) {
                        mPresenter.addRecordToDatabase(mSearchEditText.getText().toString());   //在presenter中处理逻辑
                    }
                }
                return false;
            }
        });

        mPresenter.firstUpdateList();   //进入时载入数据库中的数据
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();	//销毁活动时与Presenter分离
        }
        super.onDestroy();
    }

    private void initView() {
        mSearchImageView = findViewById(R.id.iv_search);
        mSearchImageView.setOnClickListener(this);

        mDeleteContentImageView = findViewById(R.id.iv_delete_content);
        mDeleteContentImageView.setOnClickListener(this);

        mSearchEditText = findViewById(R.id.et_content);

        mCancelTextView = findViewById(R.id.tv_cancel);
        mCancelTextView.setOnClickListener(this);

        mRecordRecyclerView = findViewById(R.id.rv_record_list);

        mClearRecordTextView = findViewById(R.id.tv_clear_record);
        mClearRecordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                if (!mSearchEditText.getText().toString().equals("")) {
                    mPresenter.addRecordToDatabase(mSearchEditText.getText().toString());   //在presenter中处理逻辑
                }
                break;
            case R.id.iv_delete_content:
                mSearchEditText.getText().clear();  //清空查询内容
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_clear_record:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("是否清除历史记录");
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteAllRecord();   //清除数据库中的数据
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 接收model的回调并更新列表
     * @param recordData 历史记录集合
     */
    @Override
    public void updateList(List<String> recordData) {
        //如果存在历史记录，则显示“清除搜索记录”按钮
        if (recordData != null && recordData.size()>0) {
            Log.d("MainActivity", "size = " + recordData.size() + " value = " + recordData.get(0));
            mClearRecordTextView.setVisibility(View.VISIBLE);
        } else {
            mClearRecordTextView.setVisibility(View.GONE);
        }
        RecordAdapter adapter = new RecordAdapter(this, recordData, mPresenter);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecordRecyclerView.setAdapter(adapter);
    }
}
