# notification 的使用

通知对象，用于构建通知信息。

- constructor(kind: NotificationKind, value?: T, error?: any)
  - kind
    - NotificationKind
      - NEXT
      - ERROR
      - COMPLETE
  - value
    - 消息的值
  - error
- observer(observer:Observer)
  - 发送通知给指定的观察者
  - observer
    - 需要通知的 observer 对象
      - Observer 结构，需要包含三个方法
        - next(v)
        - error(e)
        - complete()
- do(next: (value: T) => void, error?: (err: any) => void, complete?: () => void): any
- accept(nextOrObserver: PartialObserver<T> | ((value: T) => void), error?: (err: any) => void, complete?: () => void)
- toObservable(): Observable<T>
