#Struts2整合模块
应用Struts2的插件机制对Struts2进行了封装和整合。

整合内容主要包括：

1.	采用Struts2 Convertion插件实现零配置。
2.	采用Struts2 Spring插件实现跟Spring的整合。
3.	采用FreeMarker模版技术实现页面模版化。
4.	采用DWZ/BootStrap（暂未进行封装）实现UI框架整合。

封装内容主要包括：

1.	修改了FreeMarker管理类，使之支持从Struts2插件包中加载各自的FreeMarker配置。
2.	封装了异常处理、提示信息、出错页面处理机制。
3.	实现了自定义枚举转换器、实体转换器、参数转换器。
4.	扩展了DWZ的页面图标支持以及封装了一些自定义函数；
5.	封装了DWZ的分页模版、首页模版以及提示信息辅助工具类。

##自定义FreeMarker配置管理器
自定义FreeMarker配置管理器支持从Struts2插件包中加载FreeMarker配置，这使得通过Struts2的插件机制
来实现业务系统模块化以及可插拔机制时每个模块可以加载自己的FreeMarker配置。但Struts2的插件机制是否
适合实现业务系统模块化和可插拔仍有待实践去检验。

##异常处理及出错页面
通常我们在业务系统中遇到的异常可以分为4类：

1.	不可预料异常

	这类异常通常是程序BUG或者运行环境出现了问题，例如：程序处理遗漏了某个分支；参数校验不严谨；网络
	异常导致无法连接到数据库等等。这类异常提示给最终用户的信息通常是：系统正忙，请稍后再试...
	
2.	可预料异常

	这类异常是开发人员主动抛出的，用以给用户提示信息，以纠正用户操作行为。例如：试图创建已存在的用户；
	试图删除已被关联使用的数据等。这类异常需要给用户引导性的建议：您创建的用户名已被使用，请选择其他
	用户名...
	
3.	权限限制异常

	当用户不具备操作权限又触发了某些操作时，系统会抛出权限限制异常。这类异常提示给最终用户的信息通常是：
	您没有执行该操作的权限，请与系统管理员联系...

4.	会话超时异常

	当用户离开电脑长时间不操作再次回来进行操作时，系统会抛出会话超时异常。这时系统通常会提示用户：您的会
	话已过期，请重新登录系统...
	
利用Struts2的异常处理框架，通过整合我们可以自动处理掉1、3、4类异常，使开发人员只需要关注与业务相关的2类
异常。

##自定义转换器
自定义转换器用于对页面表单提交的值与Model属性进行转换，由于我们编写的Model是面向对象的，所以Model属性可能
不是一个基础类型而是一个关联对象。为了编码的便利性，我们需要一些自定义的转换器，让Struts2可以自动转换这些特
殊的属性。

在Struts2整合模块中，自定义了枚举类转换器和业务实体类转换器，用于转换枚举属性和关联的业务实体属性，这两个转
换器是开发过程中最常用到的特殊属性转换器。

##对DWZ的扩展和封装
DWZ本身是基于jQuery框架的，所以利用jQuery的继承机制对DWZ进行扩展和封装是相当便利的，这样无需改动DWZ原本的
代码，使得日后对DWZ的升级变得轻松。

DWZ是一个相当优秀的UI框架，用于开发常规的后台管理系统非常合适。目前存在两个较为明显的短板：

1.	内置的图标太少了，远远不够业务系统使用；而框架本身没有在组件中考虑对图标的统一管理和扩展机制。
2.	换肤机制不够严谨，换肤的效果达不到可良好应用的级别，使得大部分情况下只能放弃换肤功能。

在Struts2整合模块中对于DWZ的图标扩展，目前只做到在列表工具栏和菜单图标上可以扩展；在换肤机制上增加了一个应用
程序层的可覆盖机制，使得应用程序层可以不修改DWZ源码来覆盖原有的换肤样式。当然要做到可良好应用级别，还需要UI工
程师在应用程序层做不少的工作。

以上部分说的是对于DWZ的UI部分的一些扩展和封装，除此以外还有对服务器与UI之间的交互数据的封装。
DWZ本身是一个单页面的UI框架，它巧妙的将UI与服务器的信息交互通过Ajax封装起来，使得开发者可以像编写常规的Web应
用一样来编写Ajax通信的Web应用。在DWZ中有一个通用的Ajax回调信息数据结构，采用Json格式：

	{
		"statusCode":"200",
		"message":"提示信息",
		"navTabId":"",
		"rel":"",
		"callbackType":"",
		"forwardUrl":"",
		"confirmMsg":""
	}
	
这个回调信息决定了与服务器交互后UI将做出的动作，整合模块对生成该回调信息进行了封装，使开发人员可以便利的返回正确
的回调信息。

除此以外整合模块还通过FreeMarker宏对DWZ中部分常用的组件进行了封装，例如：分页表单、分页工具栏、首页引用等。对于
大部分的组件，DWZ本身已经做到数据结构很简洁了，已经没有再封装成宏的必要。