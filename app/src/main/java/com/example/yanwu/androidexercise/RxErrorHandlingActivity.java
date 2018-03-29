package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yanwu.androidexercise.utils.RetryWithDelay;

import org.reactivestreams.Publisher;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxErrorHandlingActivity extends AppCompatActivity {
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_error_handling);
        ButterKnife.bind(this);
        test5();
    }


    // Test doOnError
    private void test1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 0; i <= 2; i++) {
                    if (i == 2) {
                        e.onError(new Throwable("Error"));
                    } else {
                        e.onNext(i + "");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("Error got catched here");
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(throwable.getMessage());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("received");
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

    // Test onErrorComplete
    private void test2() {
        Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
                emitter.onError(new Throwable("Error"));
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("Error got catched here");
                System.out.println(Thread.currentThread().getName());
                System.out.println(throwable.getMessage());
            }
        }).onErrorComplete()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("11111");
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

    // Test retryWhen 里面的是次数, 根本不会走onError
    // result:
    // I/System.out: integer: 0
    // I/System.out: integer: 1
    // I/System.out: integer: 2
    // I/System.out: integer: 0
    // I/System.out: integer: 1
    // I/System.out: integer: 2
    // I/System.out: integer: 0
    // I/System.out: integer: 1
    // I/System.out: integer: 2
    private void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    // 可以修改if条件为i != 6
                    if (i != 3) {
                        // weird: if change to System.out.println("emitter"); 打印就不对
                        System.out.println("emit " + i);
                        emitter.onNext(i);
                    } else {
                        System.out.println("emitter");
                        emitter.onError(new Throwable("error"));
                    }
                }
            }
        })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Observable<Throwable> throwableObservable) throws Exception {
                        System.out.println("retryWhen");
                        System.out.println(Thread.currentThread().getName());
                        return Observable.range(1, 3);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("integer: " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("throwable: " + throwable.getMessage());
                    }
                });
    }

    private void test4() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                System.out.println("emitter");
                System.out.println("i  " + i);
                if (i == 0 || i == 1) {
                    emitter.onError(new Throwable("error"));
                } else {
                    emitter.onSuccess("success");
                }
            }
        })
                .retryWhen(new Function<Flowable<Throwable>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(Flowable<Throwable> throwableObservable) throws Exception {
                        System.out.println("retrywhen");
                        System.out.println(Thread.currentThread().getName());
                        System.out.println("i  " + i);
                        i++;
                        return Flowable.range(1, 3);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("result: " + s);
                        System.out.println("i  " + i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("throwable: " + throwable.getMessage());
                        System.out.println("i  " + i);
                    }
                });
    }

    private void test5() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                System.out.println("emitter");
                System.out.println("i  " + i);
                if (i == 0 || i == 1) {
                    emitter.onError(new Throwable("error"));
                } else {
                    emitter.onSuccess("success");
                }
            }
        })
                .retryWhen(new RetryWithDelay(3, 3000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("result: " + s);
                        System.out.println("i  " + i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("throwable: " + throwable.getMessage());
                        System.out.println("i  " + i);
                    }
                });
    }
}