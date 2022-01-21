package apps.commons.util.tool_util;

/**
 * @author YJH
 * @date 2019/12/6 22:50
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 *
 * @author ZJL
 * @date 2016年11月27日 下午3:32:11
 */
@Order(1)
@Component
public class SpringContextHolder implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    private static ClassLoader getClassLoader()
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
        {
            classLoader = SpringContextHolder.class.getClassLoader();
        }
        return classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.setProperty("org.owasp.esapi.resources", getClassLoader().getResource("ESAPI.properties").getPath().replace("ESAPI.properties", ""));
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        assertApplicationContext();
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        assertApplicationContext();
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        assertApplicationContext();
        return getApplicationContext().getBean(name, clazz);
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

    /**
     * 获取当前运行环境
     * @return
     */
    public static String getActiveProfile() {
        assertApplicationContext();
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        return profiles[profiles.length -1];
    }


}

