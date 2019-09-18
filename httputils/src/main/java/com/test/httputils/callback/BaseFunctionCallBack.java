package com.test.httputils.callback;




import com.test.httputils.CommonResult;
import com.test.httputils.HttpConfig;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created  on 2019-09-18
 * @author QIAO
 * @param <COME> 接受的类型
 * @param <BACK> 返回的类型
 * @describe 用于RxJava-Retrofit链式调用 flatMap 操作符统一处理
 */
public abstract class BaseFunctionCallBack<COME, BACK> implements Function<CommonResult<COME>, ObservableSource<CommonResult<BACK>>> {
    @Override
    public ObservableSource<CommonResult<BACK>> apply(CommonResult<COME> tCommonResult) throws Exception {
        if (HttpConfig.RESPONSE_OK.equals(tCommonResult.getStatus())){
            return back(tCommonResult.getData());
        }
        return Observable.error(new Throwable(tCommonResult.getErrorMsg()));

    }
    public abstract ObservableSource<CommonResult<BACK>> back(COME result);
}
