Java端SM2加密，golang无法解密，golang端SM2加密，java无法解密
https://github.com/ZZMarquis/gm/issues/6

### java 类库
https://github.com/ZZMarquis/gmhelper
https://github.com/aliyun/gm-jsse

上边两个库得集合使用需要修改 `gm-jsse.com.aliyun.gmsse.crypto.Crypto` 换成 `gmhelper.org.zz.gmhelper.SM2Util` 否则解密失败