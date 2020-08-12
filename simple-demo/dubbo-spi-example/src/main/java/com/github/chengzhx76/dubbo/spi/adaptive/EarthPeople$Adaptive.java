package com.github.chengzhx76.dubbo.spi.adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
public class EarthPeople$Adaptive implements EarthPeople {
    public void say()  {
        throw new UnsupportedOperationException("The method public abstract void com.github.chengzhx76.dubbo.spi.EarthPeople.say() of interface com.github.chengzhx76.dubbo.spi.EarthPeople is not adaptive method!");
    }
    public void eat(org.apache.dubbo.common.URL arg0)  {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("p", url.getParameter("e", "china"));
        if(extName == null) throw new IllegalStateException("Failed to get extension (com.github.chengzhx76.dubbo.spi.EarthPeople) name from url (" + url.toString() + ") use keys([p, e])");
        EarthPeople extension = (EarthPeople)ExtensionLoader.getExtensionLoader(EarthPeople.class).getExtension(extName);
        extension.eat(arg0);
    }
}