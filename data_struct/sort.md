# 排序算法

- 插入排序类
  - 直接插入排序
  - 希尔排序
- 选择排序类
  - 简单选择排序
  - 堆排序
- 交换排序
  - 冒泡排序
  - 快速排序
- 归并排序类
  - 归并排序

## 冒泡排序

通过跟相邻的数据比较，把较大或者较小的值冒泡的数组的头部，时间复杂度O(n^2^)

```javascript
let arr = [2, 919, 7, 33, 25, 99]

//  交换排序
//  通过把当前数据和其之后的数据进行比较，把较大的或者较小的放在前头
function swipeSort(arr) {
  let len = arr.length
  let temp
  for (let i = 0; i < len; i++) {
    for (let j = i; j < len; j++) {
      if (arr[i] < arr[j]) {
        temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
      }
    }
  }
  return arr
}

//  冒泡排序
function bubbleSort(arr) {
  let len = arr.length
  let temp
  for (let i = 0; i < len; i++) {
    for (let j = len - 1; j >= i; j--) {  // 关键步骤 ，循环判断后面的元素
      if (arr[j] <= arr[j - 1]) { // 关键步骤 ，移动元素
        temp = arr[j]
        arr[j] = arr[j - 1]
        arr[j - 1] = temp
      }
    }
  }
  return arr
}
```

## 简单选择排序

通过n-i次关键字之间的比较，从n-i+1个记录中选出关键字最小的记录，并和i个记录交换，时间复杂度O(n^2^)

```javascript
function simpleSelectSort(arr) {
  let len = arr.length
  let min
  let temp
  for (let i = 0; i < len; i++) {
    min = i //  把当前的作为最小值
    for (let j = i + 1; j < len; j++) {
      if (arr[min] >= arr[j]) { //  比较i之后的元素，记录所有值的最小值/最大值的下标
        min = j
      }
    }
    //  如果下标变化，说明当前值不是最小/最大的，需要交换元素
    if (min !== i) {
      temp = arr[min]
      arr[min] = arr[i]
      arr[i] = temp
    }
  }
  return arr
```

## 直接插入排序

把元素插入到一个已经排好序的列表中得到一个新的有序列表，时间复杂度O(n^2^)

```javascript
function insertSort(arr) {
  let len = arr.length
  let j
  arr.unshift(0)  //   设置哨兵
  for (let i = 2; i <= len; i++) {
    if (arr[i] > arr[i - 1]) {  //  降序大于号，升序小于号
      arr[0] = arr[i]
      for (j = i - 1; arr[j] < arr[0]; j--) { //  降序小于号，升序大于号，依次和前面的元素对比移位
        arr[j + 1] = arr[j]
      }
      arr[j + 1] = arr[0] //  插入到正确的位置
    }
  }
  arr.shift()
  return arr
}
```

## 希尔排序

跟直接插入排序类似，希尔排序的跳数是动态变化的，而快速排序的跳数都是1，时间复杂度O(n^3/2^)

```javascript
function shellSort(arr) {
  arr.unshift(0)
  let len = arr.length
  let increment = len
  let j
  do {
    increment = Math.floor(increment / 3 + 1) //  设置跳数，当设置为1的时候就是快速插入排序
    for (let i = increment + 1; i < len; i++) {
      if (arr[i] > arr[i - increment]) {  //  获取当前跳数值
        arr[0] = arr[i]
        //  判断当前值和上一跳的值的大小
        //  多个跳数值使用快速插入排序进行排序
        for (j = i - increment; j > 0 && arr[j] < arr[0]; j -= increment) {
          arr[j + increment] = arr[j] 
        }
        arr[j + increment] = arr[0] //  插入当正确的位置
      }
    }
  } while (increment > 1)
  arr.shift()
  return arr
}
```

## 堆排序

对简单选择排序的改进方案。时间复杂度O(nlogn)

## 归并排序

## 快速排序

通过一次排序将待排序元素分成两部分，并且其中一部分的值均比另一部分的大，然后对低子表和高子表递归的操作完成快速排序，复杂度为O(log(n))，不稳定的算法。

```javascript
/**
 * 元素交换位置
 * */
function swap(arr, s, f) {
  let temp = arr[s]
  arr[s] = arr[f]
  arr[f] = temp
}

/**
 * 把列表分成低子表和高子表
 * */
function separate(arr, low, high) {
  let p = arr[low]  //  以首个元素作为基点进行比较，比基点大的放在右边，小的放在左边
  while (low < high) {  //  循环比较，直到所有元素都比较完
    if (low < high && arr[high] >= p) {
      high--
    }
    swap(arr, low, high)  //  交换比基点小的元素在基点的左边
    if (low < high && arr[low] <= p) {
      low++
    }
    swap(arr, low, high)  //  交换比基点大的元素在基点的右边
  }
  return low  //  返回分离低子表和高子表的下标
}

/**
 * 递归的对子表进行排序
 * */
function qSort(arr, low, high) {
  if (low < high) {
    let f = sort(arr, low, high)
    qSort(arr, f + 1, high) //  对低子表进行递归排序
    qSort(arr, low, f - 1)  //  对高子表进行递归排序
  }
}

/**
 * 调用函数
 * */
function fastSort(arr) {
  let low = 0
  let high = arr.length - 1
  qSort(arr, low, high)
}
```