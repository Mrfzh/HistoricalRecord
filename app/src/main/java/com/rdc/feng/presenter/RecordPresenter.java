package com.rdc.feng.presenter;

import com.rdc.feng.base.BasePresenter;
import com.rdc.feng.contract.IHistoryRecordContract;
import com.rdc.feng.model.RecordModel;

import java.util.List;

/**
 * @author Feng Zhaohao
 * Created on 2018/7/20
 */
public class RecordPresenter extends BasePresenter<IHistoryRecordContract.View> implements IHistoryRecordContract.Presenter {
    private IHistoryRecordContract.Model mModel;

    //构造器中初始化model,并给model传入Presenter实例
    public RecordPresenter() {
        mModel = new RecordModel(this);
    }

    //控制view的逻辑
    @Override
    public void updateList(List<String> recordData) {
        //判断是否获取到View
        if (isAttachView()) {
            getMvpView().updateList(recordData);
        }
    }

    //控制model的逻辑
    @Override
    public void addRecordToDatabase(String newRecord) {
        mModel.addRecordToDatabase(newRecord);
    }

    @Override
    public void firstUpdateList() {
        mModel.firstUpdateList();
    }

    @Override
    public void deleteAllRecord() {
        mModel.deleteAllRecord();
    }

    @Override
    public void deleteRecord(String deleteRecord) {
        mModel.deleteRecord(deleteRecord);
    }
}
