package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.akarnokd.rxjava2.operators.FlowableTransformers;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;


/**
 * reference link: https://medium.com/@scottalancooper/pausing-and-resuming-a-stream-in-rxjava-988a0977b771
 * library: rxjava extensions
 *
 */
public class RxComposeActivity extends BaseActivity {
    PublishProcessor<Boolean> valve = PublishProcessor.create();
    private boolean isValveOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_compose);
        ButterKnife.bind(this);
    }

    // 注意subscribe 的是Object
    private void composeTest1() {
        Flowable.interval(1, TimeUnit.SECONDS)
                .compose(FlowableTransformers.valve(valve, true))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(final Object count) throws Exception {
                        Log.d("Count", "Count = " + count);
                    }
                });
    }

    private void composeTest2() {
        Flowable.create(new FlowableOnSubscribe<Student>() {
            @Override
            public void subscribe(FlowableEmitter<Student> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    System.out.println("emitting: " + i);
                    emitter.onNext(new Student(i));
                    Thread.sleep(1000);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .compose(FlowableTransformers.valve(valve, true))
                .subscribe(new Subscriber<Object>() {
                    Subscription sub;
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub = s;
                        sub.request(1);
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Received id: " + ((Student)o).id);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.btn_start)
    public void onStartClick() {
        composeTest2();
    }

    @OnClick(R.id.btn_pause)
    public void onPauseClick() {
        isValveOpen = !isValveOpen;
        Log.d("Count", "Valve is " + (isValveOpen ? "open." : "closed."));
        valve.onNext(isValveOpen);
    }

    @OnClick(R.id.btn_resume)
    public void onComposeClick() {
        isValveOpen = !isValveOpen;
        Log.d("Count", "Valve is " + (isValveOpen ? "open." : "closed."));
        valve.onNext(isValveOpen);
    }

    class Student {
        public int id;
        public Student(int id) {
            this.id = id;
        }
    }
}
