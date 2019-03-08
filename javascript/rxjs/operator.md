# rxjs 操作符

[https://rxjs.dev/api?type=function](https://rxjs.dev/api?type=function)

- [rxjs 操作符](#rxjs-%E6%93%8D%E4%BD%9C%E7%AC%A6)
  - [转换操作符](#%E8%BD%AC%E6%8D%A2%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [buffer](#buffer)
    - [bufferCount](#buffercount)
    - [bufferTime](#buffertime)
    - [bufferToggle](#buffertoggle)
    - [bufferWhen](#bufferwhen)
    - [concatMap](#concatmap)
    - [concatMapTo](#concatmapto)
    - [exhaustMap](#exhaustmap)
    - [expand 【\*】](#expand)
    - [groupBy](#groupby)
    - [map](#map)
    - [mapTo](#mapto)
    - [mergeMap](#mergemap)
    - [mergeMapTo](#mergemapto)
    - [mergeScan](#mergescan)
    - [pairwise](#pairwise)
    - [partition](#partition)
    - [pluck](#pluck)
    - [scan](#scan)
    - [switchMap](#switchmap)
    - [switchMapTo](#switchmapto)
    - [window](#window)
    - [windowCount](#windowcount)
    - [windowTime](#windowtime)
    - [windowToggle](#windowtoggle)
    - [windowWhen](#windowwhen)
  - [过滤操作符](#%E8%BF%87%E6%BB%A4%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [debounce](#debounce)
    - [debounceTime](#debouncetime)
    - [distinct](#distinct)
    - [distinctUntilChanged](#distinctuntilchanged)
    - [distinctUntilKeyChanged](#distinctuntilkeychanged)
    - [elementAt](#elementat)
    - [filter](#filter)
    - [first](#first)
    - [ignoreElements](#ignoreelements)
    - [audit](#audit)
    - [auditTime](#audittime)
    - [last](#last)
    - [sample](#sample)
    - [single](#single)
    - [skip](#skip)
    - [skipLast](#skiplast)
    - [skipUntil](#skipuntil)
    - [skipUntil](#skipuntil-1)
    - [skipWhile](#skipwhile)
    - [take](#take)
    - [takeLast](#takelast)
    - [takeUntil](#takeuntil)
    - [takeWhile](#takewhile)
    - [throttle](#throttle)
    - [throttleTime](#throttletime)
  - [组合操作符](#%E7%BB%84%E5%90%88%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [combineAll](#combineall)
    - [combineLatest](#combinelatest)
    - [concat](#concat)
    - [concatAll](#concatall)
    - [exhaust](#exhaust)
    - [forkJoin](#forkjoin)
    - [merge](#merge)
    - [mergeAll](#mergeall)
    - [race](#race)
    - [startWith](#startwith)
    - [switch](#switch)
    - [withLatestFrom](#withlatestfrom)
    - [zip](#zip)
    - [zipAll](#zipall)
  - [多播操作符](#%E5%A4%9A%E6%92%AD%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [cache](#cache)
    - [multicast](#multicast)
    - [publish](#publish)
    - [publishBehavior](#publishbehavior)
    - [publishLast](#publishlast)
    - [publishReplay](#publishreplay)
    - [share](#share)
  - [错误处理操作符](#%E9%94%99%E8%AF%AF%E5%A4%84%E7%90%86%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [catch](#catch)
    - [retry](#retry)
    - [retryWhen](#retrywhen)
  - [工具操作符](#%E5%B7%A5%E5%85%B7%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [do](#do)
    - [delay](#delay)
    - [delayWhen](#delaywhen)
    - [dematerialize](#dematerialize)
    - [finally](#finally)
    - [let](#let)
    - [materialize](#materialize)
    - [observeOn](#observeon)
    - [subscribeOn](#subscribeon)
    - [timeInterval](#timeinterval)
    - [timestamp](#timestamp)
    - [timeout](#timeout)
    - [timeoutWith](#timeoutwith)
    - [toArray](#toarray)
    - [toPromise](#topromise)
  - [条件和布尔操作符](#%E6%9D%A1%E4%BB%B6%E5%92%8C%E5%B8%83%E5%B0%94%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [defaultIfEmpty](#defaultifempty)
    - [every](#every)
    - [find](#find)
    - [findIndex](#findindex)
    - [isEmpty](#isempty)
  - [数序和操作符](#%E6%95%B0%E5%BA%8F%E5%92%8C%E6%93%8D%E4%BD%9C%E7%AC%A6)
    - [count](#count)
    - [max](#max)
    - [min](#min)
    - [reduce](#reduce)

## 转换操作符

### buffer

buffer(closingNotifier: Observable<any>): Observable<T[]>

> 将源可观察对象发出的值储存起来，直到 closingNotifier 激活才把数据消费

```javascript
import { interval } from 'rxjs'
import { buffer } from 'rxjs/operators'

let o1 = interval(500)
let o2 = interval(4000)
o1.pipe(buffer(o2)).subscribe(v => {
  // 4s 输出 [0, 1, 2, 3, 4, 5, 6]
  //  8s 输出 [7,8,9,10,11,12,13,14]
  console.log(v)
})
```

### bufferCount

bufferCount(bufferSize: number, startBufferEvery: number): Observable<T[]>

> 缓冲源发出的值，知道缓冲数量达到 bufferSize 设置的值，会发出通知

```javascript
import { interval } from 'rxjs'
import { bufferCount, buffer } from 'rxjs/operators'
let o1 = interval(1000)
//  3s之后缓冲区数量为三个，会发布通知
o1.pipe(bufferCount(3)).subscribe(v => console.log(v))
```

### bufferTime

bufferTime(bufferTimeSpan: number, bufferCreationInterval: number, maxBufferSize: number, scheduler: Scheduler): Observable<T[]>

> 在指定的时间内把源发出的值缓存起来，知道到达指定的时间，会发布童子，把缓存的值取出，单位 ms

```javascript
import { interval } from 'rxjs'
import { bufferCount, buffer, bufferTime } from 'rxjs/operators'
let o1 = interval(1000)
o1.pipe(bufferTime(2500)).subscribe(v => console.log(v))
```

### bufferToggle

bufferToggle(openings: SubscribableOrPromise<O>, closingSelector: function(value: O): SubscribableOrPromise): Observable<T[]>

> 以数组形式收集过去的值。仅当打开 openings 时才开始收集，并调用 ClosingSelector 函数以获取一个可观察对象，指示何时关闭缓冲区。

```javascript
```

### bufferWhen

bufferWhen(closingSelector: function(): Observable): Observable<T[]>

> 将过往的值收集到数组中， 当开始收集数据的时候, 调用函数返回 Observable, 该 Observable 告知何时关闭缓冲区并重新开始收集。

```javascript
```

### concatMap

concatMap<T, R, O extends ObservableInput<any>>(project: (value: T, index: number) => O, resultSelector?: (outerValue: T, innerValue: ObservedValueOf<O>, outerIndex: number, innerIndex: number) => R): OperatorFunction<T, ObservedValueOf<O> | R>

> 过滤掉重复的发出值

```javascript
import { fromEvent, interval } from 'rxjs'
import { concatMap, take } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
//  每次点击会返回一个定时器，取前面四个作为发射值。是串行的，多次点击会存放在任务队列里面，只有前面一个执行完才会接着执行
const result = clicks.pipe(concatMap(ev => interval(1000).pipe(take(4))))
result.subscribe(x => console.log(x))
```

### concatMapTo

> 返回一个指定的值

```javascript
```

### exhaustMap

> 同 concatMap，但是不会累积。只有当前的累加器没再运行的时候才会发送值。

```javascript
import { fromEvent, interval } from 'rxjs'
import { exhaustMap, take } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const result = clicks.pipe(exhaustMap(ev => interval(1000).pipe(take(5))))
result.subscribe(x => console.log(x))
```

### expand 【\*】

expand<T, R>(project: (value: T, index: number) => ObservableInput<R>, concurrent: number = Number.POSITIVE_INFINITY, scheduler: SchedulerLike = undefined): OperatorFunction<T, R>

> 递归。

```javascript
import { fromEvent, of } from 'rxjs'
import { expand, mapTo, delay, take } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const powersOfTwo = clicks.pipe(
  mapTo(1),
  expand(x => of(2 * x).pipe(delay(1000))),
  take(20)
)
//  output  1 2 4 8 16 ....
powersOfTwo.subscribe(x => console.log(x))
```

### groupBy

groupBy<T, K, R>(keySelector: (value: T) => K, elementSelector?: ((value: T) => R) | void, durationSelector?: (grouped: GroupedObservable<K, R>) => Observable<any>, subjectSelector?: () => Subject<R>): OperatorFunction<T, GroupedObservable<K, R>>

> 分组

```javascript
import { mergeMap, groupBy, reduce } from 'rxjs/operators'
import { of } from 'rxjs'

of(
  { id: 1, name: 'javascript' },
  { id: 2, name: 'parcel' },
  { id: 2, name: 'webpack' },
  { id: 1, name: 'typescript' },
  { id: 3, name: 'tslint' }
)
  .pipe(
    groupBy(p => p.id),
    mergeMap(group$ => group$.pipe(reduce((acc, cur) => [...acc, cur], [])))
  )
  .subscribe(p => console.log(p))
```

### map

map<T, R>(project: (value: T, index: number) => R, thisArg?: any): OperatorFunction<T, R>

> 与 array.prototype.map（）类似，它通过转换函数传递每个源值，以获得相应的输出值。

```javascript
import { fromEvent } from 'rxjs'
import { map } from 'rxjs/operators'

let o1 = fromEvent(document, 'click')
o1.pipe(map(ev => ev.pageX)).subscribe(v => console.log(v))
```

### mapTo

mapTo<T, R>(value: R): OperatorFunction<T, R>

> 与 map 类似，但它每次都将每个源值映射到相同的输出值。

```javascript
import { fromEvent } from 'rxjs'
import { mapTo } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const greetings = clicks.pipe(mapTo('Hi'))
greetings.subscribe(x => console.log(x))
```

### mergeMap

> 过滤掉重复的发出值

```javascript
```

### mergeMapTo

> 过滤掉重复的发出值

```javascript
```

### mergeScan

> 过滤掉重复的发出值

```javascript
```

### pairwise

> 过滤掉重复的发出值

```javascript
```

### partition

> 过滤掉重复的发出值

```javascript
```

### pluck

> 过滤掉重复的发出值

```javascript
```

### scan

scan<T, R>(accumulator: (acc: R, value: T, index: number) => R, seed?: T | R): OperatorFunction<T, R>

> 同 reduce，但是只要源观测对象发送一个值，新的观测对象就会发送计算并且发送一次

### switchMap

switchMap<T, R, O extends ObservableInput<any>>(project: (value: T, index: number) => O, resultSelector?: (outerValue: T, innerValue: ObservedValueOf<O>, outerIndex: number, innerIndex: number) => R): OperatorFunction<T, ObservedValueOf<O> | R>

> 过滤掉重复的发出值

```javascript
```

### switchMapTo

> 过滤掉重复的发出值

```javascript
```

### window

> 过滤掉重复的发出值

```javascript
```

### windowCount

> 过滤掉重复的发出值

```javascript
```

### windowTime

> 过滤掉重复的发出值

```javascript
```

### windowToggle

> 过滤掉重复的发出值

```javascript
```

### windowWhen

> 过滤掉重复的发出值

```javascript
```

## 过滤操作符

### debounce

debounce<T>(durationSelector: (value: T) => SubscribableOrPromise<any>): MonoTypeOperatorFunction<T>

> 防抖

```javascript
import { fromEvent, interval } from 'rxjs'
import { debounce } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
//  开始事件之后，如果在指定的时间再次发生，则会取消之前的事件
const result = clicks.pipe(debounce(() => interval(1000)))
result.subscribe(x => console.log(x))
```

### debounceTime

debounceTime<T>(dueTime: number, scheduler: SchedulerLike = async): MonoTypeOperatorFunction<T>

> 同 debounce，但是是直接指定时间

```javascript
import { fromEvent } from 'rxjs'
import { debounceTime } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const result = clicks.pipe(debounceTime(1000))
result.subscribe(x => console.log(x))
```

### distinct

distinct<T, K>(keySelector?: (value: T) => K, flushes?: Observable<any>): MonoTypeOperatorFunction<T>

> 使用系统的比较器来比较值，过滤掉重复的发出值。类似 Set

```javascript
```

### distinctUntilChanged

distinctUntilChanged<T, K>(compare?: (x: K, y: K) => boolean, keySelector?: (x: T) => K): MonoTypeOperatorFunction<T>

> 返回一个 Observable，该 Observable 发出源 Observable 发出的所有项，这些项通过与【前一项】的比较而不同。只会和前一项比较。

```javascript
```

### distinctUntilKeyChanged

distinctUntilKeyChanged<T, K extends keyof T>(key: K, compare?: (x: T[K], y: T[K]) => boolean): MonoTypeOperatorFunction<T>

> 比较对象，指定对象的 key，过滤掉重复的发出值

```javascript
import { of } from 'rxjs'
import { distinctUntilKeyChanged } from 'rxjs/operators'

of(
  { age: 4, name: 'Foo' },
  { age: 7, name: 'Bar' },
  { age: 5, name: 'Foo' },
  { age: 6, name: 'Foo' }
)
  .pipe(distinctUntilKeyChanged('name'))
  .subscribe(x => console.log(x))

// displays:
// { age: 4, name: 'Foo' }
// { age: 7, name: 'Bar' }
// { age: 5, name: 'Foo' }
```

### elementAt

elementAt<T>(index: number, defaultValue?: T): MonoTypeOperatorFunction<T>

> 只会输出指定位置的值

```javascript
import { fromEvent } from 'rxjs'
import { elementAt } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
//  如果不存在指定的值，会报错
const result = clicks.pipe(elementAt(2))
result.subscribe(x => console.log(x))

// Results in:
// click 1 = nothing
// click 2 = nothing
// click 3 = MouseEvent object logged to console
```

### filter

> 自定义过滤的条件

```javascript
```

### first

> 输出第一个值

```javascript
```

### ignoreElements

ignoreElements(): OperatorFunction<any, never>

> 忽略所有发出的值，值发错错误和完成的信号

```javascript
import { of } from 'rxjs'
import { ifnoreElements } from 'rxjs/operators'

of('you', 'talking', 'to', 'me')
  .pipe(ignoreElements())
  .subscribe(
    word => console.log(word),
    err => console.log('error:', err),
    () => console.log('the end')
  )
// result:
// 'the end'
```

### audit

audit<T>(durationSelector: (value: T) => SubscribableOrPromise<any>): MonoTypeOperatorFunction<T>

> 过滤掉重复的发出值

```javascript
```

### auditTime

> 过滤掉重复的发出值

```javascript
```

### last

> 过滤掉重复的发出值

```javascript
```

### sample

> 过滤掉重复的发出值

```javascript
```

### single

> 过滤掉重复的发出值

```javascript
```

### skip

> 过滤掉重复的发出值

```javascript
```

### skipLast

> 过滤掉重复的发出值

```javascript
```

### skipUntil

> 过滤掉重复的发出值

```javascript
```

### skipUntil

> 过滤掉重复的发出值

```javascript
```

### skipWhile

> 过滤掉重复的发出值

```javascript
```

### take

> 过滤掉重复的发出值

```javascript
```

### takeLast

> 过滤掉重复的发出值

```javascript
```

### takeUntil

> 过滤掉重复的发出值

```javascript
```

### takeWhile

> 过滤掉重复的发出值

```javascript
```

### throttle

> 过滤掉重复的发出值

```javascript
```

### throttleTime

> 过滤掉重复的发出值

```javascript
```

## 组合操作符

### combineAll

> 过滤掉重复的发出值

```javascript
```

### combineLatest

> 过滤掉重复的发出值

```javascript
```

### concat

> 过滤掉重复的发出值

```javascript
```

### concatAll

> 过滤掉重复的发出值

```javascript
```

### exhaust

> 过滤掉重复的发出值

```javascript
```

### forkJoin

> 过滤掉重复的发出值

```javascript
```

### merge

> 过滤掉重复的发出值

```javascript
```

### mergeAll

mergeAll<T>(concurrent: number = Number.POSITIVE_INFINITY): OperatorFunction<ObservableInput<T>, T>

> 将高阶可观测值转换为一阶可观测值，同时传递内部可观测值上发出的所有值。
> 什么是可观测值？如果使用 interval(1000)，每秒递增的的数，0，1，2....等称为可观测值，是常数。当可观测值是可观察对象的时候，这个就是高阶可观测值。

```javascript
import { fromEvent, interval } from 'rxjs'
import { take, map, mergeAll } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
//  此时higherOrder发射的数据是可观察对象，因此higherOrder称为高阶可观察对象
const higherOrder = clicks.pipe(map(ev => interval(1000).pipe(take(10))))
//  通过mergeAll能够打平成一阶
const firstOrder = higherOrder.pipe(mergeAll(2))
firstOrder.subscribe(x => console.log(x))
```

### race

> 过滤掉重复的发出值

```javascript
```

### startWith

> 过滤掉重复的发出值

```javascript
```

### switch

> 过滤掉重复的发出值

```javascript
```

### withLatestFrom

> 过滤掉重复的发出值

```javascript
```

### zip

> 过滤掉重复的发出值

```javascript
```

### zipAll

> 过滤掉重复的发出值

```javascript
```

## 多播操作符

### cache

> 过滤掉重复的发出值

```javascript
```

### multicast

> 过滤掉重复的发出值

```javascript
```

### publish

> 过滤掉重复的发出值

```javascript
```

### publishBehavior

> 过滤掉重复的发出值

```javascript
```

### publishLast

> 过滤掉重复的发出值

```javascript
```

### publishReplay

> 过滤掉重复的发出值

```javascript
```

### share

> 过滤掉重复的发出值

```javascript
```

## 错误处理操作符

### catch

> 过滤掉重复的发出值

```javascript
```

### retry

> 过滤掉重复的发出值

```javascript
```

### retryWhen

> 过滤掉重复的发出值

```javascript
```

## 工具操作符

### do

> 过滤掉重复的发出值

```javascript
```

### delay

delay<T>(delay: number | Date, scheduler: SchedulerLike = async): MonoTypeOperatorFunction<T>

> 延迟源观测对象发送值的时间

```javascript
import { fromEvent } from 'rxjs'
import { delay } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
//  每一次点击之后都会延迟1s在发送值
const delayedClicks = clicks.pipe(delay(1000))
delayedClicks.subscribe(x => console.log(x))
```

### delayWhen

delayWhen<T>(delayDurationSelector: (value: T, index: number) => Observable<any>, subscriptionDelay?: Observable<any>): MonoTypeOperatorFunction<T>

> 同 delay，只是发送的时间是当 delayDurationSelector 发送第一个值的时候

```javascript
import { fromEvent, interval } from 'rxjs'
import { delayWhen } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const delayedClicks = clicks.pipe(
  //  每次点击都会延迟0~5s之后发出
  delayWhen(event => interval(Math.random() * 5000))
)
delayedClicks.subscribe(x => console.log(x))
```

### dematerialize

> 过滤掉重复的发出值

```javascript
```

### finally

> 过滤掉重复的发出值

```javascript
```

### let

> 过滤掉重复的发出值

```javascript
```

### materialize

> 过滤掉重复的发出值

```javascript
```

### observeOn

> 过滤掉重复的发出值

```javascript
```

### subscribeOn

> 过滤掉重复的发出值

```javascript
```

### timeInterval

> 过滤掉重复的发出值

```javascript
```

### timestamp

> 过滤掉重复的发出值

```javascript
```

### timeout

> 过滤掉重复的发出值

```javascript
```

### timeoutWith

> 过滤掉重复的发出值

```javascript
```

### toArray

> 过滤掉重复的发出值

```javascript
```

### toPromise

> 过滤掉重复的发出值

```javascript
```

## 条件和布尔操作符

### defaultIfEmpty

defaultIfEmpty<T, R>(defaultValue: R = null): OperatorFunction<T, T | R>

> 如果源观测对象不发一个值，则发送一个指定的值

```javascript
import { fromEvent } from 'rxjs'
import { defaultIfEmpty, takeUntil } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const clicksBeforeFive = clicks.pipe(takeUntil(interval(5000)))
const result = clicksBeforeFive.pipe(defaultIfEmpty('no clicks'))
result.subscribe(x => console.log(x))
```

### every

every<T>(predicate: (value: T, index: number, source: Observable<T>) => boolean, thisArg?: any): OperatorFunction<T, boolean>

> 检查源发送值是否满足给定的条件

### find

find<T>(predicate: (value: T, index: number, source: Observable<T>) => boolean, thisArg?: any): OperatorFunction<T, T | undefined>

> 发送满足条件的第一个值

```javascript
import { fromEvent, interval } from 'rxjs'
import { count, takeUntil, find } from 'rxjs/operators'

//  3s之后输出3
interval(1000)
  .pipe(find(v => v === 3))
  .subscribe(v => console.log(v))
```

### findIndex

findIndex<T>(predicate: (value: T, index: number, source: Observable<T>) => boolean, thisArg?: any): OperatorFunction<T, number>

> 发送满足条件的发送值的下标

```javascript
import { fromEvent } from 'rxjs'
import { findIndex } from 'rxjs/operators'

const clicks = fromEvent(document, 'click')
const result = clicks.pipe(findIndex(ev => ev.target.tagName === 'DIV'))
result.subscribe(x => console.log(x))
```

### isEmpty

## 数序和操作符

### count

count<T>(predicate?: (value: T, index: number, source: Observable<T>) => boolean): OperatorFunction<T, number>

> 统计发出值的次数

- predicate
  - 一个布尔函数，用于选择要计算的值。

```javascript
import { fromEvent, interval } from 'rxjs'
import { count, takeUntil } from 'rxjs/operators'

let o1 = fromEvent(document, 'click')

//  在未点击之前持续发出值
let o2 = interval(500).pipe(takeUntil(o1))

//  当点击之后，统计之前发出的值的次数
o2.pipe(count(v => v % 2 == 0)).subscribe(v => console.log(v))
```

### max

max<T>(comparer?: (x: T, y: T) => number): MonoTypeOperatorFunction<T>

> 同 min 操作符

### min

min<T>(comparer?: (x: T, y: T) => number): MonoTypeOperatorFunction<T>

> 返回观测值中最小的值

```javascript
import { of } from 'rxjs'
import { min } from 'rxjs/operators'

of({ age: 7, name: 'Foo' }, { age: 5, name: 'Bar' }, { age: 9, name: 'Beer' })
  .pipe(min((a, b) => (a.age < b.age ? -1 : 1)))
  .subscribe(x => console.log(x.name)) // -> 'Bar'
```

### reduce

reduce<T, R>(accumulator: (acc: R, value: T, index?: number) => R, seed?: R): OperatorFunction<T, R>

> 累加器，类似于 Array 中的 reduce。将源上发出的所有值组合在一起，使用一个知道如何将新的源值加入过去的累积中的累加器函数。

```javascript
import { fromEvent, interval } from 'rxjs'
import { reduce, takeUntil, mapTo } from 'rxjs/operators'

//  5s以内的点击都会发出值
const clicksInFiveSeconds = fromEvent(document, 'click').pipe(
  //  在interval发出第一个值之前的点击都是发送值
  takeUntil(interval(5000))
)
//  把点击发出的值映射为1
const ones = clicksInFiveSeconds.pipe(mapTo(1))
const seed = 0
//  通过reduce统计5s内点击的次数
//  是如何知道点击截止的？
//  当clickInfiveSeconds发出截止信号的时候，count发送值？
const count = ones.pipe(scan((acc, one) => acc + one, seed))
//  当
count.subscribe(x => console.log(x))
```
