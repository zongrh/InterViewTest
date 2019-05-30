
https://www.jianshu.com/p/0cd258eecf60

RxJava 2.x 拥有了新的特性，其依赖于4个基础接口，它们分别是
* Publisher
* Subscriber
* Subscription
* Processor
其中最核心的莫过于 Publisher 和 Subscriber。Publisher 可以发出一系列的事件，而 Subscriber 负责和处理这些事件。
其中用的比较多的自然是 Publisher 的 Flowable，它支持背压。关于背压给个简洁的定义就是：
背压是指在异步场景中，被观察者发送事件速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略。
简而言之，背压是流速控制的一种策略。有兴趣的可以看一下官方对于背压的讲解。
可以明显地发现，RxJava 2.x 最大的改动就是对于 backpressure 的处理，为此将原来的 Observable 拆分成了新的 Observable 和 Flowable，同时其他相关部分也同时进行了拆分，但令人庆幸的是，是它，是它，还是它，还是我们最熟悉和最喜欢的 RxJava。
观察者模式
大家可能都知道， RxJava 以观察者模式为骨架，在 2.0 中依旧如此。
不过此次更新中，出现了两种观察者模式：
* Observable ( 被观察者 ) / Observer ( 观察者 )
* Flowable （被观察者）/ Subscriber （观察者）

在 RxJava 2.x 中，Observable 用于订阅 Observer，不再支持背压（1.x 中可以使用背压策略），而 Flowable 用于订阅 Subscriber ， 是支持背压（Backpressure）的。
** 第一步：初始化 Observable **
** 第二步：初始化 Observer **
** 第三步：建立订阅关系 **
        
   Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
                      @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() { // 第三步：订阅
            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {      
                mDisposable = d;
            }
            @Override
            public void onNext(@NonNull Integer integer) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }
            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete" + "\n" );
            }
});

线程调度
subScribeOn
用于指定发送者发送消息的线程
observeOn
用于指定接受者接送消息的线程
线程切换需要注意的
RxJava 内置的线程调度器的确可以让我们的线程切换得心应手，但其中也有些需要注意的地方。
* 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
* 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
* 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
                    }
   });

实例代码中，分别用 Schedulers.newThread() 和 Schedulers.io() 对发射线程进行切换，并采用 observeOn(AndroidSchedulers.mainThread() 和 Schedulers.io() 进行了接收线程的切换。可以看到输出中发射线程仅仅响应了第一个 newThread，但每调用一次 observeOn() ，线程便会切换一次，因此如果我们有类似的需求时，便知道如何处理了。
RxJava 中，已经内置了很多线程选项供我们选择，例如有：
* Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；
* Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作；
* Schedulers.newThread() 代表一个常规的新线程；
* AndroidSchedulers.mainThread() 代表Android的主线程
这些内置的 Scheduler 已经足够满足我们开发的需求，因此我们应该使用内置的这些选项，而 RxJava 内部使用的是线程池来维护这些线程，所以效率也比较高。

操作符
Create
create 操作符应该是最常见的操作符了，主要用于产生一个 Obserable 被观察者对象，为了方便大家的认知，以后的教程中统一把被观察者 Observable 称为发射器（上游事件），观察者 Observer 称为接收器（下游事件）。
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1" + "\n");
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2" + "\n");
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3" + "\n");
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                mRxOperatorsText.append("Observable emit 4" + "\n");
                Log.e(TAG, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n" );
                mDisposable = d;
            }
            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n" );
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                    mRxOperatorsText.append("onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                    Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }
            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                Log.e(TAG, "onComplete" + "\n" );
            }
 });
需要注意的几点是：
* 在发射事件中，我们在发射了数值 3 之后，直接调用了 e.onComlete()，虽然无法接收事件，但发送事件还是继续的。
* 另外一个值得注意的点是，在 RxJava 2.x 中，可以看到发射事件方法相比 1.x 多了一个 throws Excetion，意味着我们做一些特定操作再也不用 try-catch 了。
* 并且 2.x 中有一个 Disposable 概念，这个东西可以直接调用切断，可以看到，当它的 isDisposed() 返回为 false 的时候，接收器能正常接收事件，但当其为 true 的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。

Map
Map 基本算是 RxJava 中一个最简单的操作符了，熟悉 RxJava 1.x 的知道，它的作用是对发射时间发送的每一个事件应用一个函数，是的每一个事件都按照指定的函数去变化，而在 2.x 中它的作用几乎一致。
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mRxOperatorsText.append("accept : " + s +"\n");
                Log.e(TAG, "accept : " + s +"\n" );
            }
 });
map 基本作用就是将一个 Observable 通过某种函数关系，转换为另一种 Observable，上面例子中就是把我们的 Integer 数据变成了 String 类型。从Log日志显而易见。
Zip
zip 专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mRxOperatorsText.append("zip : accept : " + s + "\n");
                Log.e(TAG, "zip : accept : " + s + "\n");
            }
 });
