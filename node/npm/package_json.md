# 关于 package.json 的介绍

## name

在 json 文件里面最重要的是 name 字段和 version 字段，是包必须的字段。两个字段表示唯一的包
命名规则：

- 最多 214 个字符
- 不能以点或者下划线开头
- 新包不能有大写字母
- 包名会成为 url 的一部分，因此不能包含任何非 url 的安全字符

注意事项:

- 不能和 node 的核心模块相同
- 不要包好‘js’或者‘node’在名称中
- name 名会出现在 require 的参数中，因此是简短的和有描述性的
- 在 push 之前可以先检查是否已经有相同的包存在

## version

很重要，必须可以用 node-semver 解析，我们可以使用该模块来作为判断包版本的依据，具体包版本判断规则如下：

作为 node 模块使用：

```javascript {.line-numbers}
const semver = require('semver')

semver.valid('1.2.3') // '1.2.3'
semver.valid('a.b.c') // null
semver.clean('  =v1.2.3   ') // '1.2.3'
semver.satisfies('1.2.3', '1.x || >=2.5.0 || 5.0.0 - 7.2.3') // true
semver.gt('1.2.3', '9.8.7') // false
semver.lt('1.2.3', '9.8.7') // true
semver.valid(semver.coerce('v2')) // '2.0.0'
semver.valid(semver.coerce('42.6.7.9.3-alpha')) // '42.6.7'
```

## files

作为一个可选的字段，是一个文件数组的值。如果包含该字段，使用者，在安装包的的时候就必须同时包含字段的文件。一般使用.gitignore 或者.npmignore，如果包含两者都有，使用前者，如果只有后者，则后者会覆盖 files 字段的申明。

忽略选项：
.git
CVS
.svn
.hg
.lock-wscript
.wafpickle-N
._.swp
.DS*Store
.*_
npm-debug.log
.npmrc
node_modules
config.gypi
\*.orig
package-lock.json (use shrinkwrap instead)

## main

作为包的入口，如果使用者把我们的包作为依赖，当使用 require 的时候，返回的就是我们 expoort 的内容。

## scripts

是一个包含脚本命令的字典，这些脚本在包的不同环境切换使用，可以参考 vue-cli

## config

## dependencies

指定了包的依赖关系，将一个包映射到一个版本范围。依赖关系也可以通过 tarball 或 git URL 来识别。规则-[major, minor, patch] tuple--主要版本 次要版本 补丁
具体的包版本规则如下：

version Must match version exactly

> version 安装相等的包版本
> =version 大于等于版本
> <version 小于版本
> <=version 小于等于版本
> ~version 如果指定了 minor，则允许修改 patch，如没有，则允许修改主要版本,例子：

~1.2.3 := >=1.2.3 <1.(2+1).0 := >=1.2.3 <1.3.0
~1.2 := >=1.2.0 <1.(2+1).0 := >=1.2.0 <1.3.0 (Same as 1.2.x)
~1 := >=1.0.0 <(1+1).0.0 := >=1.0.0 <2.0.0 (Same as 1.x)
~0.2.3 := >=0.2.3 <0.(2+1).0 := >=0.2.3 <0.3.0
~0.2 := >=0.2.0 <0.(2+1).0 := >=0.2.0 <0.3.0 (Same as 0.2.x)
~0 := >=0.0.0 <(0+1).0.0 := >=0.0.0 <1.0.0 (Same as 0.x)
~1.2.3-beta.2 := >=1.2.3-beta.2 <1.3.0 Note that prereleases in the 1.2.3 version will be allowed, if they are greater than or equal to beta.2. So, 1.2.3-beta.4 would be allowed, but 1.2.4-beta.2 would not, because it is a prerelease of a different [major, minor, patch] tuple.

^version 允许从左到右的第一段不为 0 那一版本位+1 迭代，如下：

^1.2.3 := >=1.2.3 <2.0.0
^0.2.3 := >=0.2.3 <0.3.0
^0.0.3 := >=0.0.3 <0.0.4
^1.2.3-beta.2 := >=1.2.3-beta.2 <2.0.0 Note that prereleases in the 1.2.3 version will be allowed, if they are greater than or equal to beta.2. So, 1.2.3-beta.4 would be allowed, but 1.2.4-beta.2 would not, because it is a prerelease of a different [major, minor, patch] tuple.
^0.0.3-beta := >=0.0.3-beta <0.0.4 Note that prereleases in the 0.0.3 version only will be allowed, if they are greater than or equal to beta. So, 0.0.3-pr.2 would be allowed.

如果没有指定 patch 版本，初始会下降到 0，上限按照之前的规则
^1.2.x := >=1.2.0 <2.0.0
^0.0.x := >=0.0.0 <0.1.0
^0.0 := >=0.0.0 <0.1.0

如果没有指定 minor 和 patch 值，都下线为 0，上限按照前者的
^1.x := >=1.0.0 <2.0.0
^0.x := >=0.0.0 <1.0.0

1.2.x 1.2.0, 1.2.1, etc., but not 1.3.0
http://... See 'URLs as Dependencies' below

- Matches any version
  "" (just an empty string) Same as \*
  version1 - version2 Same as >=version1 <=version2.
  range1 || range2 Passes if either range1 or range2 are satisfied.
  git... See 'Git URLs as Dependencies' below
  user/repo See 'GitHub URLs' below
  tag A specific version tagged and published as tag See npm-dist-tag
  path/path/path See Local Paths below

## devDependencies

## peerDependencies

## bundledDependencies

## optionalDependencies

## engines

指定运行时候的 node 环境

```javascript
{ "engines" : { "node" : ">=0.10.3 <0.12" } }
{ "engines" : { "npm" : "~1.0.20" } }
```

## os

包的引用环境，操作系统

```javascript
"os" : [ "darwin", "linux" ]
"os" : [ "!win32" ]
```

## cpu

如果代码只能在特定的 cpu 环境运行，需要告诉用户

```javascript
"cpu" : [ "x64", "ia32" ]
"cpu" : [ "!arm", "!mips" ]
```

## 其他

- keywords
  - 主要是在 npm 中搜索的关键字
- bugs
  - 关于包的 bugs 反馈的地方
- licens
  - 指定包的使用范围，用户是否有权限使用

## 默认设置

```javascript
//  如果根目录包含server.js文件，会自动添加如下
"scripts": {"start": "node server.js"}

//  如果包含binding.gyp文件，但是我们没有定位install preinstall脚本执行命令，则会自动添加
"scripts":{"install": "node-gyp rebuild"}

//  如果有AUTHORS文件在根目录，则npm会自动导入  name-<email>(url)格式
"contributors": [...]
```
