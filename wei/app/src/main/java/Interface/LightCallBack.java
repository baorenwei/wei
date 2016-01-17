package Interface;

import light.LightBen;

/**
 * Created by Administrator on 2016/1/16.
 */
public interface LightCallBack {

         void complete(LightBen ben);
         void exception(Exception exception);
}
