package com.example.base.utils;

import java.util.ArrayList;
import java.util.List;

import Interface.Watched;
import Interface.Watcher;

/**
 * Created by Administrator on 2016/1/10.
 * 具体主题角色    对观察者的操作
 */
public class ConcreteWatched implements Watched {

    private List<Watcher> list = new ArrayList<>();
    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(String str) {

        for (Watcher watcher : list){
            watcher.update(str);
        }
    }
}
