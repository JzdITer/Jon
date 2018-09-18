# Jon是一个快速开发工具
app目录下为项目级Demo，为了快速开发，可参照该目录下的工具。<p>
jon目录下为核心框架，主要包含Widget、Core、Utils<p>
为了练习Kotlin，计划Jon为Kotlin工程，不过因为时间关系，工程中可能包含相关的Java类
## 集成
### gradle:
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```
dependencies {
	        implementation 'com.github.JzdITer:Jon:1.0.1'
	}
```
## 解决包冲突
```
implementation ('com.github.JzdITer:Jon:1.0.1',{
        exclude group:'com.android.support'
    })
```

具体使用方法，请参考app模块下的测试代码
