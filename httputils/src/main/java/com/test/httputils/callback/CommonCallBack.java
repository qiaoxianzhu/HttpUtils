package com.test.httputils.callback;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.test.httputils.CommonResult;
import com.test.httputils.HttpConfig;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created  on 2019-09-15
 *
 * @author QIAO
 * @describe
 */
public abstract class CommonCallBack<T> implements Observer<CommonResult<T>> {
    private boolean isShowDialog;
    private Context context;
    private String mes = "加载中请稍后";

    public CommonCallBack() {
    }

    public CommonCallBack(Context context) {
        this(false, context);
    }

    public CommonCallBack(boolean isShowDialog, Context context) {
        this.isShowDialog = isShowDialog;
        this.context = context;
    }

    public CommonCallBack(boolean isShowDialog, Context context, String mes) {
        this.isShowDialog = isShowDialog;
        this.context = context;
        this.mes = mes;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (isShowDialog) {
            Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNext(CommonResult<T> tCommonResult) {
        if (HttpConfig.RESPONSE_OK.equals(tCommonResult.getStatus())) {
            onCallBackSuccess(tCommonResult.getData());

        } else if (HttpConfig.TOKEN_OVERDUE.equals(tCommonResult.getErrorCode())) {

            onFail(tCommonResult.getErrorMsg());
        } else {
            onFail(tCommonResult.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketTimeoutException) {
            onFail("链接超时...请稍后再试");
        } else if (e instanceof ConnectException) {
            onFail("服务器连接失败...");
        } else if (e instanceof JsonSyntaxException) {
            onFail("暂无数据");
        } else if (e instanceof NullPointerException) {
            onFail("暂无数据");
        } else {
            onFail(e.getMessage());
        }
    }

    //完成
    @Override
    public void onComplete() {
//
    }

    /**
     * 数据请求成功
     *
     * @param data
     */
    public abstract void onCallBackSuccess(T data);


    /**
     * 数据请求失败
     *
     * @param mes
     */
    public void onFail(String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }

}