private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    mRxOperatorsText.append("String emit : A \n");
                    Log.e(TAG, "String emit : A \n");
                    e.onNext("B");
                    mRxOperatorsText.append("String emit : B \n");
                    Log.e(TAG, "String emit : B \n");
                    e.onNext("C");
                    mRxOperatorsText.append("String emit : C \n");
                    Log.e(TAG, "String emit : C \n");
                }
            }
        });
    }
    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    mRxOperatorsText.append("Integer emit : 1 \n");
                    Log.e(TAG, "Integer emit : 1 \n");
                    e.onNext(2);
                    mRxOperatorsText.append("Integer emit : 2 \n");
                    Log.e(TAG, "Integer emit : 2 \n");
                    e.onNext(3);
                    mRxOperatorsText.append("Integer emit : 3 \n");
                    Log.e(TAG, "Integer emit : 3 \n");
                    e.onNext(4);
                    mRxOperatorsText.append("Integer emit : 4 \n");
                    Log.e(TAG, "Integer emit : 4 \n");
                    e.onNext(5);
                    mRxOperatorsText.append("Integer emit : 5 \n");
                    Log.e(TAG, "Integer emit : 5 \n");
                }
            }
        });
    }

* zip 组合事件的过程就是分别从发射器 A 和发射器 B 各取出一个事件来组合，并且一个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的，运行后可以看到，1 永远是和 A 结合的，2 永远是和 B 结合的。
* 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同，运行后看到，5 很孤单，没有人愿意和它交往，孤独终老的单身狗。

Concat
对于单一的把两个发射器连接成一个发射器，虽然 zip 不能完成，但我们还是可以自力更生，官方提供的 concat 让我们的问题得到了完美解决。
Observable.concat(Observable.just(1,2,3), Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("concat : "+ integer + "\n");
                        Log.e(TAG, "concat : "+ integer + "\n" );
                    }
  });
FlatMap 
它可以把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable，flatMap 并不能保证事件的顺序
Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
               e.onNext(1);
               e.onNext(2);
               e.onNext(3);
           }
       }).flatMap(new Function<Integer, ObservableSource<String>>() {
           @Override
           public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
               List<String> list = new ArrayList<>();
               for (int i = 0; i < 3; i++) {
                   list.add("I am value " + integer);
               }
               int delayTime = (int) (1 + Math.random() * 10);
               return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
           }
       }).subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Consumer<String>() {
                   @Override
                   public void accept(@NonNull String s) throws Exception {
                       Log.e(TAG, "flatMap : accept : " + s + "\n");
                       mRxOperatorsText.append("flatMap : accept : " + s + "\n");
                   }
               });
ConcatMap
它可以把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable，ConcatMap 可以保证事件的顺序
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "flatMap : accept : " + s + "\n");
                        mRxOperatorsText.append("flatMap : accept : " + s + "\n");
                    }
                });

distinct
去掉重复的数据

 Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("distinct : " + integer + "\n");
                        Log.e(TAG, "distinct : " + integer + "\n");
                    }
             });
Filter
Filter 你会很常用的，它的作用也很简单，过滤器嘛。可以接受一个参数，让其过滤掉不符合我们条件的值
Observable.just(1, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 10;//过滤舍去小于10的值
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                mRxOperatorsText.append("filter : " + integer + "\n");
                Log.e(TAG, "filter : " + integer + "\n");
            }
        });

buffer  
buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个 Observable 


Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        mRxOperatorsText.append("buffer size : " + integers.size() + "\n");
                        Log.e(TAG, "buffer size : " + integers.size() + "\n");
                        mRxOperatorsText.append("buffer value : ");
                        Log.e(TAG, "buffer value : " );
                        for (Integer i : integers) {
                            mRxOperatorsText.append(i + "");
                            Log.e(TAG, i + "");
                        }
                        mRxOperatorsText.append("\n");
                        Log.e(TAG, "\n");
                    }
     });

timer
timer 很有意思，相当于一个定时任务。在 1.x 中它还可以执行间隔逻辑，但在 2.x 中此功能被交给了 interval，下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。

mRxOperatorsText.append("timer start : " + TimeUtil.getNowStrTime() + "\n");
        Log.e(TAG, "timer start : " + TimeUtil.getNowStrTime() + "\n");
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mRxOperatorsText.append("timer :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                        Log.e(TAG, "timer :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                    }
    });
interval
interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。


