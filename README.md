# Http
网络请求
NetConfig.baseUrl = BaseConfig.BaseUrl
            
NetConfig.isLog = BaseConfig.isLog
            
NetConfig.mToken = if (BaseConfig.token.isNullOrEmpty()) "token" else BaseConfig.token
            
NetConfig.mTokenKey = "XX-Token"
            
NetAppContext.init(instance)
