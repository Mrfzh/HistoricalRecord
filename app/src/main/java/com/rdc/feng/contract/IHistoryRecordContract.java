package com.rdc.feng.contract;

import java.util.List;

/**
 * 所有接口的方法都放在了这里统一管理
 * @author Feng Zhaohao
 * Created on 2018/7/20
 */
public interface IHistoryRecordContract {
    interface View {
        void updateList(List<String> recordData);   //更新列表
    }
    interface Presenter {
        void updateList(List<String> recordData);
        void addRecordToDatabase(String newRecord);
        void firstUpdateList();
        void deleteAllRecord();
        void deleteRecord(String deleteRecord);
    }
    interface Model {
        void addRecordToDatabase(String newRecord);     //添加历史记录到数据库
        void firstUpdateList();     //首次进入页面时将数据库中的数据存入集合
        void deleteAllRecord();     //删除数据库中所有的历史记录
        void deleteRecord(String deleteRecord);     //删除单条历史记录
    }
}
