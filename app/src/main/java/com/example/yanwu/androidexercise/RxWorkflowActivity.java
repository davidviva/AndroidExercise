package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;

import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class RxWorkflowActivity extends BaseActivity {
    private static final String TAG = "RxWorkflow";
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_workflow);
        ButterKnife.bind(this);

        method3();
    }

    // 等OnComplete才cancel
    //    RxWorkflow: sleep
    //    RxWorkflow: number is emitted: 0
    //    RxWorkflow: sleep
    //    RxWorkflow: the received number is: 0
    //    RxWorkflow: number is emitted: 1
    //    RxWorkflow: sleep
    //    RxWorkflow: the received number is: 1
    //    RxWorkflow: the received number is: 2
    //    RxWorkflow: complete
    //    RxWorkflow: number is emitted: 2
    //    RxWorkflow: the emitter is cancelled
    private void method0() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i <= 2; i++) {
                    Log.i(TAG, "sleep");
                    Thread.sleep(1000);
                    emitter.onNext(i);
                    Log.i(TAG, "number is emitted: " + i);

                }
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.i(TAG, "the emitter is cancelled");
                    }
                });
            }
        }, BackpressureStrategy.BUFFER)
                .take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "the received number is: " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "error message: " + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "complete");
                    }
                });
    }

    // 没运行OnComplete 直接就cancel了
    //    RxWorkflow: received item: 1
    //    RxWorkflow: received item: 2
    //    RxWorkflow: received item: 3
    //    RxWorkflow: is disposed? true
    //    RxWorkflow: is disposed? true

    private void method1() {
        mDisposable = Flowable.range(1, 5)
                .subscribeWith(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onStart() {
                        request(1);
                    }

                    @Override
                    public void onNext(Integer t) {
                        if (t == 3) {
                            cancel();
                        }
                        Log.i(TAG, "received item: " + t);
                        request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "done");
                    }
                });
        Log.i(TAG, "is disposed? " + String.valueOf(mDisposable.isDisposed()));
        mDisposable.dispose();
        Log.i(TAG, "is disposed? " + String.valueOf(mDisposable.isDisposed()));
    }

    // 测试repeatWhen
    private void method2() {
        mDisposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i <= 2; i++) {
                    Log.i(TAG, "sleep");
                    Thread.sleep(1000);
                    emitter.onNext(i);
                    Log.i(TAG, "number is emitted: " + i);
                }
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        }).toList()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        if (strings.isEmpty()) {
                            method2();
                        } else {
                            System.out.println(strings);
                        }
                    }
                });
    }

    // Maybe 的work flow
    // 外层Maybe
    // 1. onSuccess   只有第一个consumer被调用
    // 2. onComplete  只有action被调用
    // 3. onError   doOnError后 调用complete
    // 内层Maybe
    // 1. onError     只调用第二个error的consumer
    private void method3() {
        Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(1);
//                emitter.onComplete();
//                emitter.onError(new Throwable("666"));
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("do on error");
            }
        }).onErrorComplete()
                .flatMap(new Function<Integer, MaybeSource<?>>() {
                    @Override
                    public MaybeSource<Integer> apply(final Integer integer) throws Exception {
                        return Maybe.create(new MaybeOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
//                                emitter.onSuccess(integer);
                                emitter.onError(new Throwable("66666"));
                            }
                        });
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("consumer in subscriber");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("error in subscriber");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("complete");
                    }
                });
    }
}
