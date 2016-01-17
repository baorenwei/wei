package Interface;

/**
 * Created by Administrator on 2016/1/3.
 * 抽象主题
 */
public interface Watched {

    public void addWatcher(Watcher watcher);
    public void removeWatcher(Watcher watcher);
    public void notifyWatchers(String str);
}
