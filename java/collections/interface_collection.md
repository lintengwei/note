# Collection 接口

Collection 接口是所有容器类的基类

## 实现该接口的子类需要实现以下所有的方法

* size():int
  * 返回容器元素的个数
* isEmpty():boolean
  * 判断容器是否为空
* contains(Object):boolean
  * 是否包含给定元素
  * ArrarList 可以包含 null 元素
* iterator():Iterator<E>
* toArray():Object[]
  * 针对 List 类深复制容器
    * 在 ArrayList 里面的实现方式
      * Arrays.copyOf(elementData, size);
* toArray(T[]):T[]
* add(E):boolean
  * 添加元素到容器类
* remove(Object):boolean
  * 移除容器里面的元素
* containsAll(Collection<?>):boolean
  * 判断容器是否包含到集合里面所有的元素
  * ArrayList 过程
    * 如果初始化没传容量参数
      * 会默认等于一个空的数组
    * 把参数转换成数组->toArray()
    * 获取数组长度并且检测内置数组的长度
    * 如果数组长度不够，则尝试加长一般的容量进行比较，如果还是不够，直接用合并长度，并且不能超过整数的最大值
* addAll(Collection<? extends E>):boolean
* removeAll(Collection<?>):boolean
* removeIf(Predicate<? super E>):boolean
* retainAll(Collection<?>):boolean
* clear():void
* equals(Object):boolean
* hashCode():int
* spliterator():Spliterator<E>
* stream():Stream<E>
* parallelStream():Stream<E>