mRxOperatorsText.append("interval start : " + TimeUtil.getNowStrTime() + "\n");
       Log.e(TAG, "interval start : " + TimeUtil.getNowStrTime() + "\n");
       Observable.interval(3,2, TimeUnit.SECONDS)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
               .subscribe(new Consumer<Long>() {
                   @Override
                   public void accept(@NonNull Long aLong) throws Exception {
                       mRxOperatorsText.append("interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                       Log.e(TAG, "interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                   }
               });
第一次延迟了 3 秒后接收到，后面每次间隔了 2 秒。关闭当前activity时需要销毁掉
@Override
   protected void doSomething() {
       mRxOperatorsText.append("interval start : " + TimeUtil.getNowStrTime() + "\n");
       Log.e(TAG, "interval start : " + TimeUtil.getNowStrTime() + "\n");
       mDisposable = Observable.interval(3, 2, TimeUnit.SECONDS)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
               .subscribe(new Consumer<Long>() {
                   @Override
                   public void accept(@NonNull Long aLong) throws Exception {
                       mRxOperatorsText.append("interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                       Log.e(TAG, "interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                   }
               });
   }
   @Override
   protected void onDestroy() {
       super.onDestroy();
       if (mDisposable != null && !mDisposable.isDisposed()) {
           mDisposable.dispose();
       }
   }

doOnNext
它的作用是让订阅者在接收到数据之前干点有意思的事情。假如我们在获取到数据之前想先保存一下它
Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("doOnNext 保存 " + integer + "成功" + "\n");
                        Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                mRxOperatorsText.append("doOnNext :" + integer + "\n");
                Log.e(TAG, "doOnNext :" + integer + "\n");
            }
        });

skip 
skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。

 
Observable.just(1,2,3,4,5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("skip : "+integer + "\n");
                        Log.e(TAG, "skip : "+integer + "\n");
                    }
   });

take
take，接受一个 long 型参数 count ，代表至多接收 count 个数据。

Flowable.fromArray(1,2,3,4,5)
                .take(2)//接收两个数据，其余没发送完的数据舍弃
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("take : "+integer + "\n");
                        Log.e(TAG, "accept: take : "+integer + "\n" );
                    }
                });

just
就是一个简单的发射器依次调用 onNext() 方法。
Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mRxOperatorsText.append("accept : onNext : " + s + "\n");
                        Log.e(TAG,"accept : onNext : " + s + "\n" );
                    }
        });

Single
顾名思义，Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        mRxOperatorsText.append("single : onSuccess : "+integer+"\n");
                        Log.e(TAG, "single : onSuccess : "+integer+"\n" );
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRxOperatorsText.append("single : onError : "+e.getMessage()+"\n");
                        Log.e(TAG, "single : onError : "+e.getMessage()+"\n");
                    }
                });


debounce
去除发送频率过快的项   debounce(500, TimeUnit.MILLISECONDS)//去除发送间隔时间小于500毫秒的发射事件
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS)//去除发送间隔时间小于500毫秒的发射事件
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("debounce :" + integer + "\n");
                        Log.e(TAG,"debounce :" + integer + "\n");
                    }
                });
Defer
简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。

Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }
            @Override
            public void onNext(@NonNull Integer integer) {
                mRxOperatorsText.append("defer : " + integer + "\n");
                Log.e(TAG, "defer : " + integer + "\n");
            }
            @Override
            public void onError(@NonNull Throwable e) {
                mRxOperatorsText.append("defer : onError : " + e.getMessage() + "\n");
                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
            }
            @Override
            public void onComplete() {
                mRxOperatorsText.append("defer : onComplete\n");
                Log.e(TAG, "defer : onComplete\n");
            }
        });
last
last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。


Observable.just(1, 2, 3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("last : " + integer + "\n");
                        Log.e(TAG, "last : " + integer + "\n");
                    }
      });
merge
merge 合并，熟悉版本控制工具的你一定不会不知道 merge 命令，而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送

Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("merge :" + integer + "\n");
                        Log.e(TAG, "accept: merge :" + integer + "\n" );
                    }
                });
reduce
reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。

Observable.just(1, 2, 3)
               .reduce(new BiFunction<Integer, Integer, Integer>() {
                   @Override
                   public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                       return integer + integer2;
                   }
               }).subscribe(new Consumer<Integer>() {
           @Override
           public void accept(@NonNull Integer integer) throws Exception {
               mRxOperatorsText.append("reduce : " + integer + "\n");
               Log.e(TAG, "accept: reduce : " + integer + "\n");
           }
       });

scan
scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。

Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                mRxOperatorsText.append("scan " + integer + "\n");
                Log.e(TAG, "accept: scan " + integer + "\n");
            }
        });
window

按照实际划分窗口，将数据发送给不同的 Observable

mRxOperatorsText.append("window\n");
       Log.e(TAG, "window\n");
       Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
               .take(15) // 最多接收15个
               .window(3, TimeUnit.SECONDS)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Consumer<Observable<Long>>() {
                   @Override
                   public void accept(@NonNull Observable<Long> longObservable) throws Exception {
                       mRxOperatorsText.append("Sub Divide begin...\n");
                       Log.e(TAG, "Sub Divide begin...\n");
                       longObservable.subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(new Consumer<Long>() {
                                   @Override
                                   public void accept(@NonNull Long aLong) throws Exception {
                                       mRxOperatorsText.append("Next:" + aLong + "\n");
                                       Log.e(TAG, "Next:" + aLong + "\n");
                                   }
                               });
                   }
               });








