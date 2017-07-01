package com.rxjava2_demo1;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Author: Matthew
 * Describe:
 *   我们用两个事件流来描述, 事件的传递,
 *     3  2  1 -->
 *   -->     3  2  1  -->
 *
 *   两个管道通过一个定方式连接起来, 使得上游每产生一个事件, 下游就能收到该事件.
 *   事件发送顺序    1  2  3  接收顺序也只是 1  2  3 这样就很符合我们的思维;
 *   验证:
 *
 * */

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Matthew -- > DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickView(View view) {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
        //创建下一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "subscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(TAG, "" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "compelte");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }
}
