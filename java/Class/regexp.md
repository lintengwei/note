# java 正则表达式

> 注意点

- 在 java 中的\的转义字符?

## 匹配规则

> 组匹配规则

正则表达式如下(A(B)(C)),分组匹配的规则为从左往右：

1. (A(B)(C))
2. (B)
3. (C)

## Pattern

```java
//  没有显示的构造函数，通过compile方法生成Pattern对象
/**
 * @param strReg  正则表达式描述
 * @param flag    标志位
 * */
public static Pattern compile(strReg)
public static Pattern compile(strReg,flag)
/**
 * @func 简单的判断字符串是否符合表达式的要求
 * @param strReg 正则表达式
 * @param inputStr  待判断字符串
 * */
public static boolean matches(String strReg,CharSequence inputStr)

//  实例方法
public Matcher matcher(strReg)
public String[] split(CharSequence inputStr)
public String[] split(CharSequence inputStr,int limit)
```

## Matcher

```java
//  generate  Matcher
Matcher matcher=Pattern.matcher(inputStr)

//  实例方法
//  只有先执行find方法，获取到下一个匹配组才可以做后续操作。start,end,group,toMatchResult等方法
/**
 * @func  判断是否有满足匹配的序列，不会消耗，只会查询
 * @param int start 匹配位置
 **/
public boolean find()
public boolean find(int start)

/**
 * @func  返回匹配的组
 * @param int group 上一次匹配的组 从【0】开始计数。0返回完整的匹配
 * @param String name
 **/
public String group()
public String group(int group)
public String group(String name)
public int groupCount()

/**
 * @func  返回下一个匹配位置的末端
 * @param int group 上一次匹配的组 从【0】开始计数。0返回完整的匹配
 * @param String name
 **/
public int end()
public int end(int group)
public int end(String name)
public int start()
public int start(int group)
public int start(String name)

public boolean matches()  // equil Pattern.matches()
public boolean lookingAt()
public Matcher reset()
public Matcher reset(charSequence input)  //  重置匹配字符串，所以值都会刷新
public MatchResult toMatchResult()  //  返回此匹配的匹配状态，在find方法之后调用，把这次的匹配结果存储在MatceResult中

/**
 * @func  替换匹配的组，会消耗所有匹配组，知道不满足匹配组。
 * @param String replacement  替换的字符串
 **/
public String replaceAll(String replacement)
//  只会消耗第一个满足匹配的组
public String replaceFirst(String replacement)
```

```java
//  usage
Pattern pt=Pattern.compile("([a-z]+)(\\d+)");
Matcher matcher=pt.matcher("aaaa22cccc33333dd2");
/**
 * 每次返回的组数量，括号的数量？
 * 以上面的模式匹配返回的的值为2
 **/
matcher.groupCount();

// 会消耗匹配，导致下面的查询匹配不到
matcher.replaceAll("||");

/**
 * 查看是否有满足的匹配，如果有返回true，否则返回false
 * */
while(matcher.find()){
  matcher.group(0);
  matcher.group();  //  默认返回完整匹配  同上
  matcher.group(1); //  返回第一个组匹配
  matcher.group(2); //  返回第二个组匹配
  matcher.end();  //  返回完整匹配的末端的下一个字符的位置
  matcher.end(1); //  返回第一个捕获组的末端的下一个字符的位置
  matcher.end(2); //  返回第二个捕获组的末端的下一个字符的位置
}
```

## MatcherResult

```java
//   实例方法
public int start()
public int start(int group)
public int end()
public int end(int group)
public String group()
public String group(int group)
public int groupCount()
```
