# subject

每一个主体都是一个可观察者和观察者。您可以订阅一个主题，并且可以在 feed 值旁边调用 error 和 complete。

```javascript
import { Subject } from 'rxjs'

const subject = new Subject()

//  订阅
let s1 = s.subscribe(
  v => console.log('s1:::' + v),
  _ => {
    try {
    } catch (e) {
      console.log('error' + _)
    }
  }
)
let s2 = s.subscribe(v => console.log('s2:::' + v))
let s3 = s.subscribe(v => console.log('s3:::' + v))

let button = document.getElementsByTagName('button')[0]

let i = 0
button.addEventListener('click', e => {
  //  发出通知
  s.next(i++)
  if (i === 2) {
    s.error('dsds')
  }
})
```

## AsyncSubject

继承自 Subject。只有当调用 complete 的时候会发出最后一次调用 next 的时候的值。

```javascript
import { AsyncSubject } from 'rxjs'

const s = new AsyncSubject()

s.subscribe(v => console.log(v))

s.next(1)
s.next(2)
s.next('222')
//  当调用complete的时候，会发送最后一次next的值
s.complete()
```

## BehaviorSubject

继承自 Subject。但有初始值，当订阅的时候会发送初始值。

```javascript
import { BehaviorSubject } from 'rxjs'

const s = new BehaviorSubject(1)

//  马上收到信号 1
s.subscibe(v => console.log(1))
```

## ReplaySubject

继承自 Subject。会缓存最后发送的的值，对新订阅的用户马上发送缓存的值。

```javascript
import {
  fromEvent,
  interval,
  Subject,
  AsyncSubject,
  BehaviorSubject,
  ReplaySubject
} from 'rxjs'
import { audit } from 'rxjs/operators'

const s = new ReplaySubject(1)

//  订阅
let s1 = s.subscribe(
  v => console.log('s1:::' + v),
  _ => {
    console.log('error' + _)
  }
)
let s2 = s.subscribe(v => console.log('s2:::' + v))
let s3 = s.subscribe(v => console.log('s3:::' + v))

let button = document.getElementsByTagName('button')[0]
let add = document.getElementsByTagName('button')[1]

let i = 0
button.addEventListener('click', e => {
  //  发出通知
  i++
  s.next(i)
})

//  如果此时button已经点击了三次了，i的值为3，当add点击的时候会新增一个订阅用户，并且会马上收到s发出的值为3的消息
add.addEventListener('click', e => {
  s.subscribe(v => console.log('s4:::' + v))
})
```

## FAQ

1. 如何捕获错误？
