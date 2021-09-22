# Http
网络请求
````
//添加服务器地址
NetBase.baseUrl = BaseConfig.BaseUrl
//是否打印请求与返回信息
NetBase.isLog = BaseConfig.isLog
//请求token  value
NetBase.mToken = if (BaseConfig.token.isNullOrEmpty()) "token" else BaseConfig.token
//请求token  key       
NetBase.mTokenKey = "XX-Token"
//传入Content
NetAppContext.init(instance)
````
