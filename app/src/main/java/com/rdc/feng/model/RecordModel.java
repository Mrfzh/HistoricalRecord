package com.rdc.feng.model;

import android.util.Log;

import com.rdc.feng.app.MyApplication;
import com.rdc.feng.contract.IHistoryRecordContract;
import com.rdc.feng.db.DaoManager;
import com.rdc.feng.entity.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Feng Zhaohao
 * Created on 2018/7/20
 */
public class RecordModel implements IHistoryRecordContract.Model{

    private IHistoryRecordContract.Presenter mPresenter;
    //数据库管理类
    private DaoManager mDaoManager;
    //历史记录集合
    private List<String> mRecordDataList;
    //实体类集合
    private List<Record> mRecordList;

    //构造器中初始化Presenter
    public RecordModel(IHistoryRecordContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;

        //初始化DaoManager
        mDaoManager = DaoManager.getInstance();
        mDaoManager.init(MyApplication.getContextObject());
    }

    @Override
    public void addRecordToDatabase(String newRecord) {
        if (hasRecord(newRecord)) {
            //删除原来的数据
            Log.d("RecordModel", "delete " + deleteId(newRecord));

            //数据的Id并不是从0开始的，一开始以为从0开始，所以以为Id就是数据的位置，搞得删了很久都删不掉，最后才发现Id要用getId()获取
            mDaoManager.getDaoSession().getRecordDao().deleteByKey(deleteId(newRecord));
            getRecordData();
        }
        //如果数据够5条，则删除最早的数据
        if (mRecordList.size() == 5) {
            mDaoManager.getDaoSession().getRecordDao().deleteByKey(deleteId(mRecordList.get(0).getData()));
        }
        //向数据库插入数据
        mDaoManager.getDaoSession().getRecordDao().insert(new Record(null, newRecord));

        getRecordData();    //获取历史记录集合
        Collections.reverse(mRecordDataList);   //倒置集合
        mPresenter.updateList(mRecordDataList); //回调，在主线程更新UI
    }

    /**
     * 获取历史记录集合
     */
    private void getRecordData() {
        mRecordList = mDaoManager.getDaoSession().loadAll(Record.class);
        mRecordDataList = new ArrayList<>();
        for (int i = 0; i < mRecordList.size(); i++) {
            mRecordDataList.add(mRecordList.get(i).getData());
        }
    }

    /**
     * 第一次进入时将所有历史记录加入到集合中
     */
    @Override
    public void firstUpdateList() {
        getRecordData();
        mPresenter.updateList(mRecordDataList);
    }

    /**
     * 删除数据库中所有的历史记录
     */
    @Override
    public void deleteAllRecord() {
        //mDaoManager.getDaoSession().deleteAll(Record.class);
        mDaoManager.getDaoSession().getRecordDao().deleteAll();
        List<String> recordData = new ArrayList<>();
        mPresenter.updateList(recordData);
    }

    /**
     * @param data 要删除的数据
     * @return 返回要删除数据的Id
     */
    private Long deleteId(String data) {
        for (int i = 0; i < mRecordList.size(); i++) {
            if (mRecordList.get(i).getData().equals(data)) {
                return mRecordList.get(i).getId();
            }
        }
        return null;
    }

    /**
     * 判断数据库中是否存有该条数据
     * @param data
     */
    private boolean hasRecord(String data) {
        getRecordData();
        for (int i = 0; i < mRecordDataList.size(); i++) {
            if (mRecordDataList.get(i).equals(data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除单条历史记录
     * @param deleteRecord
     */
    @Override
    public void deleteRecord(String deleteRecord) {
        mDaoManager.getDaoSession().getRecordDao().deleteByKey(deleteId(deleteRecord));
        getRecordData();
        mPresenter.updateList(mRecordDataList);
    }
}
