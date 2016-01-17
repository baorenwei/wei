package com.example.base.utils;

import java.util.ArrayList;
import java.util.List;

import Interface.Watched;
import Interface.Watcher;

/**
 * Created by Administrator on 2016/1/3.
 */
public class ObserverUtils {

    public Watched watched  = new ConcreteWatched();

    class ConcreteWatched implements Watched{

        private List<Watcher> list = new ArrayList<Watcher>();
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

    public void notifyWatchers(String content){
        watched.notifyWatchers(content);
    }
    public void addWatcher(Watcher watcher){
        watched.addWatcher(watcher);
    }
}
