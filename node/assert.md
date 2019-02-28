# assert断言库

## assert.ok(value[,message])

肯定断言，如果value为真不做处理，否则抛出AssertionError错误,相当于assert.equal(!!value, true, message)（已废弃）。

## assert.fail([message:String|Error])

抛出AssertionError错误，并且抛出带message的错误如果message是字符串的话，否则抛出message【Error】

## assert.strictEqual(actual, expected[, message])

判断actual===expected,如果不全等，抛出AssertionError错误

## assert.notStrictEqual(actual,expected[,message])

判断actual !== expected ，如果全等，抛出AssertionError错误

## assert.deepStrictEqual(actual,expected[,message])

判断actual是否深度相等和expected，不相等抛出错误

## assert.notDeepStrictEqual(actual,expected[,message])

判断actual深度相等和expected，否则抛出错误

## assert.ifError(value)

如果value不为undefined或者null，抛出错误,可用于测试回调函数的 error 参数

## assert.throw(block[,error][,message])

- params
  - block <Function>
  - error <RegExp> | <Function> | <Object> | <Error>
  - <String> | <Error>

断言block函数会抛出错误，并且和指定的error比较. error 可以是 Class、RegExp、校验函数、每个属性都会被测试是否深度全等的校验对象、或每个属性（包括不可枚举的 message 和 name 属性）都会被测试是否深度全等的错误实例。 当使用对象时，可以使用正则表达式来校验字符串属性。

# version >10

## assert.reject(block[,error][,message])

assert.throws()的异步版本

- params
  - block <Function>|<Promise>
  - error <RegExp> | <Function> | <Object> | <Error>
  - <String> | <Error>