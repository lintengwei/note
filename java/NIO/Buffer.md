# Buffer

[教程](http://tutorials.jenkov.com/java-nio/overview.html).数据消费流只会读取 position 到 limit 位置间的数据？

## methods

```java
//  调用该方法之后在调用消费流，会读取整个缓冲区的数据
//  不会清空缓冲区的数据，只是单纯的的把游标指向0，下次写入数据从0开始写
public final Buffer clear() {
    position = 0;
    limit = capacity;
    mark = -1;
    return this;
}

//  设置limit，之后调用消费流会读取limit到position之间的数据
public final Buffer limit(int newLimit) {
    if ((newLimit > capacity) || (newLimit < 0))
        throw new IllegalArgumentException();
    limit = newLimit;
    if (position > limit) position = limit;
    if (mark > limit) mark = -1;
    return this;
}

//  默认limit为capcacity，如果之后没再设置limit的值，会读取整个缓冲区
//  可用与重复读取数据，先调用flip()设置limit=position，position=0；
//  之后调用rewind()只会重置position,limit不会变
public final Buffer rewind() {
    position = 0;
    mark = -1;
    return this;
}

//  设置游标的位置
public final Buffer position(int newPosition) {
    if ((newPosition > limit) || (newPosition < 0))
        throw new IllegalArgumentException();
    position = newPosition;
    if (mark > position) mark = -1;
    return this;
}

//  一般写完数据之后，会调用这个方法来设置可读区域
public final Buffer flip() {
    limit = position;
    position = 0;
    mark = -1;
    return this;
}
```
